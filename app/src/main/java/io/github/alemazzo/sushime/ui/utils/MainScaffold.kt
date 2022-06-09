package io.github.alemazzo.sushime.ui.utils

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import io.github.alemazzo.sushime.ui.navigation.Screen
import io.github.alemazzo.sushime.ui.navigation.routing.Route
import io.github.alemazzo.sushime.ui.theme.SashimeTheme
import io.github.alemazzo.sushime.utils.rememberCurrentRoute

/**
 * The default container of each page of the application.
 * Manage the current route state in order to show/hide the
 * bottom bar.
 */
@ExperimentalMaterial3Api
@Composable
fun MainScaffold(content: Screen) {
    SashimeTheme {
        val navController = rememberNavController()
        val currentRoute = rememberCurrentRoute(navController)
        Scaffold(
            floatingActionButton = {
                    MyFloatingActionButton(
                        currentRoute = currentRoute
                    )
            },
            bottomBar = {
                BottomNavBar(
                    currentRoute = currentRoute,
                    navController = navController
                )
            },
            content = { content(navController, it, null) }
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun MyFloatingActionButton(currentRoute: Route?) {
    if (currentRoute == null) return
    if (currentRoute.floatingActionButtonInfo == null) return
    if (currentRoute.getViewModel == null) return
    val viewModel = currentRoute.getViewModel.invoke()
    FloatingActionButton(onClick = { viewModel.onFabPress() }) {
        Icon(
            imageVector = currentRoute.floatingActionButtonInfo.defaultIconVector,
            contentDescription = "FAB",
        )
    }
}
