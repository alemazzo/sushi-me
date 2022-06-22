package io.github.alemazzo.sushime.ui.screens.join

import android.os.Bundle
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.routing.RoutePreview
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.join.components.JoinScreenContent


@ExperimentalMaterial3Api
object JoinScreen : Screen() {

    @Composable
    override fun TopBar() {
        CenterAlignedTopAppBar(
            title = { Text("Join") }
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
        JoinScreenContent(navigator, paddingValues)

    }

}

@Composable
fun SwipeHandler(
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    onSwipeUp: () -> Unit = {},
    onSwipeDown: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()

                val (x, y) = dragAmount
                when {
                    x > 0 -> { /* right */
                        onSwipeRight()
                    }
                    x < 0 -> { /* left */
                        onSwipeLeft()
                    }
                }
                when {
                    y > 0 -> { /* down */
                        onSwipeDown()
                    }
                    y < 0 -> { /* up */
                        onSwipeUp()
                    }
                }
            }
        }
    ) {
        content()
    }

}

@ExperimentalMaterial3Api
@Composable
@androidx.compose.ui.tooling.preview.Preview
fun JoinScreenPreview() {
    RoutePreview(route = Routes.JoinRoute)
}
