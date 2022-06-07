package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.ui.screens.infoget.InfoGetScreen
import io.github.alemazzo.sushime.ui.screens.splash.SplashScreen


@ExperimentalMaterial3Api
sealed class Route(
    val path: String,
    val screen: Screen,
    val navBarItemInfo: NavBarItemInfo? = null
) {
    companion object {
        fun all(): List<Route> =
            Route::class.sealedSubclasses
                .map { it.objectInstance as Route }
    }

    // Splash Screen
    object Splash : Route(
        path = "splash",
        screen = { navController, padding -> SplashScreen(navController, padding) },
    )

    // Get User Info Screen
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
