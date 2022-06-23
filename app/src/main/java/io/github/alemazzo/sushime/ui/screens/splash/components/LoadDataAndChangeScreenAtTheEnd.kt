package io.github.alemazzo.sushime.ui.screens.splash.components

import android.net.Uri
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.splash.viewmodel.SplashViewModel
import io.github.alemazzo.sushime.utils.getActivity
import io.github.alemazzo.sushime.utils.qr.isRestaurantQrCode
import io.github.alemazzo.sushime.utils.withIOContext

@ExperimentalMaterial3Api
@Composable
fun LoadDataAndChangeScreenAtTheEnd(
    splashViewModel: SplashViewModel,
    navController: NavHostController,
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        withIOContext {
            splashViewModel.load()
        }
        navController.backQueue.clear()
        val data: Uri? = context.getActivity()?.intent?.data
        if (data == null) {
            if (splashViewModel.hasAlreadyBeenRegistered()) {
                Routes.RestaurantsRoute.navigate(navController)
            } else {
                Routes.InfoGetRoute.navigate(navController)
            }
        } else {
            Log.d("DEEPLINK", "DATA = $data")
            val dataContent = data.toString().split("://")[1]
            Log.d("DEEPLINK", "DATACONTENT = $dataContent")
            if (isRestaurantQrCode(dataContent)) {
                val restaurantId = dataContent.split("/")[1].toInt()
                Routes.RestaurantInfoRoute.navigate(navController, restaurantId)
            } else if (splashViewModel.hasAlreadyBeenRegistered()) {
                Routes.RestaurantsRoute.navigate(navController)
            } else {
                Routes.InfoGetRoute.navigate(navController)
            }
        }
    }
}
