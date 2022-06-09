package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.navbar.NavBarItemInfo
import io.github.alemazzo.sushime.ui.screens.settings.SettingsScreen


@ExperimentalMaterial3Api
object SettingsRoute : Route(
    path = "settings",
    screen = { navController, padding, _ ->
        SettingsScreen(navController, padding)
    },
    navBarItemInfo = NavBarItemInfo(
        title = "Settings",
        imageVector = Icons.Filled.Settings
    )
) {
    fun navigate(navController: NavHostController) {
        navController.navigate(path)
    }
}
