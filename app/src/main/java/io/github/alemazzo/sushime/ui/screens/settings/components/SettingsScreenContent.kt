package io.github.alemazzo.sushime.ui.screens.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.ui.screens.settings.viewmodel.SettingsViewModel

@ExperimentalMaterial3Api
@Composable
fun SettingsScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    settingsViewModel: SettingsViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserAccountCard(settingsViewModel)
        SettingsSection(paddingValues)
    }
}
