package io.github.alemazzo.sushime.config

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.ui.screens.infoget.InfoGetScreen
import io.github.alemazzo.sushime.ui.screens.join.JoinScreen
import io.github.alemazzo.sushime.ui.screens.orders.OrdersScreen
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


    object RestaurantsRoute : Route(
        path = "restaurants",
        screen = RestaurantsScreen
    )

    object JoinRoute : Route(
        path = "join",
        screen = JoinScreen
    )

    object OrdersRoute : Route(
        path = "orders",
        screen = OrdersScreen
    )

    object SettingsRoute : Route(
        path = "settings",
        screen = SettingsScreen
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

        // Startup
        SplashRoute,
        InfoGetRoute,

        // Main
        RestaurantsRoute,
        JoinRoute,
        OrdersRoute,
        SettingsRoute,

        // Specific
        RestaurantInfoRoute
    )
}
