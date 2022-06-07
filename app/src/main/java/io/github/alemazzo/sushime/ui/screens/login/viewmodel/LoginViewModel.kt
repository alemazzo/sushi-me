package io.github.alemazzo.sushime.ui.screens.login.viewmodel

import android.app.Application
import io.github.alemazzo.sushime.model.database.AppDatabase
import io.github.alemazzo.sushime.model.database.users.User
import io.github.alemazzo.sushime.ui.screens.login.state.InfoGetScreenState
import io.github.alemazzo.sushime.utils.ViewModelWithUIState

class LoginViewModel(application: Application) :
    ViewModelWithUIState<InfoGetScreenState>(application) {

    private val userDao = AppDatabase.getInstance(application).userDao()
    private var user: User? = null

    override val uiState = InfoGetScreenState()

    fun login(email: String, password: String): Boolean {
        userDao.getByEmailAndPassword(email, password)?.let {
            user = it
            return true
        }
        return false
    }

    fun register(email: String, password: String, name: String, surname: String): Boolean {
        userDao.insert(User(0, email, password, name, surname))
        userDao.getByEmailAndPassword(email, password)?.let {
            return true
        }
        return true
    }

}
