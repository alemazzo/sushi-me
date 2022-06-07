package io.github.alemazzo.sushime.ui.screens.login.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun RoundedTextField(label: String, value: String, onChange: (String) -> Unit) {
    TextField(
        value = value,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp)),
        onValueChange = { onChange(it) },
        label = { Text(text = label) },
    )
}
