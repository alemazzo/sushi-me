package io.github.alemazzo.sushime.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel


open class AndroidViewModelWithFabButton(application: Application) : AndroidViewModel(application) {
    var onFabPress: () -> Unit = {}
}

/**
 * A ViewModel extension that handle also an instance
 * of the UI State of the Screen that is associated with.
 */
abstract class AndroidViewModelWithUIState<T>(application: Application) :
    AndroidViewModel(application) {

    /** The UI State of the screen */
    abstract val uiState: T
}

abstract class AndroidViewModelWithUIStateAndFabButton<T>(application: Application) :
    AndroidViewModelWithFabButton(application) {

    /** The UI State of the screen */
    abstract val uiState: T
}