package io.github.alemazzo.sushime.navigation.screen.bottombar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.alemazzo.sushime.navigation.routing.Route

@ExperimentalMaterial3Api
data class BottomBarItemInfo(
    val route: Route,
    val imageVector: ImageVector,
    val title: String,
)
