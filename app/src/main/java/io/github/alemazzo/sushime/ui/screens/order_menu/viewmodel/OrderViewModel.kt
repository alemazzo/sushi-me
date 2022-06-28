package io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel

import android.app.Application
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
    var restaurantId: Int? = null
    var tableId: String? = null
    var isCreator by mutableStateOf(false)
    var hasJoined = false

    // The order of this client
    val order = mutableStateMapOf<Int, SingleOrderItem>()

    // User that joined the room (must be unique)
    val users = mutableStateListOf<String>()

    // The arrived orders
    val orders = mutableStateMapOf<String, SingleOrder>()

    fun getUniqueOrdersList(): List<SingleOrderItem> {
        val result = mutableMapOf<Int, Int>()
        orders.values.flatMap { it.items }.forEach {
            if (result.containsKey(it.dishId)) {
                result[it.dishId] = result[it.dishId]!! + it.quantity
            } else {
                result[it.dishId] = it.quantity
            }
        }
        return result.map { SingleOrderItem(it.key, it.value) }
    }

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

    private fun saveOrderToDb(onEnd: () -> Unit = {}) {
        launchWithIOContext {
            val ord = Order(0, System.currentTimeMillis(), this@OrderViewModel.restaurantId!!)
            val id = ordersRepository.insert(ord).toInt()
            order.values.forEach { item ->
                val dishInOrder = DishInOrder(item.dishId, id, item.quantity)
                dishesInOrdersRepository.insert(dishInOrder)
            }
            onEnd()
        }
    }

    private fun handleNewOrderSent(order: String) {
        val user = order.split(",")[0]
        val parsedOrder = order.removePrefix("$user,")
        orders[user] = SingleOrder.loadFromString(parsedOrder)
    }

    private fun handleFinalMenuReceived(finalMenu: String, onEnd: () -> Unit) {
        val restaurantId = finalMenu.split(",")[0].toInt()
        this.restaurantId = restaurantId
        saveOrderToDb {
            onEnd()
        }
    }

    fun resetData() {
        this.tableId = null
        this.restaurantId = null
        this.isCreator = false
        this.hasJoined = false
        this.order.clear()
        this.users.clear()
        this.orders.clear()
    }

    fun createTable(restaurantId: Int, tableId: String, onCreated: () -> Unit = {}) {
        if (hasJoined) {
            onCreated()
            return
        } else {
            hasJoined = true
            launchWithIOContext {
                userDataStore.getEmail().first()?.let {
                    this@OrderViewModel.userId = it
                    this@OrderViewModel.isCreator = true
                    this@OrderViewModel.restaurantId = restaurantId
                    this@OrderViewModel.tableId = tableId
                    sushimeMqtt = SushimeMqtt(context, it)
                    sushimeMqtt!!.joinAsCreator(
                        tableId = tableId,
                        onNewUser = { if (!users.contains(it)) users.add(it) },
                        onQuitUser = { if (users.contains(it)) users.remove(it) },
                        onNewOrderSent = { handleNewOrderSent(it) }
                    ) {
                        onCreated()
                    }
                }
            }
        }

    }

    fun joinTable(tableId: String, onJoin: () -> Unit = {}) {
        if (hasJoined) {
            onJoin()
            return
        } else {
            hasJoined = true
            launchWithIOContext {
                userDataStore.getEmail().first()?.let {
                    this@OrderViewModel.userId = it
                    this@OrderViewModel.isCreator = false
                    this@OrderViewModel.tableId = tableId
                    sushimeMqtt = SushimeMqtt(context, it)
                    sushimeMqtt!!.join(tableId) {
                        onJoin()
                    }
                }
            }
        }

    }

    fun makeOrder(onMake: () -> Unit) {
        launchWithIOContext {
            val finalOrder = SingleOrder(order.values.toList())
            sushimeMqtt!!.makeOrder(
                user = userId!!,
                tableId = tableId!!,
                order = finalOrder.exportToString(),
                onMake = { onMake() }
            )
        }
    }

    fun makeOrderAndWaitForFinalMenu(onMake: () -> Unit = {}, onFinalMenuReceived: () -> Unit) {
        launchWithIOContext {
            val finalOrder = SingleOrder(order.values.toList())
            sushimeMqtt!!.makeOrderAndWaitForFinalMenu(
                user = userId!!,
                tableId = tableId!!,
                order = finalOrder.exportToString(),
                onMake = { onMake() }
            ) {
                handleFinalMenuReceived(it) {
                    resetData()
                    onFinalMenuReceived()
                }
            }
        }
    }

    fun closeTable(onClose: () -> Unit = {}) {
        launchWithIOContext {
            val uniqueList = getUniqueOrdersList()
            sushimeMqtt!!.closeTable(
                tableId = tableId!!,
                restaurantId = this@OrderViewModel.restaurantId!!,
                orders = uniqueList
            ) {
                saveOrderToDb {
                    onClose()
                }
            }
        }
    }

}
