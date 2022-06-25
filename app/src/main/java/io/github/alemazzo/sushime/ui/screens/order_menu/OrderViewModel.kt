package io.github.alemazzo.sushime.ui.screens.order_menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.repositories.categories.CategoriesRepository
import io.github.alemazzo.sushime.model.repositories.dishes.DishesRepository
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.model.store.user.UserDataStore
import io.github.alemazzo.sushime.utils.MqttWrapper
import io.github.alemazzo.sushime.utils.getDatabase
import io.github.alemazzo.sushime.utils.launchWithIOContext
import kotlinx.coroutines.flow.first

class SushimeMqtt(application: Application, private val userId: String) {

    private val mqttWrapper: MqttWrapper = MqttWrapper(application)
    fun connect(onConnect: (SushimeMqtt) -> Unit) {
        mqttWrapper.connect { onConnect(this) }
    }

    fun join(tableId: String, onJoin: (SushimeMqtt) -> Unit) {
        mqttWrapper.publish("$tableId/newUser", userId)
    }

    fun makeOrder(order: String, onMake: (SushimeMqtt) -> Unit) {

    }
}

data class SingleOrderItem(val dishId: Int, val quantity: Int)
data class SingleOrder(val items: List<SingleOrderItem>)

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val userDataStore = UserDataStore.getInstance(application)
    private val db = getDatabase()
    lateinit var sushimeMqtt: SushimeMqtt

    val categoriesRepository = CategoriesRepository(db)
    val restaurantsRepository = RestaurantsRepository(db)
    val dishesRepository = DishesRepository(db)

    val orders = mutableListOf<SingleOrder>()

    init {
        userDataStore.getEmail().let {
            launchWithIOContext {
                sushimeMqtt = SushimeMqtt(application, it.first()!!)
            }
        }
    }

}
