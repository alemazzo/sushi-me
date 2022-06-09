package io.github.alemazzo.sushime.ui.paging

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import io.github.alemazzo.sushime.ui.navigation.routing.Screen
import io.github.alemazzo.sushime.ui.navigation.utilities.rememberCurrentRoute
import io.github.alemazzo.sushime.ui.theme.SashimeTheme

/**
 * The default container of each page of the application.
 * Manage the current route state in order to show/hide the
 * bottom bar.
 */
@ExperimentalMaterial3Api
@Composable
fun App(content: Screen) {
    SashimeTheme {
        val navController = rememberNavController()
        val currentRoute = rememberCurrentRoute(navController)
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    currentRoute = currentRoute
                )
            },
            bottomBar = {
                BottomBar(
                    currentRoute = currentRoute,
                    navController = navController
                )
            },
            content = { content(navController, it, null) }
        )
    }
}
