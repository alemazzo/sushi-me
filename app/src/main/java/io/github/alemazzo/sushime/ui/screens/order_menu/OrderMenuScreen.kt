package io.github.alemazzo.sushime.ui.screens.order_menu

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import io.github.alemazzo.sushime.model.database.piatto.Piatto
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.restaurant_info.components.ShowDishInfo
import io.github.alemazzo.sushime.ui.screens.restaurants.components.CircleShapeImage
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodyMedium
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleLarge
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleMedium
import io.github.alemazzo.sushime.utils.CenteredColumn
import io.github.alemazzo.sushime.utils.getViewModel


@ExperimentalMaterial3Api
object OrderMenuScreen : Screen() {
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
        val tableId =
            arguments?.getString(Routes.OrderMenuRoute.orderMenuOrderIdArgName)!!
        val orderViewModel: OrderViewModel = getViewModel()
        var isConnected by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = true) {
            orderViewModel.createMqttInstance { mqtt ->
                mqtt.connect {
                    mqtt.join(tableId) {
                        isConnected = true
                    }
                }
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

@ExperimentalMaterial3Api
@Composable
fun OrderMenuContent(
    navigator: NavHostController,
    paddingValues: PaddingValues,
    tableId: String,
    orderViewModel: OrderViewModel,
) {
    val categoriesWithDishes by orderViewModel.categoriesRepository.getAllCategoriesWithDishes()
        .observeAsState()

    var selectedDish: Piatto? by remember {
        mutableStateOf(null)
    }

    selectedDish?.let {
        ShowDishInfo(it) {
            selectedDish = null
        }
    }

    CenteredColumn(modifier = Modifier.padding(paddingValues)) {

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                categoriesWithDishes?.let {
                    item {
                        TextTitleLarge(name = "Menu")
                    }
                    items(it) { categoryWithDishes ->
                        TextTitleMedium(name = categoryWithDishes.categoria.nome)
                        LazyRow(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            items(categoryWithDishes.piatti) { dish ->
                                var itemCount by remember {
                                    mutableStateOf(0)
                                }
                                Card(
                                    modifier = Modifier
                                        .width(200.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = CardDefaults.cardElevation(6.dp),
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            IconButton(onClick = { itemCount-- },
                                                enabled = itemCount != 0) {
                                                Icon(imageVector = Icons.Filled.Remove,
                                                    contentDescription = "Remove")
                                            }

                                            TextBodyMedium(description = itemCount.toString())


                                            IconButton(onClick = { itemCount++ }) {
                                                Icon(imageVector = Icons.Filled.Add,
                                                    contentDescription = "Add")
                                            }
                                        }


                                        TextTitleMedium(dish.name)

                                        CircleShapeImage(
                                            painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/zucchero-sintattico/sushi-me/main/db/img/${dish.id}.jpg"),
                                            size = 125.dp,
                                            onClick = {
                                                selectedDish = dish
                                            }
                                        )
                                    }


                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
