package io.github.alemazzo.sushime.ui.screens.order_resume

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.model.orders.SingleOrder
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.order_menu.ShowParticipants
import io.github.alemazzo.sushime.ui.screens.order_menu.ShowQRInfo
import io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel.OrderViewModel
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodyLarge
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleMedium
import io.github.alemazzo.sushime.utils.CenteredColumn
import io.github.alemazzo.sushime.utils.DefaultTopAppBar
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally
import io.github.alemazzo.sushime.utils.getViewModel

@ExperimentalMaterial3Api
object OrderResumeScreen : Screen() {

    var showParticipants by mutableStateOf(false)
    var showQR by mutableStateOf(false)

    @Composable
    override fun TopBar() {
        val orderViewModel: OrderViewModel = getViewModel()
        DefaultTopAppBar(title = "Order Resume") {
            if (orderViewModel.isCreator) {
                IconButton(
                    onClick = { showParticipants = true },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = contentColorFor(MaterialTheme.colorScheme.primary)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.People,
                        contentDescription = "Users"
                    )
                }
            }
            IconButton(
                onClick = { showQR = true },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = contentColorFor(MaterialTheme.colorScheme.primary)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.QrCodeScanner,
                    contentDescription = "Show QR"
                )
            }
        }
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val orderViewModel: OrderViewModel = getViewModel()
        val orders = orderViewModel.orders
        ShowParticipants(showPopup = showParticipants) {
            showParticipants = false
        }
        ShowQRInfo(showPopup = showQR) {
            showQR = false
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 8.dp,
                    bottom = paddingValues.calculateBottomPadding() + 8.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            TextTitleMedium(name = "Wait until all users complete their order")
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Log.d("TEST", orders.toString())
                items(orders.toList()) { orderPair ->
                    UserOrderItemCard(orderPair)
                }
            }
            Button(
                onClick = {}
            ) {
                TextBodyLarge(description = "Close table and complete order")
            }
        }

    }
}

@ExperimentalMaterial3Api
@Composable
fun UserOrderItemCard(orderPair: Pair<String, SingleOrder>) {
    val orderViewModel: OrderViewModel = getViewModel()
    val user = orderPair.first
    val order = orderPair.second
    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        CenteredColumn(Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                WeightedColumnCenteredHorizontally(3f) {
                    TextBodyLarge(description = user)
                }
                WeightedColumnCenteredHorizontally(1f) {
                    TextBodyLarge(description = order.items.size.toString())
                }
            }
        }
    }
}
