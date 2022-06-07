package io.github.alemazzo.sushime.ui.screens.splash.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.Route
import io.github.alemazzo.sushime.ui.screens.splash.viewmodel.SplashViewModel
import io.github.alemazzo.sushime.utils.navigate

@ExperimentalMaterial3Api
@Composable
fun LoadDataAndChangeScreenAtTheEnd(
    splashViewModel: SplashViewModel,
    navController: NavHostController
) {
    LaunchedEffect(true) {
        splashViewModel.load()
        navController.backQueue.clear()
        if (splashViewModel.hasAlreadyBeenRegistered()) {
            navController.navigate(Route.Restaurants)
        } else {
            navController.navigate(Route.InfoGet)
        }
    }
}
