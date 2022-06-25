package io.github.alemazzo.sushime.ui.screens.order_cart

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.order_menu.OrderViewModel
import io.github.alemazzo.sushime.utils.getViewModel

@ExperimentalMaterial3Api
object OrderCartScreen : Screen() {

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val orderViewModel: OrderViewModel = getViewModel()
        val mqtt = orderViewModel.sushimeMqtt


    }
}
