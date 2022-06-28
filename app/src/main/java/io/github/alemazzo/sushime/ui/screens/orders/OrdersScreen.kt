package io.github.alemazzo.sushime.ui.screens.orders

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.model.database.orders.Order
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.restaurants.components.CircleShapeImage
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodyLarge
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodyMedium
import io.github.alemazzo.sushime.utils.*
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalMaterial3Api
object OrdersScreen : Screen() {

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(currentRoute = currentRoute, navigator = navigator)
    }

    @Composable
    override fun TopBar() {
        DefaultTopAppBar(title = "Orders")
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val ordersViewModel: OrdersViewModel = getViewModel()
        val orders by ordersViewModel.ordersRepository.getAll().observeAsState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(
                    top = paddingValues.calculateTopPadding() + 8.dp,
                    bottom = paddingValues.calculateBottomPadding() + 8.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            orders?.let {
                items(it) { order ->
                    OrderItemCard(order, navigator)
                }
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun OrderItemCard(
    order: Order,
    navigator: NavHostController,
    ordersViewModel: OrdersViewModel = getViewModel(),
) {
    val restaurant by ordersViewModel.restaurantsRepository.getById(order.restaurantId)
        .observeAsState()

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        onClick = {
            Routes.OrderInfoRoute.navigate(navigator, order.id)
        }
    ) {
        CenteredColumn(Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                WeightedColumnCenteredHorizontally(2f) {
                    restaurant?.let {
                        CircleShapeImage(painter = rememberAsyncImagePainter(ordersViewModel.imagesRepository.getRestaurantImageLink(
                            it)),
                            size = 100.dp)
                    }
                }
                WeightedColumn(3f) {
                    restaurant?.let {
                        TextBodyLarge(description = it.name)
                        val date = SimpleDateFormat("dd MMMM yyyy, HH:mm",
                            Locale.ITALY).format(order.timestamp)
                        TextBodyMedium(date)
                    }
                }
            }
        }
    }
}
