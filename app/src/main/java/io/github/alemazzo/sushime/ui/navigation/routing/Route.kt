package io.github.alemazzo.sushime.ui.navigation.routing

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import io.github.alemazzo.sushime.ui.navigation.navbar.NavBarItemInfo
import io.github.alemazzo.sushime.utils.AndroidViewModelWithFabButton

/**
 * The Alias of a Screen of the application.
 * Each screen must accept a navigation controller and
 * the padding values in order to not hide the bottom-bar.
 */
typealias Screen = @Composable (
    navController: NavHostController,
    padding: PaddingValues,
    arguments: Bundle?,
) -> Unit

/**
 * The routes of the application.
 */
@ExperimentalMaterial3Api
sealed class Route(

    /** the unique path of the route */
    val path: String,

    /** the Screen to be associated */
    val screen: Screen,

    val arguments: Map<String, NavType<*>> = mapOf(),

    /** the information about his representation in the navbar,
    only if it's a route that should appear in a navbar. */
    val navBarItemInfo: NavBarItemInfo? = null,

    val floatingActionButtonInfo: FloatingActionButtonInfo? = null,

    val getViewModel: @Composable (() -> AndroidViewModelWithFabButton)? = null,

    ) {

    companion object {

        /**
         * Retrieve all the possible Route
         */
        fun all(): List<Route> =
            Route::class.sealedSubclasses
                .map { it.objectInstance as Route }
    }

}

data class FloatingActionButtonInfo(
    val defaultIconVector: ImageVector,
)
