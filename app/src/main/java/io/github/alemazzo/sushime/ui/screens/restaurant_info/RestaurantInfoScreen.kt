package io.github.alemazzo.sushime.ui.screens.restaurant_info

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.utils.CenteredColumn

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    restaurantName: String,
) {
    CenteredColumn(modifier = Modifier.padding(paddingValues)) {
        Text(restaurantName)
    }
}
