package io.github.alemazzo.sushime.ui.screens.restaurant_info

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var clicked by mutableStateOf(false)

    @Composable
    override fun FloatingActionButton() {
        androidx.compose.material3.FloatingActionButton(onClick = { clicked = true }) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Create Table"
            )
        }
    }

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
        val restaurantId =
            arguments?.getString(Routes.RestaurantInfoRoute.restaurantIdArgName)!!.toInt()
        val restaurantInfoViewModel: RestaurantInfoViewModel = getViewModel()
        val restaurant by restaurantInfoViewModel.restaurantsRepository.getById(restaurantId)
            .observeAsState()

        if (clicked) {
            restaurant?.let {
                Routes.CreationRoute.navigate(navigator, it.id)
                clicked = false
            }
        }
        restaurant?.let {
            RestaurantInfoScreenContent(navigator, paddingValues, restaurantInfoViewModel, it)
        }

    }

}
