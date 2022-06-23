package io.github.alemazzo.sushime.ui.screens.creation

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.model.database.ristorante.Ristorante
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.creation.viewmodel.CreationViewModel
import io.github.alemazzo.sushime.ui.screens.restaurants.components.CircleShapeImage
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleLarge
import io.github.alemazzo.sushime.utils.WeightedColumnCentered
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally
import io.github.alemazzo.sushime.utils.getRandomString
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.qr.getQrCodeBitmap

@ExperimentalMaterial3Api
object CreationScreen : Screen() {

    @Composable
    override fun TopBar() {
        CenterAlignedTopAppBar(
            title = { Text("Create Table") }
        )
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val restaurantId =
            arguments?.getString(Routes.CreationRoute.createRouteRestaurantIdArgName)!!.toInt()
        val creationViewModel: CreationViewModel = getViewModel()
        val ristorante by creationViewModel.restaurantsRepository.getById(restaurantId)
            .observeAsState()
        

        ristorante?.let {
            CreationScreenContent(navigator, paddingValues, creationViewModel, it)
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun CreationScreenContent(
    navigator: NavHostController,
    paddingValues: PaddingValues,
    creationViewModel: CreationViewModel,
    restaurant: Ristorante,
) {
    val code = getRandomString(5)
    val qrCodeContent = "${restaurant.id}-$code"
    val qrImage = getQrCodeBitmap(qrCodeContent)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
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
        TextTitleLarge(name = "Code: $qrCodeContent")
        Button(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            onClick = {

            }
        ) {
            TextTitleLarge(name = "Start Order")
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoCardInCreation(ristorante: Ristorante) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
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
                    painter = rememberAsyncImagePainter(model = "https://raw.githubusercontent.com/zucchero-sintattico/sushi-me/main/db/restaurant-img/${ristorante.id}.jpg"),
                    size = 120.dp
                )
            }
            WeightedColumnCentered(3f) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(4.dp)
                ) {
                    TextTitleLarge(ristorante.nome)
                }
            }
        }
    }
}
