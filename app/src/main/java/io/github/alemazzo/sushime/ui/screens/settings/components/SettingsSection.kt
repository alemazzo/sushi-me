package io.github.alemazzo.sushime.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun SettingsSection() {
    val settings = List(10) { "Setting $it" }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(settings, itemContent = {
            SwitchSettingCard(it)
        })
    }
}
