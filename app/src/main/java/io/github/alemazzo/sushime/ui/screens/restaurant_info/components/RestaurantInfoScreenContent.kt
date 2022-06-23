package io.github.alemazzo.sushime.ui.screens.restaurant_info.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.model.database.ristorante.Ristorante
import io.github.alemazzo.sushime.ui.screens.restaurants.components.*
import io.github.alemazzo.sushime.utils.WeightedColumnCentered
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    ristorante: Ristorante,
) {
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
        RestaurantInfoCard(ristorante = ristorante)
        RestaurantInfoMenuRow()
        CreateTableButton()
    }
}

@ExperimentalMaterial3Api
@Composable
fun CreateTableButton() {
    Button(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        onClick = {}
    ) {
        TextBodyMedium(description = "Create Table")
    }
}

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoMenuRow() {
    val images = (1..10).map { ImageBitmap.imageResource(id = R.drawable.example_restaurant_image) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),

        ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextTitleMedium(name = "I Nostri Piatti")
            LazyRow(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                items(images, itemContent = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircleShapeImage(
                            bitmap = it,
                            size = 100.dp
                        )
                        TextBodyMedium(description = "Huramaki")
                    }
                })
            }
        }

    }
}

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoCard(ristorante: Ristorante) {
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
                    bitmap = ImageBitmap.imageResource(id = R.drawable.example_restaurant_image),
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
                    TextBodySmall(description = "Via Salvatore Quasimodo 421, Cesena, 47522")
                }
            }
        }
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min)
        ) {
            TextBodyMedium(ristorante.descrizione)
        }
    }
}
