package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.NavBarItemInfo
import io.github.alemazzo.sushime.ui.screens.join.JoinScreen


@ExperimentalMaterial3Api
object JoinRoute : Route(
    path = "join",
    screen = { navController, padding, _ ->
        JoinScreen(navController, padding)
    },
    navBarItemInfo = NavBarItemInfo(
        title = "Join",
        imageVector = Icons.Filled.QrCodeScanner
    )
) {
    fun navigate(navController: NavHostController) {
        navController.navigate(path)
    }
}
