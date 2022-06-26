package io.github.alemazzo.sushime.ui.screens.restaurant_info.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import io.github.alemazzo.sushime.model.database.dishes.CategoryWithDishes
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
    val categoriesWithDishes by restaurantInfoViewModel.categoriesRepository.getAllCategoriesWithDishes()
        .observeAsState()

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
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(
                top = paddingValues.calculateTopPadding() + 8.dp,
                bottom = paddingValues.calculateBottomPadding() + 8.dp,
                start = 8.dp,
                end = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            RestaurantInfoCard(ristorante = restaurant)
        }

        item {
            TextTitleLarge(name = "I nostri piatti")
        }

        RestaurantInfoMenuRow(restaurantInfoViewModel, categoriesWithDishes) { selectedDish = it }
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
fun LazyListScope.RestaurantInfoMenuRow(
    restaurantInfoViewModel: RestaurantInfoViewModel,
    categoriesWithDishes: List<CategoryWithDishes>?,
    onDishClick: (Dish) -> Unit,
) {
    categoriesWithDishes?.let {
        items(categoriesWithDishes) { categoryWithDishes ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
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

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoCard(ristorante: Restaurant) {
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
