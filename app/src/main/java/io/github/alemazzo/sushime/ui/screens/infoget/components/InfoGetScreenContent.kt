package io.github.alemazzo.sushime.ui.screens.infoget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.ui.screens.infoget.components.RoundedTextField
import io.github.alemazzo.sushime.ui.screens.infoget.viewmodel.InfoGetViewModel
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.launchWithIOContext
import io.github.alemazzo.sushime.utils.withMainContext

@ExperimentalMaterial3Api
@Composable
fun InfoGetScreenContent(
    navController: NavHostController,
    padding: PaddingValues,
    infoGetViewModel: InfoGetViewModel = getViewModel(),
) {
    val uiState = infoGetViewModel.uiState
    var email by uiState.email
    var name by uiState.name
    var surname by uiState.surname
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sushi-me", style = MaterialTheme.typography.titleLarge)
        Text("Please insert your info", style = MaterialTheme.typography.titleMedium)
        RoundedTextField(
            label = "Email",
            value = email,
            onChange = { email = it })
        RoundedTextField(
            label = "Name",
            value = name,
            onChange = { name = it })
        RoundedTextField(
            label = "Surname",
            value = surname,
            onChange = { surname = it })
        Button(onClick = {
            launchWithIOContext {
                infoGetViewModel.registerInfo()
                withMainContext {
                    navController.backQueue.clear()
                    Routes.RestaurantsRoute.navigate(navController)
                }
            }
        }, shape = RoundedCornerShape(16.dp)) {
            Text(text = "Proceed")
        }

    }
}
