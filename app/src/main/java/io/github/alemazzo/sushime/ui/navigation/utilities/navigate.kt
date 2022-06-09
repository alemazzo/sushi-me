package io.github.alemazzo.sushime.ui.navigation.utilities

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.routing.Route


@ExperimentalMaterial3Api
fun NavHostController.navigate(route: Route) {
    navigate(route.path, builder = {
        launchSingleTop = true
        restoreState = true
    })
}
