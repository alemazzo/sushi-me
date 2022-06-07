package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api


@ExperimentalMaterial3Api
sealed class Navbar(val routes: List<Route>) {
    companion object {
        fun all(): List<Navbar> =
            Navbar::class.sealedSubclasses
                .map { it.objectInstance as Navbar }
    }

    object Navigate : Navbar(
        listOf(
            Route.Restaurants,
            Route.Join,
            Route.Settings
        )
    )

    object Order : Navbar(
        listOf(
            Route.OrderMenu,
            Route.OrderCart
        )
    )
}
