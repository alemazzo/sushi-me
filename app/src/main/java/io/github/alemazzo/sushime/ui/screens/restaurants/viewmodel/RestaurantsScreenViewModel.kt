package io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel

import android.app.Application
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.ui.screens.restaurants.components.RestaurantInfo

class RestaurantsScreenViewModel(application: Application) : AndroidViewModel(application) {

    val listState: LazyListState = LazyListState()

    val items = (1..15).map {
        RestaurantInfo(
            name = "Restaurant $it",
            description = "Via Salvatore Quasimodo 421, Cesena (FC), 4752$it",
            image = R.drawable.example_restaurant_image
        )
    }
}
