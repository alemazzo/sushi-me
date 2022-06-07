package io.github.alemazzo.sushime.ui.screens.navigate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.ui.screens.navigate.components.RestaurantInfo
import io.github.alemazzo.sushime.ui.screens.navigate.components.RestaurantInfoCard
import io.github.alemazzo.sushime.ui.utils.MainScaffold

@ExperimentalMaterial3Api
@Composable
fun NavigateScreen(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavigateScreenContent(navController, padding)
}


@ExperimentalMaterial3Api
@Composable
fun NavigateScreenContent(
    navController: NavHostController,
    padding: PaddingValues
) {
    val items = (1..15).map {
        RestaurantInfo(
            name = "Restaurant $it",
            description = "Description $it",
            image = painterResource(id = R.drawable.example_restaurant_image)
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, itemContent = {
            RestaurantInfoCard(restaurantInfo = it, padding)
        })

    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun NavigateScreenPreview() {
    MainScaffold { navController, padding ->
        NavigateScreen(navController, padding)
    }
}
