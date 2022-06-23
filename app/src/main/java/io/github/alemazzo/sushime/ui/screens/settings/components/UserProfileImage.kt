package io.github.alemazzo.sushime.ui.screens.settings.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.model.store.savePhotoToInternalStorage
import io.github.alemazzo.sushime.ui.screens.restaurants.components.CircleShapeImage
import io.github.alemazzo.sushime.ui.screens.settings.viewmodel.SettingsViewModel
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.launchWithIOContext
import io.github.alemazzo.sushime.utils.withMainContext

@Composable
fun UserProfileImage() {
    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = getViewModel()

    val image: Bitmap? by remember { settingsViewModel.image }

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
            ?: ImageBitmap.imageResource(id = R.drawable.avatar),
        size = 112.dp,
        onClick = {
            launcher.launch(
                "image/*"
            )
        }
    )
}
