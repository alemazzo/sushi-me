package io.github.alemazzo.sushime.ui.screens.restaurants

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import io.github.alemazzo.sushime.ui.navigation.routing.RestaurantInfoRoute
import io.github.alemazzo.sushime.ui.paging.App
import io.github.alemazzo.sushime.ui.screens.restaurants.components.RestaurantInfoCard
import io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel.RestaurantsScreenViewModel
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.qr.QRScanner

@ExperimentalMaterial3Api
@Composable
fun RestaurantsScreen(
    navController: NavHostController,
    padding: PaddingValues,
) {
    RestaurantsScreenContent(navController, padding)
}


@ExperimentalMaterial3Api
@Composable
fun RestaurantsScreenContent(
    navController: NavHostController,
    padding: PaddingValues,
) {
    val restaurantsScreenViewModel: RestaurantsScreenViewModel = getViewModel()
    val context = LocalContext.current

    var isQRScannerVisible by remember {
        mutableStateOf(false)
    }

    ShowQRScannerWhenFabPressed(
        viewModel = restaurantsScreenViewModel,
        onStart = {
            isQRScannerVisible = true
        },
        onEnd = {
            isQRScannerVisible = false
        }
    ) {
        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
    }

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
                RestaurantInfoRoute.navigate(
                    navController = navController,
                    restaurantName = it.name
                )
            }
        })
    }
}


@Composable
fun ShowQRScannerWhenFabPressed(
    viewModel: RestaurantsScreenViewModel,
    onStart: () -> Unit,
    onEnd: () -> Unit,
    onResult: (String) -> Unit,
) {
    var showPopup by remember {
        mutableStateOf(false)
    }
    viewModel.onFabPress = {
        showPopup = true
        onStart()
    }
    if (showPopup) {
        BackPressHandler {
            showPopup = false
            onEnd()
        }
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { showPopup = false; onEnd() }
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
                        showPopup = false
                    }
                }, width = 300.dp, height = 300.dp)
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
    App { navController, padding, _ ->
        RestaurantsScreen(navController, padding)
    }
}
