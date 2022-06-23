package io.github.alemazzo.sushime.ui.screens.restaurants.components

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel.RestaurantsScreenViewModel
import io.github.alemazzo.sushime.utils.qr.getRestaurantIdFromQrCode
import io.github.alemazzo.sushime.utils.qr.isRestaurantQrCode

@ExperimentalMaterial3Api
@Composable
fun RestaurantsScreenContent(
    navController: NavHostController,
    padding: PaddingValues,
    restaurantsScreenViewModel: RestaurantsScreenViewModel,
    isQRScannerVisible: Boolean,
    onQRScannerVisibilityChange: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    ShowQRScannerWhenFabPressed(
        showPopup = isQRScannerVisible,
        onEnd = {
            onQRScannerVisibilityChange(false)
        },
        onResult = { result ->
            if (isRestaurantQrCode(result)) {
                val restaurantId = getRestaurantIdFromQrCode(result)
                Routes.RestaurantInfoRoute.navigate(
                    navigator = navController,
                    restaurantId = restaurantId!!
                )
            } else {
                Toast.makeText(context, "Not a Sushime QR Code", Toast.LENGTH_LONG).show()
            }
        }
    )
    RestaurantsList(navController, restaurantsScreenViewModel, padding, isQRScannerVisible.not())
}
