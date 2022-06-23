package io.github.alemazzo.sushime.ui.screens.restaurants

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.routing.RoutePreview
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.restaurants.components.RestaurantsScreenContent
import io.github.alemazzo.sushime.utils.getViewModel
import androidx.compose.material3.FloatingActionButton as FAB

@ExperimentalMaterial3Api
object RestaurantsScreen : Screen() {

    var isQRScannerVisible by mutableStateOf(false)

    @Composable
    override fun FloatingActionButton() {
        FAB(onClick = { isQRScannerVisible = true }) {
            Icon(
                imageVector = Icons.Filled.QrCodeScanner,
                contentDescription = "Scan"
            )
        }
    }

    @Composable
    override fun TopBar() {
        CenterAlignedTopAppBar(
            title = { Text("Restaurants") }
        )
    }

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(currentRoute, navigator)
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        RestaurantsScreenContent(
            navigator,
            paddingValues,
            getViewModel(),
            isQRScannerVisible
        ) { isQRScannerVisible = it }
    }

}


@ExperimentalMaterial3Api
@Preview
@Composable
fun RestaurantsScreenPreview() {
    RoutePreview(route = Routes.RestaurantsRoute)
}
