package io.github.alemazzo.sushime.ui.screens.creation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.alemazzo.sushime.model.database.restaurants.Restaurant
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.utils.getDatabase

class CreationViewModel(application: Application) : AndroidViewModel(application) {
    private val restaurantsRepository = RestaurantsRepository(getDatabase())
    fun getRestaurant(restaurantId: Int): LiveData<Restaurant> =
        restaurantsRepository.getById(restaurantId)
}
