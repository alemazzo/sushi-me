package io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel

import android.app.Application
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.AppDatabase

class RestaurantsScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val ristorantiDao = db.ristorantiDao()

    val listState: LazyListState = LazyListState()
}
