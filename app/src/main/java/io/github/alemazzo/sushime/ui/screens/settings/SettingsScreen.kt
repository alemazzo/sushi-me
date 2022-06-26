package io.github.alemazzo.sushime.ui.screens.settings

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.routing.RoutePreview
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.settings.components.SettingsScreenContent

@ExperimentalMaterial3Api
object SettingsScreen : Screen() {

    @Composable
    override fun TopBar() {
        CenterAlignedTopAppBar(
            title = { Text("Settings") }
        )
    }

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(currentRoute, navigator)
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        SettingsScreenContent(navigator, paddingValues)
    }

}


@ExperimentalMaterial3Api
@Preview
@Composable
fun SettingsScreenPreview() {
    RoutePreview(route = Routes.SettingsRoute)
}
