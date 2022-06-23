package io.github.alemazzo.sushime.ui.screens.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun EditIconSection(isEditing: Boolean, onChange: () -> Unit) {
    IconButton(onClick = { onChange() }) {
        Icon(
            imageVector = if (isEditing) Icons.Filled.EditOff else Icons.Filled.Edit,
            contentDescription = "Edit"
        )
    }
}
