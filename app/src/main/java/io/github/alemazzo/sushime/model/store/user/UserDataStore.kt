package io.github.alemazzo.sushime.model.store.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.github.alemazzo.sushime.model.store.getProperty
import io.github.alemazzo.sushime.model.store.updateProperty

class UserDataStore(context: Context) {

    object Keys {
        val USER_ALREADY_REGISTERED = booleanPreferencesKey("user-registered")
        val USER_EMAIL = stringPreferencesKey("user-email")
        val USER_NAME = stringPreferencesKey("user-name")
        val USER_SURNAME = stringPreferencesKey("user-surname")
    }

    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val userDataStore = context.userDataStore


    private suspend fun updateUserRegistered(value: Boolean) =
        userDataStore.updateProperty(Keys.USER_ALREADY_REGISTERED, value)

    fun isUserRegistered() = userDataStore.getProperty(Keys.USER_ALREADY_REGISTERED)
    suspend fun registerUser(email: String, name: String, surname: String) {
        updateEmail(email)
        updateName(name)
        updateSurname(surname)
        updateUserRegistered(true)
    }

    fun getEmail() = userDataStore.getProperty(Keys.USER_EMAIL)
    suspend fun updateEmail(email: String) = userDataStore.updateProperty(Keys.USER_EMAIL, email)

    fun getName() = userDataStore.getProperty(Keys.USER_NAME)
    suspend fun updateName(name: String) = userDataStore.updateProperty(Keys.USER_NAME, name)

    fun getSurname() = userDataStore.getProperty(Keys.USER_SURNAME)
    suspend fun updateSurname(surname: String) =
        userDataStore.updateProperty(Keys.USER_SURNAME, surname)


}
