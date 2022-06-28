package io.github.alemazzo.sushime.ui.screens.creation

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.model.database.restaurants.Restaurant
import io.github.alemazzo.sushime.model.repositories.images.ImagesRepository
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.creation.viewmodel.CreationViewModel
import io.github.alemazzo.sushime.ui.screens.order_menu.viewmodel.OrderViewModel
import io.github.alemazzo.sushime.ui.screens.restaurants.components.CircleShapeImage
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleLarge
import io.github.alemazzo.sushime.utils.*
import io.github.alemazzo.sushime.utils.qr.getQrCodeBitmap

@ExperimentalMaterial3Api
object CreationScreen : Screen() {

    @Composable
    override fun TopBar() {
        DefaultTopAppBar(title = "Create Table", navigationIcon = { BackIconButton() })
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val creationViewModel: CreationViewModel = getViewModel()
        val restaurantId =
            arguments?.getString(Routes.CreationRoute.createRouteRestaurantIdArgName)!!.toInt()
        val restaurant by creationViewModel.getRestaurant(restaurantId).observeAsState()

        val ready = rememberCreateTable(restaurantId)
        when {
            !ready || restaurant == null -> ShowCircularProgressIndicator(paddingValues)
            else -> CreationScreenContent(
                navigator = navigator,
                paddingValues = paddingValues,
                restaurant = restaurant!!
            )
        }
    }
}

@Composable
fun rememberCreateTable(
    restaurantId: Int,
    orderViewModel: OrderViewModel = getViewModel(),
): Boolean {
    var created by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        getRandomString(5).let { tableId ->
            orderViewModel.createTable(restaurantId, tableId) {
                created = true
            }
        }
    }
    return created
}

@Composable
fun ShowCircularProgressIndicator(paddingValues: PaddingValues) {
    CenteredColumn(modifier = Modifier
        .padding(paddingValues)
        .background(MaterialTheme.colorScheme.primaryContainer)) {
        CircularProgressIndicator()
    }
}

@ExperimentalMaterial3Api
@Composable
fun CreationScreenContent(
    navigator: NavHostController,
    paddingValues: PaddingValues,
    restaurant: Restaurant,
    creationViewModel: CreationViewModel = getViewModel(),
    orderViewModel: OrderViewModel = getViewModel(),
) {
    val tableId = orderViewModel.tableId!!
    val qrImage = getQrCodeBitmap(tableId)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RestaurantInfoCardInCreation(ristorante = restaurant)
        Image(
            bitmap = qrImage.asImageBitmap(),
            contentDescription = "QRCode",
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(250.dp)
        )
        TextTitleLarge(name = "Code: $tableId")
        Button(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            onClick = {

            }
        ) {
            Button(onClick = {
                Routes.OrderMenuRoute.navigate(navigator, tableId)
            }) {
                TextTitleLarge(name = "Start Order")
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoCardInCreation(ristorante: Restaurant) {
    val imagesRepository = ImagesRepository()
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min)

        ) {
            WeightedColumnCenteredHorizontally(2f) {
                CircleShapeImage(
                    painter = rememberAsyncImagePainter(imagesRepository.getRestaurantImageLink(
                        ristorante)),
                    size = 120.dp
                )
            }
            WeightedColumnCentered(3f) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(4.dp)
                ) {
                    TextTitleLarge(ristorante.name)
                }
            }
        }
    }
}
