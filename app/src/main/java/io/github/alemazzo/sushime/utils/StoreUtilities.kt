package io.github.alemazzo.sushime.model.store

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

fun <T : Any> DataStore<Preferences>.getProperty(key: Preferences.Key<T>): Flow<T?> = this.data
    .map { preferences ->
        preferences[key]
    }

suspend fun <T : Any> DataStore<Preferences>.updateProperty(key: Preferences.Key<T>, value: T) =
    this.edit { preferences ->
        preferences[key] = value
    }


suspend fun loadPhotoFromInternalStorage(context: Context, filename: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        val files = context.filesDir.listFiles()
        var bitmap: Bitmap? = null
        files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") && it.name == filename + ".jpg" }
            ?.firstOrNull()
            ?.also {
                val bytes = it.readBytes()
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        bitmap
    }
}

fun savePhotoToInternalStorage(context: Context, filename: String, bmp: Bitmap): Boolean {
    return try {
        context.openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
            if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                throw IOException("Couldn't save bitmap.")
            }
        }
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}
