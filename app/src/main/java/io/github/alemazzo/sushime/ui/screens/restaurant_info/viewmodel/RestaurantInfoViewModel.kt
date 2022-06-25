package io.github.alemazzo.sushime.ui.screens.restaurant_info.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.repositories.categories.CategoriesRepository
import io.github.alemazzo.sushime.model.repositories.dishes.DishesRepository
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.utils.getDatabase

class RestaurantInfoViewModel(application: Application) : AndroidViewModel(application) {

    val restaurantsRepository = RestaurantsRepository(getDatabase())
    val categoriesRepository = CategoriesRepository(getDatabase())
    val dishesRepository = DishesRepository(getDatabase())

}
