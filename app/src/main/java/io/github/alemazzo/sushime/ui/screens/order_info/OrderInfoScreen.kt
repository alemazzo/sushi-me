package io.github.alemazzo.sushime.ui.screens.order_info

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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.database.dishes_in_orders.DishInOrder
import io.github.alemazzo.sushime.model.database.orders.OrderWithDishInOrder
import io.github.alemazzo.sushime.model.database.restaurants.Restaurant
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.restaurant_info.components.ShowDishInfo
import io.github.alemazzo.sushime.ui.screens.restaurants.components.CircleShapeImage
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodyLarge
import io.github.alemazzo.sushime.utils.BackIconButton
import io.github.alemazzo.sushime.utils.DefaultTopAppBar
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally
import io.github.alemazzo.sushime.utils.getViewModel
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterial3Api
object OrderInfoScreen : Screen() {

    var order: OrderWithDishInOrder? by mutableStateOf(null)
    var restaurant: Restaurant? by mutableStateOf(null)


    @Composable
    override fun TopBar() {
        val date = SimpleDateFormat("dd/MM/yy",
            Locale.ITALY).format(order?.order?.timestamp ?: 0)
        DefaultTopAppBar(title = "${restaurant?.name} $date", navigationIcon = {
            BackIconButton()
        })
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val orderId =
            arguments?.getString(Routes.OrderInfoRoute.orderIdArgName)!!.toInt()
        val orderInfoViewModel: OrderInfoViewModel = getViewModel()

        val orderWithDishesInOrder by orderInfoViewModel.ordersRepository.getOrderWithDishesInOrderByOrderId(
            orderId).observeAsState()

        orderWithDishesInOrder?.let {
            order = it
            val rest by orderInfoViewModel.restaurantsRepository.getById(it.order.restaurantId)
                .observeAsState()
            rest?.let {
                restaurant = rest!!
            }
        }

        var selectedDish: Dish? by remember {
            mutableStateOf(null)
        }

        selectedDish?.let {
            ShowDishInfo(it) {
                selectedDish = null
            }
        }

        LazyColumn(
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            orderWithDishesInOrder?.let {
                items(it.dishesInOrder) { item: DishInOrder ->
                    val _dish by orderInfoViewModel.dishesRepository.getById(item.dishId)
                        .observeAsState()
                    _dish?.let { dish ->
                        OrderItemInfoCard(dish, amount = item.amount, { selectedDish = dish })
                    }
                }
            }

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun OrderItemInfoCard(
    dish: Dish,
    amount: Int,
    onClick: () -> Unit,
    orderInfoViewModel: OrderInfoViewModel = getViewModel(),
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            WeightedColumnCenteredHorizontally(2f) {
                CircleShapeImage(painter = rememberAsyncImagePainter(orderInfoViewModel.imagesRepository.getDishImageLink(
                    dish)),
                    size = 100.dp)
            }
            WeightedColumnCenteredHorizontally(4f) {
                TextBodyLarge(description = dish.name)
            }
            WeightedColumnCenteredHorizontally(1f) {
                TextBodyLarge(description = amount.toString())
            }
        }
    }

}
