package io.github.alemazzo.sushime.ui.screens.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alemazzo.sushime.ui.screens.settings.viewmodel.SettingsViewModel
import io.github.alemazzo.sushime.utils.WeightedColumnCentered
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally
import io.github.alemazzo.sushime.utils.launchWithIOContext

@ExperimentalMaterial3Api
@Composable
fun UserAccountCard(settingsViewModel: SettingsViewModel) {
    val name by settingsViewModel.userDataStore.getName().collectAsState(initial = "")
    val surname by settingsViewModel.userDataStore.getSurname().collectAsState(initial = "")
    val email by settingsViewModel.userDataStore.getEmail().collectAsState(initial = "")
    var isEditing by remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .clickable { },
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
                EditIconSection(isEditing) { isEditing = !isEditing }
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
                    name = name!!,
                    onNameEdit = {
                        launchWithIOContext {
                            settingsViewModel.userDataStore.updateName(it)
                        }
                    },
                    surname = surname!!,
                    onSurnameEdit = {
                        launchWithIOContext {
                            settingsViewModel.userDataStore.updateSurname(it)
                        }
                    },
                    email = email!!,
                    onEmailEdit = {
                        launchWithIOContext {
                            settingsViewModel.userDataStore.updateEmail(it)
                        }
                    }
                )
            }
        }
    }
}
