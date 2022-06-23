package io.github.alemazzo.sushime.ui.screens.splash.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.splash.viewmodel.SplashViewModel
import io.github.alemazzo.sushime.utils.withIOContext

@ExperimentalMaterial3Api
@Composable
fun LoadDataAndChangeScreenAtTheEnd(
    splashViewModel: SplashViewModel,
    navController: NavHostController,
) {
    LaunchedEffect(true) {
        withIOContext {
            splashViewModel.load()
        }
        navController.backQueue.clear()
        if (splashViewModel.hasAlreadyBeenRegistered()) {
            Routes.RestaurantsRoute.navigate(navController)
        } else {
            Routes.InfoGetRoute.navigate(navController)
        }
    }
}
