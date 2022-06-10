package io.github.alemazzo.sushime.ui.screens.restaurants

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.BottomBars
import io.github.alemazzo.sushime.Routes
import io.github.alemazzo.sushime.ui.navigation.Route
import io.github.alemazzo.sushime.ui.navigation.RoutePreview
import io.github.alemazzo.sushime.ui.navigation.Screen
import io.github.alemazzo.sushime.ui.screens.restaurants.components.RestaurantInfoCard
import io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel.RestaurantsScreenViewModel
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.qr.QRScanner

@ExperimentalMaterial3Api
object RestaurantsScreen : Screen() {

    var isQRScannerVisible by mutableStateOf(false)

    @Composable
    override fun FloatingActionButton() {
        androidx.compose.material3.FloatingActionButton(onClick = { isQRScannerVisible = true }) {
            Icon(
                imageVector = Icons.Filled.QrCodeScanner,
                contentDescription = "Scan"
            )
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
        RestaurantsScreenContent(
            navigator,
            paddingValues,
            isQRScannerVisible
        ) { isQRScannerVisible = it }
    }

}

@ExperimentalMaterial3Api
@Composable
fun RestaurantsScreenContent(
    navController: NavHostController,
    padding: PaddingValues,
    isQRScannerVisible: Boolean,
    onQRScannerVisibilityChange: (Boolean) -> Unit,
) {
    val restaurantsScreenViewModel: RestaurantsScreenViewModel = getViewModel()
    val context = LocalContext.current

    ShowQRScannerWhenFabPressed(
        showPopup = isQRScannerVisible,
        onEnd = {
            onQRScannerVisibilityChange(false)
        },
        onResult = {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    )

    RestaurantList(navController, restaurantsScreenViewModel, padding, isQRScannerVisible.not())

}

@ExperimentalMaterial3Api
@Composable
fun RestaurantList(
    navController: NavHostController,
    restaurantsScreenViewModel: RestaurantsScreenViewModel,
    padding: PaddingValues,
    enabled: Boolean,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 16.dp,
                bottom = padding.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
        userScrollEnabled = enabled,
        state = restaurantsScreenViewModel.listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(restaurantsScreenViewModel.items, itemContent = {
            RestaurantInfoCard(restaurantInfo = it, enabled = enabled) {
                Routes.RestaurantInfoRoute.navigate(
                    navigator = navController,
                    restaurantName = it.name
                )
            }
        })
    }
}


@Composable
fun ShowQRScannerWhenFabPressed(
    showPopup: Boolean,
    onEnd: () -> Unit,
    onResult: (String) -> Unit,
) {
    if (showPopup) {
        BackPressHandler {
            onEnd()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = {
                    onEnd()
                }
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .width(350.dp)
                        .height(400.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Scan Restaurant's QR",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black)
                    QRScanner(onTextChange = {
                        if (it != "") {
                            onResult(it)
                            onEnd()
                        }
                    }, width = 300.dp, height = 300.dp)
                }
            }
        }

    }
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit,
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun RestaurantsScreenPreview() {
    RoutePreview(route = Routes.RestaurantsRoute)
}
