package io.github.alemazzo.sushime.ui.screens.restaurant_info

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.restaurant_info.components.RestaurantInfoScreenContent
import io.github.alemazzo.sushime.ui.screens.restaurant_info.viewmodel.RestaurantInfoViewModel
import io.github.alemazzo.sushime.utils.getViewModel

@ExperimentalMaterial3Api
object RestaurantInfoScreen : Screen() {

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(Routes.RestaurantsRoute, navigator)
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val restaurantName =
            arguments?.getString(Routes.RestaurantInfoRoute.restaurantNameArgName)!!
        val restaurantInfoViewModel: RestaurantInfoViewModel = getViewModel()
        val ristorante by restaurantInfoViewModel.restaurantsRepository.getByName(restaurantName)
            .observeAsState()
        ristorante?.let {
            RestaurantInfoScreenContent(navigator, paddingValues, restaurantInfoViewModel, it)
        }

    }

}
