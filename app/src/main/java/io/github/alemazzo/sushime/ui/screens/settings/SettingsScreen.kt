package io.github.alemazzo.sushime.ui.screens.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.routing.RoutePreview
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.restaurants.components.CircleShapeImage
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextBodySmall
import io.github.alemazzo.sushime.ui.screens.restaurants.components.TextTitleMedium
import io.github.alemazzo.sushime.ui.screens.settings.viewmodel.SettingsViewModel
import io.github.alemazzo.sushime.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

@ExperimentalMaterial3Api
object SettingsScreen : Screen() {

    @Composable
    override fun TopBar() {
        CenterAlignedTopAppBar(
            title = { Text("Settings") }
        )
    }

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(currentRoute, navigator)
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        val settingsViewModel: SettingsViewModel = getViewModel()
        SettingsScreenContent(navigator, paddingValues)
    }

}

@ExperimentalMaterial3Api
@Composable
fun SettingsScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val settingsViewModel: SettingsViewModel = getViewModel()
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

suspend fun loadPhotoFromInternalStorage(context: Context, filename: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        val files = context.filesDir.listFiles()
        var bitmap: Bitmap? = null
        files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") && it.name == filename + ".jpg" }
            ?.firstOrNull()
            ?.also {
                val bytes = it.readBytes()
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        bitmap
    }
}

private fun savePhotoToInternalStorage(context: Context, filename: String, bmp: Bitmap): Boolean {
    return try {
        context.openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
            if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                throw IOException("Couldn't save bitmap.")
            }
        }
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

@Composable
fun UserProfileImage() {
    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = getViewModel()

    var image: Bitmap? by remember { settingsViewModel.image }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            launchWithIOContext {
                var bitmap: Bitmap? = null
                if (uri != null) {
                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images
                            .Media.getBitmap(context.contentResolver, uri)
                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }
                }

                bitmap?.let { bitmapImage ->
                    withMainContext {
                        settingsViewModel.image.value = bitmapImage
                    }
                    savePhotoToInternalStorage(context, "profile-photo-image", bitmapImage)
                }
            }
        }

    CircleShapeImage(
        bitmap = image?.asImageBitmap()
            ?: ImageBitmap.imageResource(id = R.drawable.example_restaurant_image),
        size = 112.dp,
        onClick = {
            launcher.launch(
                "image/*"
            )
        }
    )
}

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
    RoutePreview(route = Routes.SettingsRoute)
}
