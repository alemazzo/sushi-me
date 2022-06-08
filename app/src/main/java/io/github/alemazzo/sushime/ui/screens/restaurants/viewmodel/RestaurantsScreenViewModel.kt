package io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel

import android.app.Application
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.ui.screens.restaurants.components.RestaurantInfo

class RestaurantsScreenViewModel(application: Application) : AndroidViewModel(application) {

    val listState: LazyListState = LazyListState()

    val items = (1..15).map {
        RestaurantInfo(
            name = "Restaurant $it",
            description = "Description $it",
            image = R.drawable.example_restaurant_image
        )
    }
}
