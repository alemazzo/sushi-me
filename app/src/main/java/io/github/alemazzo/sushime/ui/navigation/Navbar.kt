package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api

/**
 * All possible navbars in the application:
 * - Navigate: the main one (Restaurants, Join, Settings).
 * - Order: the one for make an order (Menu, Cart).
 *
 *
 */
@ExperimentalMaterial3Api
sealed class Navbar(
    /**
     * The ordered sequence of Route of the Navbar.
     */
    val routes: List<Route>
    ) {

    companion object {

        /**
         * Retrieve all Navbar objects.
         */
        fun all(): List<Navbar> =
            Navbar::class.sealedSubclasses
                .map { it.objectInstance as Navbar }
    }

    /**
     * The Navbar in the navigation section.
     */
    object Navigate : Navbar(
        listOf(
            Route.Restaurants,
            Route.Join,
            Route.Settings
        )
    )

    /**
     * The Navbar in the order section.
     */
    object Order : Navbar(
        listOf(
            Route.OrderMenu,
            Route.OrderCart
        )
    )
}
