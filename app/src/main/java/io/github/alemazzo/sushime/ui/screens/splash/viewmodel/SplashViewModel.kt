package io.github.alemazzo.sushime.ui.screens.splash.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.store.UserDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class SplashViewModel(application: Application) :
    AndroidViewModel(application) {

    private val userDataStore = UserDataStore(application)

    suspend fun load() {
        Log.d("SplashViewModel", "start loading")
        delay(1000)
        Log.d("SplashViewModel", "load done")
    }

    suspend fun hasAlreadyBeenRegistered(): Boolean {
        return userDataStore.isUserRegistered().first() != null
    }
}
