package io.github.alemazzo.sushime

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.ui.navigation.BottomBar
import io.github.alemazzo.sushime.ui.navigation.BottomBarItemInfo

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
                route = Routes.SettingsRoute,
                imageVector = Icons.Filled.Settings,
                title = "Settings"
            ),
        )
    )
}
