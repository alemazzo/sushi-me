package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.utils.NavBarItemFromRoute
import io.github.alemazzo.sushime.utils.navigate

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
                        onClick = { navController.navigate(it) }
                    )
                }
            }
        }
}
