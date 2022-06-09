package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.ui.navigation.NavBarItemInfo

@ExperimentalMaterial3Api
object OrderCartRoute : Route(
    path = "order-cart",
    screen = { navController, padding, _ ->
        {}
    },
    navBarItemInfo = NavBarItemInfo(
        title = "Cart",
        imageVector = Icons.Filled.ShoppingCart
    )
)
