package io.github.alemazzo.sushime.ui.screens.join

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.routing.RoutePreview
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.join.components.JoinScreenContent
import io.github.alemazzo.sushime.utils.DefaultTopAppBar


@ExperimentalMaterial3Api
object JoinScreen : Screen() {

    var useCamera by mutableStateOf(true)

    @Composable
    override fun TopBar() {
        DefaultTopAppBar(title = "Join") {
            IconButton(
                onClick = { useCamera = !useCamera },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = contentColorFor(MaterialTheme.colorScheme.primary)
                )
            ) {
                Icon(
                    imageVector = if (useCamera) Icons.Filled.TextFields else Icons.Filled.QrCodeScanner,
                    contentDescription = "Use Code"
                )
            }
        }
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
        JoinScreenContent(navigator, paddingValues, useCamera)
    }
}


@ExperimentalMaterial3Api
@Composable
@androidx.compose.ui.tooling.preview.Preview
fun JoinScreenPreview() {
    RoutePreview(route = Routes.JoinRoute)
}
