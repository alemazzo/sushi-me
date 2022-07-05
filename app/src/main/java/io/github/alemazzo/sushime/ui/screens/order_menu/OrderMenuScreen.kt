package io.github.alemazzo.sushime.ui.screens.order_menu

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.repositories.images.ImagesRepository
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel.OrderViewModel
import io.github.alemazzo.sushime.ui.screens.restaurant_info.components.ShowDishInfo
import io.github.alemazzo.sushime.ui.screens.restaurants.components.*
import io.github.alemazzo.sushime.utils.CenteredColumn
import io.github.alemazzo.sushime.utils.DefaultTopAppBar
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.qr.getQrCodeBitmap
import io.github.alemazzo.sushime.utils.qr.getQrContentFromTableId


@ExperimentalMaterial3Api
object OrderMenuScreen : Screen() {

    var showParticipants by mutableStateOf(false)
    var showQR by mutableStateOf(false)

    @Composable
    override fun TopBar() {
        val orderViewModel: OrderViewModel = getViewModel()
        DefaultTopAppBar(title = "Menu") {
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
        val tableId =
            arguments?.getString(Routes.OrderMenuRoute.orderMenuOrderIdArgName)!!
        val orderViewModel: OrderViewModel = getViewModel()

        ShowParticipants(showPopup = showParticipants) {
            showParticipants = false
        }
        ShowQRInfo(showPopup = showQR) {
            showQR = false
        }
        OrderMenuContent(
            navigator = navigator,
            paddingValues = paddingValues,
            tableId = tableId,
            orderViewModel = orderViewModel
        )

    }

}


@Composable
fun LoadingScreen(paddingValues: PaddingValues) {
    CenteredColumn(modifier = Modifier.padding(paddingValues)) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowQRInfo(showPopup: Boolean, onEnd: () -> Unit) {
    val orderViewModel: OrderViewModel = getViewModel()
    val qrImage = getQrCodeBitmap(getQrContentFromTableId(orderViewModel.tableId!!))
    if (showPopup) {
        BackPressHandler {
            onEnd()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = {
                    onEnd()
                }
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .width(350.dp)
                        .height(400.dp)
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextTitleLarge(name = "Table Information")
                    Image(
                        bitmap = qrImage.asImageBitmap(),
                        contentDescription = "QRCode",
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .size(250.dp)
                    )
                    TextTitleLarge(name = "Code: ${orderViewModel.tableId!!}")
                }
            }
        }
    }

}

@Composable
fun ShowParticipants(
    showPopup: Boolean,
    onEnd: () -> Unit,
) {
    val orderViewModel: OrderViewModel = getViewModel()
    if (showPopup) {
        BackPressHandler {
            onEnd()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = {
                    onEnd()
                }
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .width(350.dp)
                        .height(400.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Text(text = "Participants",
                        style = MaterialTheme.typography.titleLarge)
                    LazyColumn {
                        items(orderViewModel.users.toList()) { user ->
                            TextBodyLarge(description = user)
                        }
                    }
                }
            }
        }

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
    val imagesRepository = ImagesRepository()
    val categoriesWithDishes by orderViewModel.categoriesRepository.getAllCategoriesWithDishes()
        .observeAsState()

    var selectedDish: Dish? by remember {
        mutableStateOf(null)
    }

    selectedDish?.let {
        ShowDishInfo(it) {
            selectedDish = null
        }
    }

    CenteredColumn(modifier = Modifier
        .padding(paddingValues)
        .background(MaterialTheme.colorScheme.primaryContainer)) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            categoriesWithDishes?.let {
                items(it) { categoryWithDishes ->
                    Card(
                        elevation = CardDefaults.cardElevation(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            TextTitleLarge(name = categoryWithDishes.category.name)
                            LazyRow(
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                items(categoryWithDishes.dishes) { dish ->
                                    var itemCount by remember {
                                        mutableStateOf(orderViewModel.getDishAmount(dish))
                                    }
                                    Card(
                                        modifier = Modifier
                                            .width(200.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        elevation = CardDefaults.cardElevation(6.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = contentColorFor(MaterialTheme.colorScheme.secondaryContainer)
                                        ),
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                IconButton(onClick = {
                                                    itemCount--
                                                    orderViewModel.decreaseDishFromOrder(dish)
                                                },
                                                    enabled = itemCount != 0) {
                                                    Icon(imageVector = Icons.Filled.Remove,
                                                        contentDescription = "Remove")
                                                }

                                                TextBodyMedium(description = itemCount.toString())


                                                IconButton(
                                                    onClick = {
                                                        itemCount++
                                                        orderViewModel.increaseDishToOrder(dish)
                                                    }
                                                ) {
                                                    Icon(imageVector = Icons.Filled.Add,
                                                        contentDescription = "Add")
                                                }
                                            }


                                            TextTitleMedium(dish.name)

                                            CircleShapeImage(
                                                painter = rememberAsyncImagePainter(imagesRepository.getDishImageLink(
                                                    dish)),
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
}
