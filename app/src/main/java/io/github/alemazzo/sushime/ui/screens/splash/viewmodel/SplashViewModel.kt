package io.github.alemazzo.sushime.ui.screens.splash.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.delay

class SplashViewModel(application: Application) :
    AndroidViewModel(application) {

    suspend fun load() {
        Log.d("SplashViewModel", "start loading")
        delay(4000)
        Log.d("SplashViewModel", "load done")
    }

    fun hasAlreadyBeenRegistered(): Boolean {
        return false
    }
}
