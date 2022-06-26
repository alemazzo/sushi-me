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
import io.github.alemazzo.sushime.ui.screens.order_menu.OrderViewModel
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodyLarge
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.qr.*

@ExperimentalMaterial3Api
@Composable
fun JoinScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
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
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QRScanner(onTextChange = {
            Log.d("QRSCANNER", it)
            if (isRestaurantQrCode(it)) {
                Routes.RestaurantInfoRoute.navigate(navController,
                    getRestaurantIdFromQrCode(it)!!)
            } else if (isTableQrCode(it)) {
                code = getTableIdFromQrCode(it)!!
                //Routes.OrderMenuRoute.navigate(navController, tableId = tableId)
            }
        }, width = 300.dp, height = 300.dp)
        OutlinedTextField(value = code,
            onValueChange = { code = it },
            label = { Text(text = "Table Code") })

        Button(onClick = {
            orderViewModel.joinTable(code) {
                Routes.OrderMenuRoute.navigate(navController, code)
            }
        }) {
            TextBodyLarge(description = "Join")
        }
    }
}
