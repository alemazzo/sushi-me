package io.github.alemazzo.sushime.ui.screens.infoget.viewmodel

import android.app.Application
import android.widget.Toast
import io.github.alemazzo.sushime.model.database.store.UserDataStore
import io.github.alemazzo.sushime.ui.screens.infoget.state.InfoGetScreenState
import io.github.alemazzo.sushime.utils.AndroidViewModelWithUIState
import io.github.alemazzo.sushime.utils.withMainContext
import kotlinx.coroutines.delay

class InfoGetViewModel(application: Application) :
    AndroidViewModelWithUIState<InfoGetScreenState>(application) {
    override val uiState: InfoGetScreenState = InfoGetScreenState()

    private val userDataStore = UserDataStore(application)

    suspend fun registerInfo() {
        val email = uiState.email.value
        val name = uiState.name.value
        val surname = uiState.surname.value
        userDataStore.registerUser(email, name, surname)
        withMainContext {
            Toast.makeText(
                getApplication(), "Register $name $surname : $email",
                Toast.LENGTH_LONG
            ).show()
        }
        delay(1000)
    }
}
