package io.github.alemazzo.sushime.ui.screens.join.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.utils.qr.QRScanner
import io.github.alemazzo.sushime.utils.qr.getRestaurantIdFromQrCode
import io.github.alemazzo.sushime.utils.qr.isRestaurantQrCode

@ExperimentalMaterial3Api
@Composable
fun JoinScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QRScanner(onTextChange = {
            if (isRestaurantQrCode(it)) {
                Routes.RestaurantInfoRoute.navigate(navController, getRestaurantIdFromQrCode(it)!!)
            }
        }, width = 300.dp, height = 300.dp)
    }
}
