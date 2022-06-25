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
    private var tableId: String? = null

    fun connect(onConnect: (SushimeMqtt) -> Unit = {}) {
        mqttWrapper.connect { onConnect(this) }
    }

    fun joinAsCreator(
        tableId: String,
        onNewOrderSent: (String) -> Unit,
        onNewUser: (String) -> Unit,
        onJoin: (SushimeMqtt) -> Unit = {},
    ) {
        this.tableId = tableId
        mqttWrapper.subscribe("$tableId/newUser", onMessage = onNewUser) {
            onJoin(this)
        }
        mqttWrapper.subscribe(tableId, onMessage = onNewOrderSent) {
            onJoin(this)
        }
    }

    fun join(tableId: String, onJoin: (SushimeMqtt) -> Unit = {}) {
        this.tableId = tableId
        mqttWrapper.publish("$tableId/newUser", userId) {
            onJoin(this)
        }
    }

    fun makeOrder(order: String, onMake: (SushimeMqtt) -> Unit = {}) {
        mqttWrapper.publish("$tableId/menu", order) {
            onMake(this)
        }
    }
}

data class SingleOrderItem(val dishId: Int, val quantity: Int)
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

    fun createMqttInstance(onCreated: (SushimeMqtt) -> Unit = {}) {
        if (sushimeMqtt != null) {
            onCreated(sushimeMqtt!!)
            return
        }
        launchWithIOContext {
            sushimeMqtt = SushimeMqtt(_application, userDataStore.getEmail().first()!!)
            onCreated(sushimeMqtt!!)
        }
    }

    init {

    }


}
