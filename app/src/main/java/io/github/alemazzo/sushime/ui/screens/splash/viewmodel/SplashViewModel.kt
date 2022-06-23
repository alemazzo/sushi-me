package io.github.alemazzo.sushime.ui.screens.splash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.store.user.UserDataStore
import io.github.alemazzo.sushime.utils.withIOContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class SplashViewModel(application: Application) :
    AndroidViewModel(application) {

    private val userDataStore = UserDataStore.getInstance(application)

    suspend fun load() {
        withIOContext {
            delay(1000)
        }
    }

    suspend fun hasAlreadyBeenRegistered(): Boolean {
        return userDataStore.isUserRegistered().first() != null
    }
}
