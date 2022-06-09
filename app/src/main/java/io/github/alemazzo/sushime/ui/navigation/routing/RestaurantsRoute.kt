package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.NavBarItemInfo
import io.github.alemazzo.sushime.ui.screens.restaurants.RestaurantsScreen
import io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel.RestaurantsScreenViewModel
import io.github.alemazzo.sushime.utils.AndroidViewModelWithFabButton
import io.github.alemazzo.sushime.utils.getViewModel


@ExperimentalMaterial3Api
object RestaurantsRoute : Route(
    path = "restaurants",
    screen = { navController, padding, _ ->
        RestaurantsScreen(
            navController,
            padding
        )
    },
    navBarItemInfo = NavBarItemInfo(
        title = "Restaurants",
        imageVector = Icons.Filled.Restaurant
    ),
    floatingActionButtonInfo = FloatingActionButtonInfo(
        defaultIconVector = Icons.Filled.QrCodeScanner
    ),
    getViewModel = {
        getViewModel<RestaurantsScreenViewModel>() as AndroidViewModelWithFabButton
    }
) {
    fun navigate(navController: NavHostController) {
        navController.navigate(path)
    }
}
