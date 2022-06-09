package io.github.alemazzo.sushime.ui.navigation.utilities

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.alemazzo.sushime.ui.navigation.routing.Route

@ExperimentalMaterial3Api
@Composable
fun rememberCurrentRoute(navController: NavHostController): Route? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = Route.all().firstOrNull {
        it.path == navBackStackEntry?.destination?.route
    }
    return currentRoute
}
