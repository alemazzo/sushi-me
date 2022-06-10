package io.github.alemazzo.sushime.ui.navigation.utilities

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.alemazzo.sushime.ui.navigation.Route

@ExperimentalMaterial3Api
@Composable
fun rememberCurrentRoute(routes: List<Route>, navigator: NavHostController): Route? {
    val navBackStackEntry by navigator.currentBackStackEntryAsState()
    val currentRoute = routes.firstOrNull {
        it.path == navBackStackEntry?.destination?.route
    }
    return currentRoute
}
