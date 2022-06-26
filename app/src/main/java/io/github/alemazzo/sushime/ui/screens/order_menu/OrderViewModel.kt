package io.github.alemazzo.sushime.ui.screens.order_menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.repositories.categories.CategoriesRepository
import io.github.alemazzo.sushime.model.repositories.dishes.DishesRepository
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.model.store.user.UserDataStore
import io.github.alemazzo.sushime.utils.MqttWrapper
import io.github.alemazzo.sushime.utils.getDatabase
import io.github.alemazzo.sushime.utils.launchWithIOContext
import io.github.alemazzo.sushime.utils.launchWithMainContext
import kotlinx.coroutines.flow.first

class SushimeMqtt(private val application: Application, private val userId: String) {

    private var mqttWrapper: MqttWrapper = MqttWrapper(application)
    private var tableId: String? = null

    fun connect(onConnect: (SushimeMqtt) -> Unit = {}) {
        mqttWrapper.connect { onConnect(this) }
    }

    private fun _joinAsCreator(
        tableId: String,
        onNewOrderSent: (String) -> Unit,
        onNewUser: (String) -> Unit,
        onJoin: (SushimeMqtt) -> Unit = {},
    ) {
        mqttWrapper.connect {
            this.tableId = tableId
            mqttWrapper.subscribe("$tableId/newUser", onMessage = onNewUser) {
                mqttWrapper.subscribe(tableId, onMessage = onNewOrderSent) {
                    mqttWrapper.publish("$tableId/newUser", userId) {
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
        onJoin: (SushimeMqtt) -> Unit = {},
    ) {
        if (mqttWrapper.isConnected) {
            mqttWrapper.disconnect {
                this._joinAsCreator(tableId, onNewOrderSent, onNewUser, onJoin)
            }
        } else {
            this._joinAsCreator(tableId, onNewOrderSent, onNewUser, onJoin)
        }
    }

    private fun _join(tableId: String, onJoin: (SushimeMqtt) -> Unit = {}) {
        mqttWrapper.connect {
            this.tableId = tableId
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

    fun makeOrder(order: String, onMake: (SushimeMqtt) -> Unit = {}) {
        mqttWrapper.publish("$tableId/menu", order) {
            onMake(this)
        }
    }
}

data class SingleOrderItem(val dishId: Int, var quantity: Int)
data class SingleOrder(val items: List<SingleOrderItem>) {

    companion object {
        fun fromString(string: String): SingleOrder {
            return SingleOrder(listOf())
        }
    }
}

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val _application = application
    private val userDataStore = UserDataStore.getInstance(application)
    private val db = getDatabase()
    var sushimeMqtt: SushimeMqtt? = null

    val categoriesRepository = CategoriesRepository(db)
    val restaurantsRepository = RestaurantsRepository(db)
    val dishesRepository = DishesRepository(db)

    val users = mutableListOf<String>()
    val orders = mutableListOf<SingleOrder>()

    val order = mutableMapOf<Int, SingleOrderItem>()

    var tableId: String? = null

    fun getDishAmount(dish: Dish): Int {
        return order[dish.id]?.quantity ?: 0
    }

    fun decreaseDishFromOrder(dish: Dish) {
        if (order[dish.id]!!.quantity == 1) {
            order.remove(dish.id)
        } else {
            order[dish.id]!!.quantity--
        }
    }

    fun increaseDishToOrder(dish: Dish) {
        if (order.containsKey(dish.id)) {
            order[dish.id]!!.quantity++
        } else {
            order[dish.id] = SingleOrderItem(dish.id, 1)
        }
    }

    fun createTable(tableId: String, onCreated: () -> Unit = {}) {
        this.tableId = tableId
        launchWithIOContext {
            sushimeMqtt = SushimeMqtt(_application, userDataStore.getEmail().first()!!)
            sushimeMqtt!!.connect {
                sushimeMqtt!!.joinAsCreator(
                    tableId = tableId,
                    onNewUser = { users.add(it) },
                    onNewOrderSent = { orders.add(SingleOrder.fromString(it)) }
                ) {
                    onCreated()
                }
            }
        }
    }

    fun joinTable(tableId: String, onJoin: () -> Unit = {}) {
        this.tableId = tableId
        launchWithIOContext {
            sushimeMqtt = SushimeMqtt(_application, userDataStore.getEmail().first()!!)
            sushimeMqtt?.let { mqtt ->
                mqtt.connect {
                    mqtt.join(tableId) {
                        launchWithMainContext {
                            onJoin()
                        }
                    }
                }
            }
        }
    }

    fun makeOrder(onMake: () -> Unit = {}) {
        launchWithIOContext {
            sushimeMqtt!!.makeOrder(order.values.toList().toString()) {
                onMake()
            }
        }
    }

}
