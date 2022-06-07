package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.github.alemazzo.sushime.utils.SimpleRoute

/**
 * The main navigator of the App.
 * Setup all possible routes and start from the SplashScreen.
 *
 * @param navController the navigation controller.
 * @param padding the padding to be passed to all screen in order to fit with appbars.
 */
@ExperimentalMaterial3Api
@Composable
fun AppNavigator(navController: NavHostController, padding: PaddingValues) {
    NavHost(navController = navController, startDestination = Route.Splash.path) {
        Route.all().forEach {
            SimpleRoute(route = it, destination = { it.screen(navController, padding) })
        }
    }
}
