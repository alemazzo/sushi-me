package io.github.alemazzo.sushime.ui.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import io.github.alemazzo.sushime.ui.navigation.BottomNavBar
import io.github.alemazzo.sushime.ui.navigation.Screen
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
