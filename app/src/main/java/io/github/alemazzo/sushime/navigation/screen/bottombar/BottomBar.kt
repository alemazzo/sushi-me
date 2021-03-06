package io.github.alemazzo.sushime.navigation.screen.bottombar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.navigation.routing.Route

@ExperimentalMaterial3Api
open class BottomBar(val items: List<BottomBarItemInfo>) {
    @Composable
    fun Get(
        currentRoute: Route,
        navigator: NavHostController,
        args: Map<String, String> = mapOf(),
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            items.forEach {
                NavigationBarItem(
                    selected = it.route == currentRoute,
                    onClick = { it.route.navigate(navigator, args) },
                    label = { Text(it.title) },
                    icon = {
                        Icon(
                            imageVector = it.imageVector,
                            contentDescription = it.title
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}
