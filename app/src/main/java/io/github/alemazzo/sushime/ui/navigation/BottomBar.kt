package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

@ExperimentalMaterial3Api
data class BottomBarItemInfo constructor(
    val route: Route,
    val imageVector: ImageVector,
    val title: String,
)

@ExperimentalMaterial3Api
open class BottomBar(private val routes: List<BottomBarItemInfo>) {
    @Composable
    fun Get(currentRoute: Route, navigator: NavHostController) {
        NavigationBar {
            routes.forEach {
                NavigationBarItem(
                    selected = it.route == currentRoute,
                    onClick = { it.route.navigate(navigator) },
                    label = { Text(it.title) },
                    icon = {
                        Icon(
                            imageVector = it.imageVector,
                            contentDescription = it.title
                        )
                    }
                )
            }
        }
    }
}
