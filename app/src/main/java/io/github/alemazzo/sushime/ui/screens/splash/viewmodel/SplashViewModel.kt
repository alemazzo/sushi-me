package io.github.alemazzo.sushime.ui.screens.splash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.AppDatabase
import io.github.alemazzo.sushime.model.store.UserDataStore
import io.github.alemazzo.sushime.utils.withIOContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class SplashViewModel(application: Application) :
    AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val userDataStore = UserDataStore(application)

    suspend fun load() {
        withIOContext {
            val categoriaDao = db.categorieDao()
        }
        delay(1000)
    }

    suspend fun hasAlreadyBeenRegistered(): Boolean {
        return userDataStore.isUserRegistered().first() != null
    }
}
