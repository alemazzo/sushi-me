package io.github.alemazzo.sushime.ui.screens.login.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.alemazzo.sushime.R

@Composable
fun LoginTitle() {
    Text(
        text = stringResource(R.string.login_title),
        style = MaterialTheme.typography.headlineSmall,
        color = Color.Black,
        modifier = Modifier.padding(16.dp)
    )
}
