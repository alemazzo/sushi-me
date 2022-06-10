package io.github.alemazzo.sushime.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.ui.theme.SushimeTheme


@ExperimentalMaterial3Api
@Composable
fun App(routes: List<Route>, startingRoute: Route) {
    SushimeTheme {
        val navigator = rememberNavController()
        NavHost(
            navController = navigator,
            startDestination = startingRoute.path
        ) {
            routes.forEach { route ->
                composable(
                    route = route.path,
                    arguments = route.arguments.map {
                        navArgument(it) {
                            type = NavType.StringType
                        }
                    }
                ) {
                    Scaffold(
                        floatingActionButton = { route.screen.FloatingActionButton() },
                        topBar = { route.screen.TopBar() },
                        bottomBar = { route.screen.BottomBar(navigator, route) },
                        content = { padding ->
                            route.screen.Content(
                                navigator = navigator,
                                paddingValues = padding,
                                arguments = it.arguments
                            )
                        }
                    )
                }
            }
        }
    }
}
