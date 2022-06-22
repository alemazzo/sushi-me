package io.github.alemazzo.sushime.model.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(private val context: Context) {

    object Keys {
        val USER_ALREADY_REGISTERED = booleanPreferencesKey("user-registered")
        val USER_EMAIL = stringPreferencesKey("user-email")
        val USER_NAME = stringPreferencesKey("user-name")
        val USER_SURNAME = stringPreferencesKey("user-surname")
    }

    // to make sure there's only one instance
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }

    private fun <T : Any> getProperty(key: Preferences.Key<T>): Flow<T?> = context.dataStore.data
        .map { preferences ->
            preferences[key]
        }

    private suspend fun <T : Any> updateProperty(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private suspend fun updateUserRegistered(value: Boolean) =
        updateProperty(Keys.USER_ALREADY_REGISTERED, value)

    fun isUserRegistered() = getProperty(Keys.USER_ALREADY_REGISTERED)
    suspend fun registerUser(email: String, name: String, surname: String) {
        updateEmail(email)
        updateName(name)
        updateSurname(surname)
        updateUserRegistered(true)
    }

    fun getEmail() = getProperty(Keys.USER_EMAIL)
    suspend fun updateEmail(email: String) = updateProperty(Keys.USER_EMAIL, email)

    fun getName() = getProperty(Keys.USER_NAME)
    suspend fun updateName(name: String) = updateProperty(Keys.USER_NAME, name)

    fun getSurname() = getProperty(Keys.USER_SURNAME)
    suspend fun updateSurname(surname: String) = updateProperty(Keys.USER_SURNAME, surname)


}
