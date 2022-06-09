package io.github.alemazzo.sushime.ui.paging

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.navbar.Navbar
import io.github.alemazzo.sushime.ui.navigation.routing.Route
import io.github.alemazzo.sushime.ui.navigation.utilities.navigate

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
fun BottomBar(currentRoute: Route?, navController: NavHostController) {
    if (currentRoute == null) return
    Navbar.all()
        .firstOrNull { it.routes.contains(currentRoute) }
        ?.apply {
            NavigationBar {
                routes.forEach {
                    BottomBarItemFromRoute(
                        element = it.navBarItemInfo!!,
                        isSelected = it == currentRoute,
                        onClick = { navController.navigate(it) }
                    )
                }
            }
        }
}
