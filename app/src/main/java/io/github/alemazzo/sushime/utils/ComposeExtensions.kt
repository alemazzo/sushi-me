package io.github.alemazzo.sushime.utils

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.alemazzo.sushime.ui.navigation.NavBarItemInfo
import io.github.alemazzo.sushime.ui.navigation.Route

@ExperimentalMaterial3Api
fun NavGraphBuilder.addRoute(route: Route, navController: NavHostController, paddingValues: PaddingValues) {
    composable(
        route = route.path,
        arguments = route.arguments.map { navArgument(it.first) { type = it.second } }
    ) { route.screen(navController, paddingValues, it.arguments) }
}

@ExperimentalMaterial3Api
fun NavHostController.navigate(
    route: Route,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = { }
) {
    navigate(route.path, builder = {
        launchSingleTop = true
        navOptionsBuilder()
    })
}


@ExperimentalMaterial3Api
fun NavOptionsBuilder.popUpTo(route: Route, function: PopUpToBuilder.() -> Unit = {}) {
    popUpTo(route.path, function)
}

@Composable
fun RowScope.NavBarItemFromRoute(
    element: NavBarItemInfo,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        label = { Text(element.title) },
        icon = {
            Icon(
                painter = painterResource(id = element.iconResourceId),
                contentDescription = element.title
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun rememberCurrentRoute(navController: NavHostController): Route? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = Route.all().firstOrNull {
        it.path == navBackStackEntry?.destination?.route
    }
    return currentRoute
}
