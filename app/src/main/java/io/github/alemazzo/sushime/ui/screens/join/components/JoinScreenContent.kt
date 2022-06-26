package io.github.alemazzo.sushime.ui.screens.join.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.creation.ShowCircularProgressIndicator
import io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel.OrderViewModel
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodyLarge
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleLarge
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.qr.*

@ExperimentalMaterial3Api
@Composable
fun JoinScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    useCamera: Boolean,
) {
    var loading by remember {
        mutableStateOf(false)
    }

    when {
        loading -> ShowCircularProgressIndicator(paddingValues = paddingValues)
        else -> ShowNormalContent(navController, paddingValues, useCamera) { loading = true }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ShowNormalContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    useCamera: Boolean,
    onStartLoading: () -> Unit,
) {
    val orderViewModel: OrderViewModel = getViewModel()
    var code by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (useCamera) {
            TextTitleLarge(name = "Scan your table's QR Code")
            Spacer(modifier = Modifier.height(16.dp))
            QRScanner(onTextChange = {
                Log.d("QRSCANNER", it)
                when {
                    isRestaurantQrCode(it) -> {
                        Routes.RestaurantInfoRoute.navigate(navController,
                            getRestaurantIdFromQrCode(it)!!)
                    }
                    isTableQrCode(it) -> {
                        code = getTableIdFromQrCode(it)!!
                        onStartLoading()
                        orderViewModel.joinTable(code) {
                            Routes.OrderMenuRoute.navigate(navController, code)
                        }
                    }
                }
            }, width = 300.dp, height = 300.dp)
        } else {
            TextTitleLarge(name = "Insert your table's code")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = code,
                onValueChange = { code = it },
                label = { Text(text = "Table Code") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                onStartLoading()
                orderViewModel.joinTable(code) {
                    Routes.OrderMenuRoute.navigate(navController, code)
                }
            }) {
                TextBodyLarge(description = "Join")
            }
        }
    }
}
