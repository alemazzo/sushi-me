package io.github.alemazzo.sushime.ui.screens.order_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.repositories.dishes.DishesRepository
import io.github.alemazzo.sushime.model.repositories.images.ImagesRepository
import io.github.alemazzo.sushime.model.repositories.orders.OrdersRepository
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.utils.getDatabase

class OrderInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = getDatabase()
    val restaurantsRepository = RestaurantsRepository(db)
    val dishesRepository = DishesRepository(db)
    val ordersRepository = OrdersRepository(db)

    val imagesRepository = ImagesRepository()
}
