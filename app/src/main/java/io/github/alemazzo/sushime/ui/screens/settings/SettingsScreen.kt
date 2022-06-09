package io.github.alemazzo.sushime.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.ui.screens.restaurants.components.*
import io.github.alemazzo.sushime.utils.WeightedColumnCentered
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredVertically

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 16.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        UserAccountCard()
        SettingsSection(paddingValues)
    }
}

@ExperimentalMaterial3Api
@Composable
fun SettingsSection(paddingValues: PaddingValues) {
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

@ExperimentalMaterial3Api
@Composable
fun SwitchSettingCard(setting: String) {
    var state by remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center
        ) {
            WeightedColumnCenteredVertically(4f) {
                TextTitleMedium(setting)
            }
            WeightedColumnCenteredHorizontally(1f) {
                Switch(checked = state, onCheckedChange = { state = it })
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun UserAccountCard() {
    var name by remember {
        mutableStateOf("Alessandro Mazzoli")
    }
    var email by remember {
        mutableStateOf("alemazzoli97@gmail.com")
    }
    var isEditing by remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .clickable {  },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp)
                .height(IntrinsicSize.Min)
            ,
            horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally)
        ) {
            WeightedColumnCentered(13f) {
                Text(
                    "Account",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            WeightedColumnCenteredHorizontally(1f){
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
            WeightedColumnCenteredHorizontally(2f){
                UserProfileImage()
            }
            WeightedColumnCentered(4f){
                UserAccountNameAndEmailSection(
                    isEditing = isEditing,
                    name = name,
                    onNameEdit = { name = it },
                    email = email,
                    onEmailEdit = { email = it }
                )
            }
        }
    }
}

@Composable
fun UserProfileImage() {
    CircleShapeImage(
        painter = painterResource(id = R.drawable.example_restaurant_image),
        size = 112.dp
    )
}
@Composable
fun UserAccountNameAndEmailSection(
    isEditing: Boolean,
    name: String,
    onNameEdit: (String) -> Unit,
    email: String,
    onEmailEdit: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(4.dp)
    ) {
        if (isEditing) {
            OutlinedTextField(value = name, onValueChange = { onNameEdit(it) })
        } else {
            TextTitleMedium(name)
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (isEditing) {
            OutlinedTextField(value = email, onValueChange = { onEmailEdit(it) })
        } else {
            TextBodySmall(email)
        }
    }
}

@Composable
fun EditIconSection(isEditing: Boolean, onChange: () -> Unit) {
    IconButton(onClick = { onChange() }) {
        Icon(
            imageVector = if (isEditing) Icons.Filled.EditOff else Icons.Filled.Edit,
            contentDescription = "Edit"
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(rememberNavController(), PaddingValues(16.dp))
}
