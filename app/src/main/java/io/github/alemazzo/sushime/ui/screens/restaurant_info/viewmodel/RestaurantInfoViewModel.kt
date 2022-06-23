package io.github.alemazzo.sushime.ui.screens.restaurant_info.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.AppDatabase

class RestaurantInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val ristorantiDao = db.restaurantsDao()

}
