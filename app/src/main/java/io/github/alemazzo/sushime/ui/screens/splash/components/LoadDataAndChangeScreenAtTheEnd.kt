package io.github.alemazzo.sushime.ui.screens.splash.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.routing.InfoGetRoute
import io.github.alemazzo.sushime.ui.navigation.routing.RestaurantsRoute
import io.github.alemazzo.sushime.ui.screens.splash.viewmodel.SplashViewModel

@ExperimentalMaterial3Api
@Composable
fun LoadDataAndChangeScreenAtTheEnd(
    splashViewModel: SplashViewModel,
    navController: NavHostController,
) {
    LaunchedEffect(true) {
        splashViewModel.load()
        navController.backQueue.clear()
        if (splashViewModel.hasAlreadyBeenRegistered()) {
            RestaurantsRoute.navigate(navController)
        } else {
            InfoGetRoute.navigate(navController)
        }
    }
}
