package io.github.alemazzo.sushime.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        restoreState = true
        navOptionsBuilder()
    })
}
@ExperimentalMaterial3Api
fun NavHostController.navigate(
    route: Route,
    arguments: Map<String, String>,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = { }
) {
    val args = route.arguments.map { it.first }
    var path = route.path
    args.forEach {
        if(arguments.containsKey(it)) path = path.replace("{$it}", arguments[it]!!)
    }
    navigate(path, builder = {
        launchSingleTop = true
        restoreState = true
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
                imageVector = element.imageVector,
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
@Composable
fun RowScope.WeightedColumn(weight: Float, content: @Composable () -> Unit) {
    Column(Modifier.weight(weight)){
        content()
    }
}

@Composable
fun RowScope.WeightedColumnCenteredHorizontally(weight: Float, content: @Composable () -> Unit) {
    Column(Modifier.weight(weight), horizontalAlignment = Alignment.CenterHorizontally){
        content()
    }
}

@Composable
fun RowScope.WeightedColumnCenteredVertically(weight: Float, content: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .weight(weight),
        verticalArrangement = Arrangement.Center
    ){
        content()
    }
}
@Composable
fun RowScope.WeightedColumnCentered(weight: Float, content: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .weight(weight),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        content()
    }
}
