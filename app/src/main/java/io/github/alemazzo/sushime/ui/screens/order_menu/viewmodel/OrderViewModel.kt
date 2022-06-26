package io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.orders.SingleOrder
import io.github.alemazzo.sushime.model.orders.SingleOrderItem
import io.github.alemazzo.sushime.model.repositories.categories.CategoriesRepository
import io.github.alemazzo.sushime.model.repositories.dishes.DishesRepository
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.model.store.user.UserDataStore
import io.github.alemazzo.sushime.utils.getDatabase
import io.github.alemazzo.sushime.utils.launchWithIOContext
import io.github.alemazzo.sushime.utils.mqtt.SushimeMqtt
import kotlinx.coroutines.flow.first

class OrderViewModel(private val context: Application) : AndroidViewModel(context) {

    private val userDataStore = UserDataStore.getInstance(context)
    private val db = getDatabase()
    val categoriesRepository = CategoriesRepository(db)
    val restaurantsRepository = RestaurantsRepository(db)
    val dishesRepository = DishesRepository(db)


    var sushimeMqtt: SushimeMqtt? = null

    // Table info
    var tableId: String? = null
    var isCreator by mutableStateOf(false)

    // The order of this client
    val order = mutableMapOf<Int, SingleOrderItem>()

    // User that joined the room (must be unique)
    val users = mutableSetOf<String>()

    // The arrived orders
    val orders = mutableListOf<SingleOrder>()


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
            sushimeMqtt = SushimeMqtt(context, userDataStore.getEmail().first()!!)
            sushimeMqtt!!.connect {
                onConnected(it)
            }
        }
    }

    fun createTable(tableId: String, onCreated: () -> Unit = {}) {
        connect(tableId, true) { mqtt ->
            mqtt.joinAsCreator(
                tableId = tableId,
                onNewUser = { users.add(it) },
                onNewOrderSent = { orders.add(SingleOrder.fromString(it)) }
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

    fun makeOrder(onMake: () -> Unit = {}) {
        launchWithIOContext {
            sushimeMqtt!!.makeOrderAndDisconnect(order.values.toList().toString()) {
                onMake()
            }
        }
    }

}
