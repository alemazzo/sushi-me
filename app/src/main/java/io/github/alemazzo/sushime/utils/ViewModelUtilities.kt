package io.github.alemazzo.sushime.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.model.database.AppDatabase


/**
 * A ViewModel extension that handle also an instance
 * of the UI State of the Screen that is associated with.
 */
abstract class AndroidViewModelWithUIState<T>(application: Application) :
    AndroidViewModel(application) {

    /** The UI State of the screen */
    abstract val uiState: T
}

fun AndroidViewModel.getDatabase(): AppDatabase {
    return AppDatabase.getInstance(getApplication())
}
