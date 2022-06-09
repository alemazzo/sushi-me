package io.github.alemazzo.sushime.ui.screens.infoget.viewmodel

import android.app.Application
import android.widget.Toast
import io.github.alemazzo.sushime.ui.screens.infoget.state.InfoGetScreenState
import io.github.alemazzo.sushime.utils.ViewModelWithUIState
import io.github.alemazzo.sushime.utils.launchWithMainContext
import kotlinx.coroutines.delay

class InfoGetViewModel(application: Application) :
    ViewModelWithUIState<InfoGetScreenState>(application) {
    override val uiState: InfoGetScreenState = InfoGetScreenState()

    suspend fun registerInfo() {
        val email = uiState.email.value
        val name = uiState.name.value
        val surname = uiState.surname.value
        launchWithMainContext {
            Toast.makeText(
                getApplication(), "Register $name $surname : $email",
                Toast.LENGTH_LONG
            ).show()
        }
        delay(1000)
    }
}
