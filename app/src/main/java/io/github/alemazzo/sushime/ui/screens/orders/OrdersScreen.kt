package io.github.alemazzo.sushime.ui.screens.orders

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.utils.CenteredColumn


@ExperimentalMaterial3Api
object OrdersScreen : Screen() {

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(currentRoute = currentRoute, navigator = navigator)
    }

    @Composable
    override fun TopBar() {
        CenterAlignedTopAppBar(
            title = { Text("Restaurants") }
        )
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        CenteredColumn(modifier = Modifier.padding(paddingValues)) {
            Text("Orders")
        }
    }
}
