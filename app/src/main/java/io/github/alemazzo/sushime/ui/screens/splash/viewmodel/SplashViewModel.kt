package io.github.alemazzo.sushime.ui.screens.splash.viewmodel

import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.store.user.UserDataStore
import io.github.alemazzo.sushime.utils.getDatabase
import io.github.alemazzo.sushime.utils.withIOContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@ExperimentalMaterial3Api
class SplashViewModel(application: Application) :
    AndroidViewModel(application) {

    private val userDataStore = UserDataStore.getInstance(application)

    suspend fun load() {
        withIOContext {
            getDatabase() // load the database if not present
            delay(1000) // reasonable amount of time for splash screen
        }
    }

    suspend fun hasAlreadyBeenRegistered(): Boolean {
        return userDataStore.isUserRegistered().first() != null
    }
}
