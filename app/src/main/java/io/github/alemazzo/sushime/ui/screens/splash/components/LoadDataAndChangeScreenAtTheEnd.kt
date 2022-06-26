package io.github.alemazzo.sushime.ui.screens.splash.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.splash.viewmodel.SplashViewModel
import io.github.alemazzo.sushime.utils.Run
import io.github.alemazzo.sushime.utils.getActivity
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.qr.getRestaurantIdFromQrCode
import io.github.alemazzo.sushime.utils.qr.getTableIdFromQrCode
import io.github.alemazzo.sushime.utils.qr.isRestaurantQrCode
import io.github.alemazzo.sushime.utils.qr.isTableQrCode

@ExperimentalMaterial3Api
@Composable
fun LoadDataAndChangeScreenAtTheEnd(
    navController: NavHostController,
    splashViewModel: SplashViewModel = getViewModel(),
) {
    val context = LocalContext.current
    Run {
        splashViewModel.load()
        navController.backQueue.clear()

        context.getActivity()?.intent?.data?.let { data ->
            val dataContent = data.toString().split("://")[1]
            when {
                isRestaurantQrCode(dataContent) -> {
                    val restaurantId = getRestaurantIdFromQrCode(dataContent)!!
                    Routes.RestaurantInfoRoute.navigate(navController, restaurantId)
                }
                isTableQrCode(dataContent) -> {
                    val tableId = getTableIdFromQrCode(dataContent)
                }
                splashViewModel.hasAlreadyBeenRegistered() -> {
                    Routes.RestaurantsRoute.navigate(navController)
                }
                else -> {
                    Routes.InfoGetRoute.navigate(navController)
                }
            }
            return@Run
        }

        if (splashViewModel.hasAlreadyBeenRegistered()) {
            Routes.RestaurantsRoute.navigate(navController)
        } else {
            Routes.InfoGetRoute.navigate(navController)
        }
    }
}
