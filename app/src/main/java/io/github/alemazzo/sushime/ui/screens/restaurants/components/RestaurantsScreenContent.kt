package io.github.alemazzo.sushime.ui.screens.restaurants.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel.RestaurantsScreenViewModel
import io.github.alemazzo.sushime.utils.qr.getRestaurantIdFromQrCodeContent
import io.github.alemazzo.sushime.utils.qr.getSushimeQRCodeContent
import io.github.alemazzo.sushime.utils.qr.isRestaurantQrCode
import io.github.alemazzo.sushime.utils.qr.isSushimeQRCode

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
            Log.d("TEST", result)
            if (!isSushimeQRCode(result)) {
                Toast.makeText(context, "Not a Sushime QR Code", Toast.LENGTH_LONG).show()
            } else {
                val content = getSushimeQRCodeContent(result)!!
                if (isRestaurantQrCode(content)) {
                    val restaurantId = getRestaurantIdFromQrCodeContent(content)
                    Routes.RestaurantInfoRoute.navigate(
                        navigator = navController,
                        restaurantId = restaurantId!!
                    )
                }
            }
        }
    )
    RestaurantsList(navController, restaurantsScreenViewModel, padding, isQRScannerVisible.not())
}
