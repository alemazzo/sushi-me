package io.github.alemazzo.sushime.utils.mqtt

import android.app.Application
import io.github.alemazzo.sushime.model.orders.SingleOrderItem

class SushimeMqtt(private val application: Application, private val userId: String) {

    private var mqttWrapper: MqttWrapper = MqttWrapper(application)

    fun connect(onConnect: (SushimeMqtt) -> Unit = {}) {
        mqttWrapper.connect { onConnect(this) }
    }

    private fun _joinAsCreator(
        tableId: String,
        onNewOrderSent: (String) -> Unit,
        onNewUser: (String) -> Unit,
        onQuitUser: (String) -> Unit,
        onJoin: (SushimeMqtt) -> Unit = {},
    ) {
        mqttWrapper.connect {
            mqttWrapper.subscribe("$tableId/newUser", onMessage = onNewUser) {
                mqttWrapper.subscribe("$tableId/quitUser", onMessage = onQuitUser) {
                    mqttWrapper.subscribe("$tableId/menu", onMessage = onNewOrderSent) {
                        onJoin(this)
                    }
                }
            }
        }

    }

    fun joinAsCreator(
        tableId: String,
        onNewOrderSent: (String) -> Unit,
        onNewUser: (String) -> Unit,
        onQuitUser: (String) -> Unit,
        onJoin: (SushimeMqtt) -> Unit = {},
    ) {
        if (mqttWrapper.isConnected) {
            mqttWrapper.disconnect {
                this._joinAsCreator(tableId, onNewOrderSent, onNewUser, onQuitUser, onJoin)
            }
        } else {
            this._joinAsCreator(tableId, onNewOrderSent, onNewUser, onQuitUser, onJoin)
        }
    }

    private fun _join(tableId: String, onJoin: (SushimeMqtt) -> Unit = {}) {
        mqttWrapper.connect {
            mqttWrapper.publish("$tableId/newUser", userId) {
                onJoin(this)
            }
        }
    }

    fun join(tableId: String, onJoin: (SushimeMqtt) -> Unit = {}) {
        if (mqttWrapper.isConnected) {
            mqttWrapper.disconnect {
                this._join(tableId, onJoin)
            }
        } else {
            this._join(tableId, onJoin)
        }
    }

    fun makeOrder(
        user: String,
        order: String,
        tableId: String,
        onMake: (SushimeMqtt) -> Unit = {},
    ) {
        val message = "$user,$order"
        mqttWrapper.publish("$tableId/menu", message) {
            onMake(this)
        }
    }

    fun makeOrderAndWaitForFinalMenu(
        user: String,
        order: String,
        tableId: String,
        onMake: (SushimeMqtt) -> Unit = {},
        onFinalMenuReceived: (String) -> Unit = {},
    ) {
        val message = "$user,$order"
        mqttWrapper.publish("$tableId/menu", message) {
            mqttWrapper.subscribe(
                "$tableId/finalMenu",
                onMessage = { onFinalMenuReceived(it) }
            ) {
                onMake(this)
            }
        }
    }

    fun closeTable(
        restaurantId: Int,
        tableId: String,
        orders: List<SingleOrderItem>,
        onClose: () -> Unit = {},
    ) {
        val serializedOrder = orders.joinToString(separator = ",") { "${it.dishId}:${it.quantity}" }
        val message = "$restaurantId,$serializedOrder"
        mqttWrapper.publish("$tableId/finalMenu", message) {
            mqttWrapper.disconnect {
                onClose()
            }
        }
    }


}
