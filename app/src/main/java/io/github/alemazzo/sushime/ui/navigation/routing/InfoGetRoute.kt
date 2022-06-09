package io.github.alemazzo.sushime.ui.navigation.routing

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.screens.infoget.InfoGetScreen

/**
 * The screen where we ask to the user his data.
 *
 * Only displayed at the first start-up of the App.
 */
@ExperimentalMaterial3Api
object InfoGetRoute : Route(
    path = "info-get",
    screen = { navController, padding, _ ->
        InfoGetScreen(
            navController,
            padding
        )
    }
) {
    fun navigate(navController: NavHostController) {
        navController.navigate(path)
    }
}
