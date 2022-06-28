package io.github.alemazzo.sushime.ui.screens.waiting

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleMedium
import io.github.alemazzo.sushime.utils.CenteredColumn

@ExperimentalMaterial3Api
object WaitingScreen : Screen() {

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        ShowCircularProgressIndicatorWithText(paddingValues = paddingValues,
            "Wait for the table to be closed")
    }
}

@Composable
fun ShowCircularProgressIndicatorWithText(paddingValues: PaddingValues, text: String) {
    CenteredColumn(modifier = Modifier
        .padding(paddingValues)
        .background(MaterialTheme.colorScheme.primaryContainer)) {
        TextTitleMedium(text)
        Spacer(modifier = Modifier.height(32.dp))
        CircularProgressIndicator()
    }
}
