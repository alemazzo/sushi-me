package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import io.github.alemazzo.sushime.ui.navigation.NavBarItemInfo
import io.github.alemazzo.sushime.ui.navigation.Screen
import io.github.alemazzo.sushime.utils.AndroidViewModelWithFabButton

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
    val navBarItemInfo: NavBarItemInfo? = null,

    val floatingActionButtonInfo: FloatingActionButtonInfo? = null,

    val getViewModel: (@Composable () -> AndroidViewModelWithFabButton)? = null,

    ) {

    companion object {
        /**
         * The entry point of the application.
         */
        val StartingRoute = SplashRoute

        /**
         * Retrieve all the possible Route
         */
        fun all(): List<Route> =
            Route::class.sealedSubclasses
                .map { it.objectInstance as Route }
    }

}

data class FloatingActionButtonInfo(
    val defaultIconVector: ImageVector
)
