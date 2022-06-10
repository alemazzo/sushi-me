package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import io.github.alemazzo.sushime.ui.theme.SushimeTheme

@ExperimentalMaterial3Api
@Composable
fun RoutePreview(route: Route) {
    val navigator = rememberNavController()
    SushimeTheme {
        Scaffold(
            floatingActionButton = { route.screen.FloatingActionButton() },
            topBar = { route.screen.TopBar() },
            bottomBar = { route.screen.BottomBar(navigator, route) },
            content = { route.screen.Content(navigator, it, null) }
        )
    }
}
