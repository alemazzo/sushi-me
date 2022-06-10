package io.github.alemazzo.sushime

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.navigation.Route
import io.github.alemazzo.sushime.ui.screens.infoget.InfoGetScreen
import io.github.alemazzo.sushime.ui.screens.join.JoinScreen
import io.github.alemazzo.sushime.ui.screens.restaurant_info.RestaurantInfoScreen
import io.github.alemazzo.sushime.ui.screens.restaurants.RestaurantsScreen
import io.github.alemazzo.sushime.ui.screens.settings.SettingsScreen
import io.github.alemazzo.sushime.ui.screens.splash.SplashScreen

@ExperimentalMaterial3Api
object Routes {

    object SplashRoute : Route(
        path = "splash",
        screen = SplashScreen
    )

    object InfoGetRoute : Route(
        path = "info-get",
        screen = InfoGetScreen
    )

    object SettingsRoute : Route(
        path = "settings",
        screen = SettingsScreen
    )

    object RestaurantsRoute : Route(
        path = "restaurants",
        screen = RestaurantsScreen
    )

    object JoinRoute : Route(
        path = "join",
        screen = JoinScreen
    )

    private const val restaurantInfoRouteArgName = "restaurantName"

    object RestaurantInfoRoute : Route(
        path = "restaurant/{$restaurantInfoRouteArgName}",
        arguments = listOf(restaurantInfoRouteArgName),
        screen = RestaurantInfoScreen
    ) {
        fun navigate(navigator: NavHostController, restaurantName: String) {
            navigate(
                navigator = navigator,
                arguments = mapOf(restaurantInfoRouteArgName to restaurantName)
            )
        }

    }

    fun all(): List<Route> = listOf(
        SplashRoute,
        InfoGetRoute,
        SettingsRoute,
        RestaurantsRoute,
        JoinRoute,
        RestaurantInfoRoute
    )
}
