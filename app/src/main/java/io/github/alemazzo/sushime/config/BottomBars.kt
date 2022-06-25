package io.github.alemazzo.sushime.config

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.navigation.screen.bottombar.BottomBar
import io.github.alemazzo.sushime.navigation.screen.bottombar.BottomBarItemInfo

@ExperimentalMaterial3Api
object BottomBars {

    object NavigateBottomBar : BottomBar(
        listOf(
            BottomBarItemInfo(
                route = Routes.RestaurantsRoute,
                imageVector = Icons.Filled.Restaurant,
                title = "Restaurants"
            ),
            BottomBarItemInfo(
                route = Routes.JoinRoute,
                imageVector = Icons.Filled.QrCodeScanner,
                title = "Join"
            ),
            BottomBarItemInfo(
                route = Routes.OrdersRoute,
                imageVector = Icons.Filled.List,
                title = "Orders"
            ),
            BottomBarItemInfo(
                route = Routes.SettingsRoute,
                imageVector = Icons.Filled.Settings,
                title = "Settings"
            ),
        )
    )

    object OrderBottomBar : BottomBar(
        listOf(
            BottomBarItemInfo(
                route = Routes.OrderMenuRoute,
                imageVector = Icons.Filled.Menu,
                title = "Menu",

                ),
            BottomBarItemInfo(
                route = Routes.OrderCartRoute,
                imageVector = Icons.Filled.ShoppingCart,
                title = "Cart"
            )
        )
    )
}
