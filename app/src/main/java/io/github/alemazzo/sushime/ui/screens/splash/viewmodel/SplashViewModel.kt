package io.github.alemazzo.sushime.ui.screens.splash.viewmodel

import android.app.Application
import android.util.Log
import io.github.alemazzo.sushime.ui.utils.UiState
import io.github.alemazzo.sushime.utils.ViewModelWithUIState
import kotlinx.coroutines.delay
import kotlinx.parcelize.Parcelize

@Parcelize
class SplashScreenUIState : UiState()

class SplashViewModel(application: Application) :
    ViewModelWithUIState<SplashScreenUIState>(application) {

    override val uiState: SplashScreenUIState = SplashScreenUIState()

    suspend fun load() {
        Log.d("SplashViewModel", "start loading")
        delay(4000)
        Log.d("SplashViewModel", "load done")
    }

    fun hasAlreadyBeenRegistered(): Boolean {
        return false
    }
}
