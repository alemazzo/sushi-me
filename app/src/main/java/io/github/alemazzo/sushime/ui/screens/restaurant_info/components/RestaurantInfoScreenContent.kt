package io.github.alemazzo.sushime.ui.screens.restaurant_info.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.model.database.dishes.Dish
import io.github.alemazzo.sushime.model.database.restaurants.Restaurant
import io.github.alemazzo.sushime.ui.screens.restaurant_info.viewmodel.RestaurantInfoViewModel
import io.github.alemazzo.sushime.ui.screens.restaurants.components.*
import io.github.alemazzo.sushime.utils.WeightedColumnCentered
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    restaurantInfoViewModel: RestaurantInfoViewModel,
    restaurant: Restaurant,
) {

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
        item {
            RestaurantInfoCard(ristorante = restaurant)
        }
        item {
            RestaurantInfoMenuRow(restaurantInfoViewModel) { selectedDish = it }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ShowDishInfo(
    dish: Dish,
    onEnd: () -> Unit,
) {
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
            Card(
                elevation = CardDefaults.cardElevation(16.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(400.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .width(350.dp)
                        .height(400.dp)
                        .padding(16.dp),
                ) {
                    TextTitleLarge(name = dish.name)
                    CircleShapeImage(
                        painter = rememberAsyncImagePainter(model = "https://raw.githubusercontent.com/zucchero-sintattico/sushi-me/main/db/img/${dish.id}.jpg"),
                        size = 200.dp
                    )
                    TextBodyLarge(description = dish.description)
                }
            }

        }
    }


}

@ExperimentalMaterial3Api
@Composable
fun CreateTableButton(navigator: NavHostController, restaurant: Restaurant) {
    Button(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        onClick = {
            Routes.CreationRoute.navigate(navigator, restaurant.id)
        }
    ) {
        TextBodyLarge(description = "Create Table")
    }
}

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoMenuRow(
    restaurantInfoViewModel: RestaurantInfoViewModel,
    onDishClick: (Dish) -> Unit,
) {

    val categoriesWithDishes by restaurantInfoViewModel.categoriesRepository.getAllCategoriesWithDishes()
        .observeAsState()

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTitleLarge(name = "I nostri piatti")
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                categoriesWithDishes?.let {
                    items(it) { categoryWithDishes ->
                        TextTitleMedium(name = categoryWithDishes.category.name)
                        LazyRow(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            items(categoryWithDishes.dishes) { dish ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    CircleShapeImage(
                                        painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/zucchero-sintattico/sushi-me/main/db/img/${dish.id}.jpg"),
                                        size = 100.dp,
                                        onClick = {
                                            onDishClick(dish)
                                        }
                                    )
                                    TextBodyMediumWithMaximumWidth(description = dish.name,
                                        maxWidth = 100.dp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoCard(ristorante: Restaurant) {
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
                    TextTitleLarge(ristorante.name)
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
            TextBodyMedium(ristorante.description)
        }
    }
}
