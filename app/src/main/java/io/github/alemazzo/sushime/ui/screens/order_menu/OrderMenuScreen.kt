package io.github.alemazzo.sushime.ui.screens.order_menu

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.utils.CenteredColumn
import io.github.alemazzo.sushime.utils.getViewModel


@ExperimentalMaterial3Api
object OrderMenuScreen : Screen() {

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val tableId =
            arguments?.getString(Routes.OrderMenuRoute.orderMenuOrderIdArgName)!!
        val orderViewModel: OrderViewModel = getViewModel()
        val mqtt = orderViewModel.sushimeMqtt
        var isConnected by remember { mutableStateOf(false) }

        mqtt.connect {
            it.join(tableId) {
                isConnected = true
            }
        }

        if (!isConnected) LoadingScreen(paddingValues)
        else OrderMenuContent(
            navigator, paddingValues, tableId, orderViewModel)


    }
}


@Composable
fun LoadingScreen(paddingValues: PaddingValues) {
    CenteredColumn(modifier = Modifier.padding(paddingValues)) {
        CircularProgressIndicator()
    }
}

@Composable
fun OrderMenuContent(
    navigator: NavHostController,
    paddingValues: PaddingValues,
    tableId: String,
    orderViewModel: OrderViewModel,
) {
    val categories by orderViewModel.categoriesRepository.getAll().observeAsState()
    val dishes by orderViewModel.dishesRepository.getAll().observeAsState()

    CenteredColumn(modifier = Modifier.padding(paddingValues)) {
        Text(text = "MENU")
    }
}
