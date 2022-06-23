package io.github.alemazzo.sushime.ui.screens.restaurants.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.restaurants.viewmodel.RestaurantsScreenViewModel

@ExperimentalMaterial3Api
@Composable
fun RestaurantsList(
    navController: NavHostController,
    restaurantsScreenViewModel: RestaurantsScreenViewModel,
    padding: PaddingValues,
    enabled: Boolean,
) {
    val ristoranti by restaurantsScreenViewModel.restaurantsRepository.getAll().observeAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
        userScrollEnabled = enabled,
        state = restaurantsScreenViewModel.listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(ristoranti ?: listOf(), itemContent = {
            RestaurantInfoCard(ristorante = it, enabled = enabled) {
                Routes.RestaurantInfoRoute.navigate(
                    navigator = navController,
                    restaurantId = it.id
                )
            }
        })
    }
}
