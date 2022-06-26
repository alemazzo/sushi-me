package io.github.alemazzo.sushime.utils

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
inline fun <reified T : ViewModel> getViewModel(): T {
    return viewModel(LocalContext.current as ComponentActivity)
}

@Composable
fun RowScope.WeightedColumn(weight: Float, content: @Composable () -> Unit) {
    Column(Modifier.weight(weight)) {
        content()
    }
}

@Composable
fun RowScope.WeightedColumnCenteredHorizontally(weight: Float, content: @Composable () -> Unit) {
    Column(Modifier.weight(weight), horizontalAlignment = Alignment.CenterHorizontally) {
        content()
    }
}

@Composable
fun RowScope.WeightedColumnCenteredVertically(weight: Float, content: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .weight(weight),
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

@Composable
fun RowScope.WeightedColumnCentered(weight: Float, content: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .weight(weight),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun CenteredColumn(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

@Composable
fun DefaultTopAppBar(
    title: String,
    navigationIcon: @Composable
        () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = contentColorFor(MaterialTheme.colorScheme.primary)
        ),
        title = { Text(title) },
        actions = actions,
        navigationIcon = navigationIcon
    )
}

@Composable
fun BackIconButton() {
    val context = LocalContext.current
    IconButton(
        onClick = { context.getActivity()?.onBackPressed() },
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = contentColorFor(MaterialTheme.colorScheme.primary)
        )
    ) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
    }
}
