package io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.database.dishes_in_orders.DishInOrder
import io.github.alemazzo.sushime.model.database.orders.Order
import io.github.alemazzo.sushime.model.orders.SingleOrder
import io.github.alemazzo.sushime.model.orders.SingleOrderItem
import io.github.alemazzo.sushime.model.repositories.categories.CategoriesRepository
import io.github.alemazzo.sushime.model.repositories.dishes.DishesRepository
import io.github.alemazzo.sushime.model.repositories.dishes_in_orders.DishesInOrdersRepository
import io.github.alemazzo.sushime.model.repositories.orders.OrdersRepository
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.model.store.user.UserDataStore
import io.github.alemazzo.sushime.utils.getDatabase
import io.github.alemazzo.sushime.utils.launchWithIOContext
import io.github.alemazzo.sushime.utils.mqtt.SushimeMqtt
import kotlinx.coroutines.flow.first

class OrderViewModel(private val context: Application) : AndroidViewModel(context) {

    private val userDataStore = UserDataStore.getInstance(context)
    private val db = getDatabase()
    val ordersRepository = OrdersRepository(db)
    val dishesInOrdersRepository = DishesInOrdersRepository(db)
    val categoriesRepository = CategoriesRepository(db)
    val restaurantsRepository = RestaurantsRepository(db)
    val dishesRepository = DishesRepository(db)


    var sushimeMqtt: SushimeMqtt? = null

    // Table info
    var userId: String? = null
    var tableId: String? = null
    var isCreator by mutableStateOf(false)

    // The order of this client
    val order = mutableStateMapOf<Int, SingleOrderItem>()

    // User that joined the room (must be unique)
    val users = mutableStateListOf<String>()

    // The arrived orders
    val orders = mutableStateMapOf<String, SingleOrder>()


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


    private fun connect(tableId: String, isCreator: Boolean, onConnected: (SushimeMqtt) -> Unit) {
        this.isCreator = isCreator
        this.tableId = tableId
        launchWithIOContext {
            userDataStore.getEmail().first()?.let {
                userId = it
                sushimeMqtt = SushimeMqtt(context, it)
                sushimeMqtt!!.connect { mqtt ->
                    onConnected(mqtt)
                }
            }
        }
    }

    private fun handleNewOrderSent(order: String) {
        val user = order.split(",")[0]
        val parsedOrder = order.removePrefix("$user,")
        Log.d("TEST", "Handle New Order: user = $user, parsedOrder = $parsedOrder")
        orders[user] = SingleOrder.loadFromString(parsedOrder)
    }

    fun createTable(tableId: String, onCreated: () -> Unit = {}) {
        connect(tableId, true) { mqtt ->
            mqtt.joinAsCreator(
                tableId = tableId,
                onNewUser = { if (!users.contains(it)) users.add(it) },
                onQuitUser = { if (users.contains(it)) users.remove(it) },
                onNewOrderSent = { handleNewOrderSent(it) }
            ) {
                onCreated()
            }
        }
    }

    fun joinTable(tableId: String, onJoin: () -> Unit = {}) {
        connect(tableId, false) { mqtt ->
            mqtt.join(tableId) {
                onJoin()
            }
        }
    }

    private fun saveOrderToDb(order: SingleOrder) {
        val ord = Order(0, System.currentTimeMillis(), 1)
        val id = ordersRepository.insert(ord).toInt()
        order.items.forEach { item ->
            val dishInOrder = DishInOrder(item.dishId, id, item.quantity)
            dishesInOrdersRepository.insert(dishInOrder)
        }
        Log.d("DBINFO", "ORDERID = $id")
    }

    fun makeOrder(onMake: () -> Unit = {}) {
        launchWithIOContext {
            val finalOrder = SingleOrder(order.values.toList())
            saveOrderToDb(finalOrder)
            sushimeMqtt!!.makeOrder(userId!!, finalOrder.exportToString()) {
                onMake()
            }
        }
    }

}
