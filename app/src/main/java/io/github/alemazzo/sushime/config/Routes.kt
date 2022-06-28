package io.github.alemazzo.sushime.config

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.ui.screens.creation.CreationScreen
import io.github.alemazzo.sushime.ui.screens.infoget.InfoGetScreen
import io.github.alemazzo.sushime.ui.screens.join.JoinScreen
import io.github.alemazzo.sushime.ui.screens.order_cart.OrderCartScreen
import io.github.alemazzo.sushime.ui.screens.order_final_resume.OrderFinalResumeScreen
import io.github.alemazzo.sushime.ui.screens.order_info.OrderInfoScreen
import io.github.alemazzo.sushime.ui.screens.order_menu.OrderMenuScreen
import io.github.alemazzo.sushime.ui.screens.order_resume.OrderResumeScreen
import io.github.alemazzo.sushime.ui.screens.order_resume_info.OrderResumeInfoScreen
import io.github.alemazzo.sushime.ui.screens.orders.OrdersScreen
import io.github.alemazzo.sushime.ui.screens.restaurant_info.RestaurantInfoScreen
import io.github.alemazzo.sushime.ui.screens.restaurants.RestaurantsScreen
import io.github.alemazzo.sushime.ui.screens.settings.SettingsScreen
import io.github.alemazzo.sushime.ui.screens.splash.SplashScreen
import io.github.alemazzo.sushime.ui.screens.waiting.WaitingScreen

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
    private const val restaurantInfoRouteArgName = "restaurantId"

    object RestaurantInfoRoute : Route(
        path = "restaurant/{$restaurantInfoRouteArgName}",
        arguments = listOf(restaurantInfoRouteArgName),
        screen = RestaurantInfoScreen
    ) {
        val restaurantIdArgName = restaurantInfoRouteArgName
        fun navigate(navigator: NavHostController, restaurantId: Int) {
            navigate(
                navigator = navigator,
                arguments = mapOf(restaurantInfoRouteArgName to restaurantId.toString())
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

    // Order route
    private const val orderMenuRouteArgName = "order-id"

    object OrderMenuRoute : Route(
        path = "order-menu/{$orderMenuRouteArgName}",
        arguments = listOf(orderMenuRouteArgName),
        screen = OrderMenuScreen
    ) {
        val orderMenuOrderIdArgName = orderMenuRouteArgName
        fun navigate(navigator: NavHostController, tableId: String) {
            navigate(
                navigator = navigator,
                arguments = mapOf(orderMenuOrderIdArgName to tableId)
            )
        }
    }

    object OrderCartRoute : Route(
        path = "order-cart/{$orderMenuRouteArgName}",
        arguments = listOf(orderMenuRouteArgName),
        screen = OrderCartScreen
    ) {
        val orderMenuOrderIdArgName = orderMenuRouteArgName
        fun navigate(navigator: NavHostController, tableId: String) {
            navigate(
                navigator = navigator,
                arguments = mapOf(orderMenuOrderIdArgName to tableId)
            )
        }
    }

    object OrderResumeRoute : Route(
        path = "order-resume/{$orderMenuRouteArgName}",
        arguments = listOf(orderMenuRouteArgName),
        screen = OrderResumeScreen
    ) {
        val orderMenuOrderIdArgName = orderMenuRouteArgName
        fun navigate(navigator: NavHostController, tableId: String) {
            navigate(
                navigator = navigator,
                arguments = mapOf(orderMenuOrderIdArgName to tableId)
            )
        }
    }

    const val userIdArgName = "user-id"

    object OrderResumeInfoRoute : Route(
        path = "order-resume-info/{$userIdArgName}",
        arguments = listOf(userIdArgName),
        screen = OrderResumeInfoScreen
    ) {
        const val orderUserIdArgName = userIdArgName
        fun navigate(navigator: NavHostController, userId: String) {
            navigate(
                navigator = navigator,
                arguments = mapOf(userIdArgName to userId)
            )
        }
    }

    object OrderInfoRoute : Route(
        path = "order-info/{$orderMenuRouteArgName}",
        arguments = listOf(orderMenuRouteArgName),
        screen = OrderInfoScreen
    ) {
        val orderIdArgName = orderMenuRouteArgName
        fun navigate(navigator: NavHostController, orderId: Int) {
            navigate(
                navigator = navigator,
                arguments = mapOf(orderIdArgName to orderId.toString())
            )
        }
    }

    object OrderFinalResumeRoute : Route(
        path = "order-final-resume",
        screen = OrderFinalResumeScreen
    )

    object WaitingRoute : Route(
        path = "waiting",
        screen = WaitingScreen
    )

    fun all(): List<Route> = listOf(

        // Startup
        SplashRoute,
        InfoGetRoute,

        // Main
        RestaurantsRoute,
        JoinRoute,
        OrdersRoute,
        OrderInfoRoute,
        SettingsRoute,

        // Specific
        RestaurantInfoRoute,

        // Table
        CreationRoute,

        // Order
        OrderMenuRoute,
        OrderCartRoute,
        OrderResumeRoute,
        OrderResumeInfoRoute,
        OrderFinalResumeRoute,

        WaitingRoute
    )
}
