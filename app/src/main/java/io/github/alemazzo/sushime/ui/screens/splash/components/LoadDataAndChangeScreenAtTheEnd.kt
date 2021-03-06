package io.github.alemazzo.sushime.ui.screens.splash.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel.OrderViewModel
import io.github.alemazzo.sushime.ui.screens.splash.viewmodel.SplashViewModel
import io.github.alemazzo.sushime.utils.Run
import io.github.alemazzo.sushime.utils.getActivity
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.qr.*


@ExperimentalMaterial3Api
@Composable
fun LoadAllImages() {
    val splashViewModel: SplashViewModel = getViewModel()
    val dishes by splashViewModel.dishesRepository.getAll().observeAsState()
    val restaurants by splashViewModel.restaurantsRepository.getAll().observeAsState()
    val imagesRepository = splashViewModel.imagesRepository
    dishes?.forEach { rememberAsyncImagePainter(imagesRepository.getDishImageLink(it)) }
    restaurants?.forEach { rememberAsyncImagePainter(imagesRepository.getRestaurantImageLink(it)) }
}

@ExperimentalMaterial3Api
@Composable
fun LoadDataAndChangeScreenAtTheEnd(
    navController: NavHostController,
    splashViewModel: SplashViewModel = getViewModel(),
) {
    val context = LocalContext.current
    val orderViewModel: OrderViewModel = getViewModel()
    LoadAllImages()
    Run {
        splashViewModel.load()
        navController.backQueue.clear()
        context.getActivity()?.intent?.data?.let { data ->
            val dataContent = getSushimeQRCodeContent(data.toString())!!
            when {
                isRestaurantQrCode(dataContent) -> {
                    val restaurantId = getRestaurantIdFromQrCodeContent(dataContent)!!
                    Routes.RestaurantInfoRoute.navigate(navController, restaurantId)
                }
                isTableQrCode(dataContent) -> {
                    val tableId = getTableIdFromQrCode(dataContent)!!
                    orderViewModel.joinTable(tableId) {
                        Routes.OrderMenuRoute.navigate(navController, tableId)
                    }
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
