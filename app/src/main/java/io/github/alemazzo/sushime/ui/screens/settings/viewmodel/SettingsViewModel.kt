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
import kotlinx.coroutines.flow.first

class SettingsViewModel(application: Application) :
    AndroidViewModel(application) {

    private val userDataStore = UserDataStore.getInstance(application)
    suspend fun updateEmail(email: String) = userDataStore.updateEmail(email)
    suspend fun updateName(name: String) = userDataStore.updateName(name)
    suspend fun updateSurname(surname: String) = userDataStore.updateSurname(surname)
    suspend fun getEmail(): String? = userDataStore.getEmail().first()
    suspend fun getName(): String? = userDataStore.getName().first()
    suspend fun getSurname(): String? = userDataStore.getSurname().first()

    var image: MutableState<Bitmap?> = mutableStateOf(null)

    init {
        launchWithIOContext {
            val bitmap = loadPhotoFromInternalStorage(application, "profile-photo-image")
            withMainContext {
                image.value = bitmap
            }
        }
    }


}
