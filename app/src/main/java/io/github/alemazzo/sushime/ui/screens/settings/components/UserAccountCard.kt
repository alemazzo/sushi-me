package io.github.alemazzo.sushime.ui.screens.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alemazzo.sushime.ui.screens.settings.viewmodel.SettingsViewModel
import io.github.alemazzo.sushime.utils.*

@ExperimentalMaterial3Api
@Composable
fun UserAccountCard(settingsViewModel: SettingsViewModel = getViewModel()) {
    var isEditing by remember {
        mutableStateOf(false)
    }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Run {
        name = settingsViewModel.getName()!!
        surname = settingsViewModel.getSurname()!!
        email = settingsViewModel.getEmail()!!
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally)
        ) {
            WeightedColumnCentered(13f) {
                Text(
                    "Account",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            WeightedColumnCenteredHorizontally(1f) {
                EditIconSection(isEditing) {
                    if (isEditing) {
                        launchWithIOContext {
                            settingsViewModel.updateName(name)
                            settingsViewModel.updateSurname(surname)
                            settingsViewModel.updateEmail(email)
                        }
                    }
                    isEditing = !isEditing
                }
            }
        }
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min)

        ) {
            WeightedColumnCenteredHorizontally(2f) {
                UserProfileImage()
            }
            WeightedColumnCentered(4f) {
                UserAccountNameAndEmailSection(
                    isEditing = isEditing,
                    name = name,
                    onNameEdit = {
                        name = it
                    },
                    surname = surname,
                    onSurnameEdit = {
                        surname = it
                    },
                    email = email,
                    onEmailEdit = {
                        email = it
                    }
                )
            }
        }
    }
}
