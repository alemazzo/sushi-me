package io.github.alemazzo.sushime.ui.screens.order_final_resume

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.orders.SingleOrderItem
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.order_info.OrderItemInfoCard
import io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel.OrderViewModel
import io.github.alemazzo.sushime.ui.screens.restaurant_info.components.ShowDishInfo
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleLarge
import io.github.alemazzo.sushime.utils.CenteredColumn
import io.github.alemazzo.sushime.utils.DefaultTopAppBar
import io.github.alemazzo.sushime.utils.getViewModel

@ExperimentalMaterial3Api
object OrderFinalResumeScreen : Screen() {

    @Composable
    override fun TopBar() {
        DefaultTopAppBar(title = "Final Resume")
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val orderViewModel: OrderViewModel = getViewModel()
        var selectedDish: Dish? by remember {
            mutableStateOf(null)
        }

        selectedDish?.let {
            ShowDishInfo(it) {
                selectedDish = null
            }
        }

        val orders = orderViewModel.getUniqueOrdersList()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(
                    top = paddingValues.calculateTopPadding() + 8.dp,
                    bottom = paddingValues.calculateBottomPadding() + 8.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            LazyColumn(
                modifier = Modifier.weight(4f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(orders) { item: SingleOrderItem ->
                    val _dish by orderViewModel.dishesRepository.getById(item.dishId)
                        .observeAsState()
                    _dish?.let { dish ->
                        OrderItemInfoCard(dish, amount = item.quantity, { selectedDish = dish })
                    }
                }
            }

            CenteredColumn(modifier = Modifier.weight(1f)) {
                Button(onClick = {
                    orderViewModel.resetData()
                    navigator.backQueue.clear()
                    Routes.OrdersRoute.navigate(navigator)
                }) {
                    TextTitleLarge(name = "Close")
                }
            }
        }
    }
}
