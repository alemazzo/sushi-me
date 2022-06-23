package io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel

import android.app.Application
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.repositories.restaurants.RestaurantsRepository
import io.github.alemazzo.sushime.utils.getDatabase


class RestaurantsScreenViewModel(application: Application) : AndroidViewModel(application) {

    val restaurantsRepository = RestaurantsRepository(getDatabase())
    val listState: LazyListState = LazyListState()
}
