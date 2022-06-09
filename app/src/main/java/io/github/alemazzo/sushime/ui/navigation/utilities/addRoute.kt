package io.github.alemazzo.sushime.ui.navigation.utilities

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.alemazzo.sushime.ui.navigation.routing.Route

@ExperimentalMaterial3Api
fun NavGraphBuilder.addRoute(
    route: Route,
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    composable(
        route = route.path,
        arguments = route.arguments.keys.map { navArgument(it) { type = route.arguments[it]!! } }
    ) {
        route.screen(navController, paddingValues, it.arguments)
    }
}
