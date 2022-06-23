package io.github.alemazzo.sushime.ui.screens.restaurant_info.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.model.database.ristorante.Ristorante
import io.github.alemazzo.sushime.utils.CenteredColumn

@ExperimentalMaterial3Api
@Composable
fun RestaurantInfoScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    ristorante: Ristorante,
) {
    CenteredColumn(modifier = Modifier.padding(paddingValues)) {

    }
}
