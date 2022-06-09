package io.github.alemazzo.sushime.ui.navigation.navbar

import androidx.compose.material3.ExperimentalMaterial3Api
import io.github.alemazzo.sushime.ui.navigation.routing.*

/**
 * All possible navbars in the application:
 * - Navigate: the main one (Restaurants, Join, Settings).
 * - Order: the one for make an order (Menu, Cart).
 */
@ExperimentalMaterial3Api
sealed class Navbar(
    /**
     * The ordered sequence of Route of the Navbar.
     */
    val routes: List<Route>,
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
            RestaurantsRoute,
            JoinRoute,
            SettingsRoute
        )
    )

    /**
     * The Navbar in the order section.
     */
    object Order : Navbar(
        listOf(
            OrderMenuRoute,
            OrderCartRoute
        )
    )
}