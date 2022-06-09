package io.github.alemazzo.sushime.ui.paging

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import io.github.alemazzo.sushime.ui.navigation.routing.Route

@ExperimentalMaterial3Api
@Composable
fun FloatingActionButton(currentRoute: Route?) {
    if (currentRoute?.floatingActionButtonInfo == null || currentRoute.getViewModel == null) return
    val viewModel = currentRoute.getViewModel.invoke()
    FloatingActionButton(onClick = { viewModel.onFabPress() }) {
        Icon(
            imageVector = currentRoute.floatingActionButtonInfo.defaultIconVector,
            contentDescription = "FAB",
        )
    }
}
