package io.github.alemazzo.sushime.config

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.ui.screens.creation.CreationScreen
import io.github.alemazzo.sushime.ui.screens.infoget.InfoGetScreen
import io.github.alemazzo.sushime.ui.screens.join.JoinScreen
import io.github.alemazzo.sushime.ui.screens.orders.OrdersScreen
import io.github.alemazzo.sushime.ui.screens.restaurant_info.RestaurantInfoScreen
import io.github.alemazzo.sushime.ui.screens.restaurants.RestaurantsScreen
import io.github.alemazzo.sushime.ui.screens.settings.SettingsScreen
import io.github.alemazzo.sushime.ui.screens.splash.SplashScreen

@ExperimentalMaterial3Api
object Routes {

    // Startup Routes
    object SplashRoute : Route(
        path = "splash",
        screen = SplashScreen
    )

    object InfoGetRoute : Route(
        path = "info-get",
        screen = InfoGetScreen
    )


    // Homepage routes
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

    // Specific routes
    private const val restaurantInfoRouteArgName = "restaurantName"

    object RestaurantInfoRoute : Route(
        path = "restaurant/{$restaurantInfoRouteArgName}",
        arguments = listOf(restaurantInfoRouteArgName),
        screen = RestaurantInfoScreen
    ) {
        val restaurantNameArgName = restaurantInfoRouteArgName
        fun navigate(navigator: NavHostController, restaurantName: String) {
            navigate(
                navigator = navigator,
                arguments = mapOf(restaurantInfoRouteArgName to restaurantName)
            )
        }
    }

    // Table routes
    private const val createRouteArgName = "restaurantId"

    object CreationRoute : Route(
        path = "create/{$createRouteArgName}",
        arguments = listOf(createRouteArgName),
        screen = CreationScreen
    ) {
        val createRouteRestaurantIdArgName = createRouteArgName
        fun navigate(navigator: NavHostController, restaurantId: Int) {
            navigate(
                navigator = navigator,
                arguments = mapOf(createRouteArgName to restaurantId.toString())
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
        RestaurantInfoRoute,

        // Table
        CreationRoute
    )
}
