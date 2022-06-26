package io.github.alemazzo.sushime.utils

import android.content.Context
import android.util.Log
import info.mqtt.android.service.Ack
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttWrapper(context: Context) {

    companion object {
        const val TAG = "MqttWrapper"
    }

    private val serverURI = "tcp://broker.emqx.io:1883"
    private val mqttClient: MqttAndroidClient =
        MqttAndroidClient(context, serverURI, "kotlin_client", Ack.AUTO_ACK)
    private var channelMap = mutableMapOf<String, (String) -> Unit>()
    var isConnected = false

    fun connect(onConnect: (MqttWrapper) -> Unit) {
        if (isConnected) {
            isConnected = false
            channelMap = mutableMapOf()
            this.mqttClient.disconnect()
        }
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d(TAG, "Receive message: ${message.toString()} from topic: $topic")
                if (channelMap.containsKey(topic)) {
                    channelMap[topic]?.invoke(message.toString())
                }
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d(TAG, "Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
        val options = MqttConnectOptions()
        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                isConnected = true
                onConnect(this@MqttWrapper)
                Log.d(TAG, "Connection success")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d(TAG, "Connection failure")
            }
        })

    }

    fun disconnect(onDisconnect: () -> Unit) {
        try {
            mqttClient.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Disconnected")
                    this@MqttWrapper.isConnected = false
                    channelMap.keys.forEach {
                        this@MqttWrapper.unsubscribe(it)
                    }
                    channelMap = mutableMapOf()
                    onDisconnect()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to disconnect")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(
        topic: String,
        qos: Int = 1,
        onMessage: (String) -> Unit,
        onSubscribe: () -> Unit,
    ) {
        channelMap[topic] = onMessage
        try {
            mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    onSubscribe()
                    Log.d(TAG, "Subscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to subscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic: String) {
        try {
            mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Unsubscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to unsubscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(
        topic: String,
        msg: String,
        qos: Int = 1,
        retained: Boolean = false,
        onPublish: () -> Unit,
    ) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    onPublish()
                    Log.d(TAG, "$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to publish $msg to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}
