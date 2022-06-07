package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.ui.screens.infoget.InfoGetScreen
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

    /** the information about his representation in the navbar,
    only if it's a route that should appear in a navbar. */
    val navBarItemInfo: NavBarItemInfo? = null

) {

    companion object {
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
        screen = { navController, padding -> SplashScreen(navController, padding) },
    )

    /**
     * The screen where we ask to the user his data.
     *
     * Only displayed at the first start-up of the App.
     */
    object InfoGet : Route(path = "info-get", screen = { navController, padding ->
        InfoGetScreen(
            navController,
            padding
        )
    })

    object Restaurants : Route(
        path = "restaurants",
        screen = { navController, padding ->
            {}
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Restaurants",
            iconResourceId = R.drawable.ic_launcher_background
        )
    )

    object Join : Route(
        path = "join",
        screen = { navController, padding ->
            {}
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Join",
            iconResourceId = R.drawable.ic_launcher_background
        )
    )

    object Settings : Route(
        path = "settings",
        screen = { navController, padding ->
            {}
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Settings",
            iconResourceId = R.drawable.ic_launcher_background
        )
    )

    object RestaurantInfo : Route(
        path = "restaurant-info",
        screen = { navController, padding ->
            {}
        }
    )

    object CreateTable : Route(
        path = "create-table",
        screen = { navController, padding ->
            {}
        }
    )

    object OrderMenu : Route(
        path = "order-menu",
        screen = { navController, padding ->
            {}
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Menu",
            iconResourceId = R.drawable.ic_launcher_background
        )
    )

    object OrderCart : Route(
        path = "order-cart",
        screen = { navController, padding ->
            {}
        },
        navBarItemInfo = NavBarItemInfo(
            title = "Cart",
            iconResourceId = R.drawable.ic_launcher_background
        )
    )

    object Item : Route(
        path = "item",
        screen = { navController, padding ->
            {}
        }
    )
}
