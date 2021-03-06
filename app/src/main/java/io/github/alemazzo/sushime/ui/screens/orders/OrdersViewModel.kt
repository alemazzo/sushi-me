package io.github.alemazzo.sushime.ui.screens.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.repositories.images.ImagesRepository
import io.github.alemazzo.sushime.model.repositories.orders.OrdersRepository
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.utils.getDatabase

class OrdersViewModel(application: Application) : AndroidViewModel(application) {

    private val db = getDatabase()
    val ordersRepository = OrdersRepository(db)
    val restaurantsRepository = RestaurantsRepository(db)
    val imagesRepository = ImagesRepository()
}
