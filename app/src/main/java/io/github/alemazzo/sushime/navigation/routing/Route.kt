package io.github.alemazzo.sushime.navigation.routing

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.navigation.screen.Screen

@ExperimentalMaterial3Api
open class Route(
    val path: String,
    val screen: Screen,
    val arguments: List<String> = listOf(),
) {
    fun navigate(navigator: NavHostController, arguments: Map<String, String> = mapOf()) {
        var destination = this.path
        if (this.arguments.isNotEmpty()) {
            this.arguments.forEach {
                destination = destination.replace("{$it}", arguments[it]!!)
            }
        }
        navigator.navigate(destination) {
            launchSingleTop = true
            restoreState = true
        }
    }
}
