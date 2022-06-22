package io.github.alemazzo.sushime.ui.screens.settings.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.store.UserDataStore
import io.github.alemazzo.sushime.ui.screens.settings.loadPhotoFromInternalStorage
import io.github.alemazzo.sushime.utils.launchWithIOContext

class SettingsViewModel(application: Application) :
    AndroidViewModel(application) {

    init {
        launchWithIOContext {
            image.value = loadPhotoFromInternalStorage(application, "profile-photo-image")
        }
    }

    val userDataStore = UserDataStore(application)
    var image: MutableState<Bitmap?> = mutableStateOf(null)


}
