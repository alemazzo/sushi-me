package io.github.alemazzo.sushime.ui.screens.settings.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.store.loadPhotoFromInternalStorage
import io.github.alemazzo.sushime.model.store.user.UserDataStore
import io.github.alemazzo.sushime.utils.launchWithIOContext
import io.github.alemazzo.sushime.utils.withMainContext

class SettingsViewModel(application: Application) :
    AndroidViewModel(application) {

    var image: MutableState<Bitmap?> = mutableStateOf(null)
    val userDataStore = UserDataStore.getInstance(application)

    init {
        launchWithIOContext {
            val bitmap = loadPhotoFromInternalStorage(application, "profile-photo-image")
            withMainContext {
                image.value = bitmap
            }
        }
    }


}
