package io.github.alemazzo.sushime.ui.screens.restaurants

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.ui.screens.restaurants.components.RestaurantInfo
import io.github.alemazzo.sushime.ui.screens.restaurants.components.RestaurantInfoCard
import io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel.RestaurantsScreenViewModel
import io.github.alemazzo.sushime.ui.utils.MainScaffold
import io.github.alemazzo.sushime.utils.getViewModel

@ExperimentalMaterial3Api
@Composable
fun RestaurantsScreen(
    navController: NavHostController,
    padding: PaddingValues
) {
    RestaurantsScreenContent(navController, padding)
}


@ExperimentalMaterial3Api
@Composable
fun RestaurantsScreenContent(
    navController: NavHostController,
    padding: PaddingValues
) {
    val restaurantsScreenViewModel: RestaurantsScreenViewModel = getViewModel()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding(),
                start = 8.dp,
                end = 8.dp
            ),
        state = restaurantsScreenViewModel.listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(restaurantsScreenViewModel.items, itemContent = {
            RestaurantInfoCard(restaurantInfo = it)
        })

    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun RestaurantsScreenPreview() {
    MainScaffold { navController, padding, _ ->
        RestaurantsScreen(navController, padding)
    }
}
