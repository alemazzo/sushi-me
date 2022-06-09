package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.ui.navigation.navbar.NavBarItemInfo

@ExperimentalMaterial3Api
object OrderMenuRoute : Route(
    path = "order-menu",
    screen = { navController, padding, _ ->
        {}
    },
    navBarItemInfo = NavBarItemInfo(
        title = "Menu",
        imageVector = Icons.Filled.RestaurantMenu
    )
)
