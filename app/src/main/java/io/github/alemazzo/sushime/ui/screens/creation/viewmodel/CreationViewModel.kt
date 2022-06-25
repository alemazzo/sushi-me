package io.github.alemazzo.sushime.ui.screens.creation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.utils.getDatabase

class CreationViewModel(application: Application) : AndroidViewModel(application) {

    private val db = getDatabase()
    val restaurantsRepository = RestaurantsRepository(db)

}
