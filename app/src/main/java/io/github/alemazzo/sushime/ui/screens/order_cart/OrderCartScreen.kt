package io.github.alemazzo.sushime.ui.screens.order_cart

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.model.orders.SingleOrderItem
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.order_menu.ShowParticipants
import io.github.alemazzo.sushime.ui.screens.order_menu.ShowQRInfo
import io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel.OrderViewModel
import io.github.alemazzo.sushime.ui.screens.restaurants.components.CircleShapeImage
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodyLarge
import io.github.alemazzo.sushime.utils.DefaultTopAppBar
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.launchWithMainContext

@ExperimentalMaterial3Api
object OrderCartScreen : Screen() {


    var showParticipants by mutableStateOf(false)
    var showQR by mutableStateOf(false)

    @Composable
    override fun TopBar() {
        val orderViewModel: OrderViewModel = getViewModel()
        DefaultTopAppBar(title = "Cart") {
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
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        val orderViewModel: OrderViewModel = getViewModel()
        BottomBars.OrderBottomBar.Get(currentRoute,
            navigator,
            mapOf(Routes.OrderCartRoute.orderMenuOrderIdArgName to orderViewModel.tableId!!))
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val orderViewModel: OrderViewModel = getViewModel()
        val order = orderViewModel.order

        ShowParticipants(showPopup = showParticipants) {
            showParticipants = false
        }
        ShowQRInfo(showPopup = showQR) {
            showQR = false
        }
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .weight(5f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(order.values.toList()) { item: SingleOrderItem ->
                    OrderItemCard(item)
                }
            }

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        orderViewModel.makeOrder {
                            launchWithMainContext {
                                if (orderViewModel.isCreator) {
                                    Routes.OrderResumeRoute.navigate(navigator,
                                        orderViewModel.tableId!!)
                                } else {
                                    Routes.OrdersRoute.navigate(navigator)
                                }
                            }
                        }
                    }) {
                    TextBodyLarge(description = "Confirm Order")
                }
            }

        }


    }
}

@ExperimentalMaterial3Api
@Composable
fun OrderItemCard(item: SingleOrderItem) {
    val orderViewModel: OrderViewModel = getViewModel()
    val dish by orderViewModel.dishesRepository.getById(item.dishId).observeAsState()

    dish?.let {
        var itemCount by remember {
            mutableStateOf(orderViewModel.getDishAmount(dish!!))
        }
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = contentColorFor(MaterialTheme.colorScheme.onBackground)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                WeightedColumnCenteredHorizontally(2f) {
                    CircleShapeImage(painter = rememberAsyncImagePainter(model = "https://raw.githubusercontent.com/zucchero-sintattico/sushi-me/main/db/img/${dish!!.id}.jpg"),
                        size = 100.dp)
                }
                WeightedColumnCenteredHorizontally(4f) {
                    TextBodyLarge(description = dish!!.name)
                }
                WeightedColumnCenteredHorizontally(1f) {
                    IconButton(
                        onClick = {
                            itemCount++
                            orderViewModel.increaseDishToOrder(dish!!)
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Add,
                            contentDescription = "Add")
                    }
                    TextBodyLarge(description = itemCount.toString())
                    IconButton(
                        enabled = itemCount > 0,
                        onClick = {
                            itemCount--
                            orderViewModel.decreaseDishFromOrder(dish!!)
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove")
                    }

                }
            }
        }
    }

}
