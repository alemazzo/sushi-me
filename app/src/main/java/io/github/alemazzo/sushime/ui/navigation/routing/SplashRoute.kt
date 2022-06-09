package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.screens.splash.SplashScreen

/**
 * The Splash Screen.
 *
 * Used to load data at startup and then changed.
 */
@ExperimentalMaterial3Api
object SplashRoute : Route(
    path = "splash",
    screen = { navController, padding, _ -> SplashScreen(navController, padding) },
) {
    fun navigate(navController: NavHostController) {
        navController.navigate(path)
    }
}
