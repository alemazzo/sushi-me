package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import io.github.alemazzo.sushime.ui.screens.restaurant_info.RestaurantInfoScreen

@ExperimentalMaterial3Api
object RestaurantInfoRoute : Route(
    path = "restaurant-info/{restaurantName}",
    arguments = listOf(Pair("restaurantName", NavType.StringType)),
    screen = { navController, padding, args ->
        RestaurantInfoScreen(navController, padding, args!!.getString("restaurantName")!!)
    }
) {
    fun navigate(navController: NavHostController, restaurantName: String) {
        navController.navigate(this.path.replace("{restaurantName}", restaurantName))
    }
}
