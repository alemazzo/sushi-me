package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavType
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.ui.screens.infoget.InfoGetScreen
import io.github.alemazzo.sushime.ui.screens.join.JoinScreen
import io.github.alemazzo.sushime.ui.screens.restaurants.RestaurantsScreen
import io.github.alemazzo.sushime.ui.screens.splash.SplashScreen

/**
 * The routes of the application.
 */
@ExperimentalMaterial3Api
sealed class Route(

    /** the unique path of the route */
    val path: String,

    /** the Screen to be associated */
    val screen: Screen,

    val arguments: List<Pair<String, NavType<*>>> = listOf(),

    /** the information about his representation in the navbar,
    only if it's a route that should appear in a navbar. */
    val navBarItemInfo: NavBarItemInfo? = null

) {

    companion object {

        /**
         * The entry point of the application.
         */
        val StartingRoute = Route.Splash

        /**
         * Retrieve all the possible Route
         */
        fun all(): List<Route> =
            Route::class.sealedSubclasses
                .map { it.objectInstance as Route }
    }

    /**
     * The Splash Screen.
     *
     * Used to load data at startup and then changed.
     */
    object Splash : Route(
        path = "splash",
        screen = { navController, padding, _ -> SplashScreen(navController, padding) },
    )

    /**
     * The screen where we ask to the user his data.
     *
     * Only displayed at the first start-up of the App.
     */
    object InfoGet : Route(path = "info-get", screen = { navController, padding, _ ->
        InfoGetScreen(
            navController,
            padding
        )
    })

    object Restaurants : Route(
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
        )
    )

    object Join : Route(
        path = "join",
        screen = { navController, padding, _ ->
            JoinScreen(navController, padding)
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Join",
            imageVector = Icons.Filled.QrCodeScanner
        )
    )

    object Settings : Route(
        path = "settings",
        screen = { navController, padding, _ ->
            {}
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Settings",
            imageVector = Icons.Filled.Settings
        )
    )

    object RestaurantInfo : Route(
        path = "restaurant-info",
        screen = { navController, padding, _ ->
            {}
        }
    )

    object CreateTable : Route(
        path = "create-table",
        screen = { navController, padding, _ ->
            {}
        }
    )

    object OrderMenu : Route(
        path = "order-menu",
        screen = { navController, padding, _ ->
            {}
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Menu",
            imageVector = Icons.Filled.RestaurantMenu
        )
    )

    object OrderCart : Route(
        path = "order-cart",
        screen = { navController, padding, _ ->
            {}
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Cart",
            imageVector = Icons.Filled.ShoppingCart
        )
    )

    object Item : Route(
        path = "item",
        screen = { navController, padding, _ ->
            {}
        }
    )
}
