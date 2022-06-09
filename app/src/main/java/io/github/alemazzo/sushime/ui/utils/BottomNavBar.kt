package io.github.alemazzo.sushime.ui.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.Navbar
import io.github.alemazzo.sushime.ui.navigation.routing.Route
import io.github.alemazzo.sushime.utils.NavBarItemFromRoute
import io.github.alemazzo.sushime.utils.Navigate

/**
 * Automatic detected navbar from currentRoute.
 * If a route is present and it's part of a Navbar
 * that navbar is display, otherwise no Navbar is displayed.
 *
 * @param currentRoute the actual route, if present
 * @param navController the navigation controller
 */
@ExperimentalMaterial3Api
@Composable
fun BottomNavBar(currentRoute: Route?, navController: NavHostController) {
    if (currentRoute == null) return
    Navbar.all()
        .firstOrNull { it.routes.contains(currentRoute) }
        ?.apply {
            NavigationBar {
                routes.forEach {
                    NavBarItemFromRoute(
                        element = it.navBarItemInfo!!,
                        isSelected = it == currentRoute,
                        onClick = { navController.Navigate(it) }
                    )
                }
            }
        }
}
