package io.github.alemazzo.sushime.ui.screens.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodySmall
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleMedium

@Composable
fun UserAccountNameAndEmailSection(
    isEditing: Boolean,
    name: String,
    onNameEdit: (String) -> Unit,
    surname: String,
    onSurnameEdit: (String) -> Unit,
    email: String,
    onEmailEdit: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(4.dp)
    ) {
        if (isEditing) {
            OutlinedTextField(
                value = name,
                onValueChange = { onNameEdit(it) },
                label = { Text("Name") }
            )
            OutlinedTextField(
                value = surname,
                onValueChange = { onSurnameEdit(it) },
                label = { Text("Surname") }
            )
        } else {
            TextTitleMedium("$name $surname")
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (isEditing) {
            OutlinedTextField(value = email,
                onValueChange = { onEmailEdit(it) },
                label = { Text("Email") })
        } else {
            TextBodySmall(email)
        }
    }
}
