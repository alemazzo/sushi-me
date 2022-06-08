package io.github.alemazzo.sushime.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.ui.utils.UiState

/**
 * A ViewModel extension that handle also an instance
 * of the UI State of the Screen that is associated with.
 */
abstract class ViewModelWithUIState<T : UiState>(application: Application) :
    AndroidViewModel(application) {

    /** The UI State of the screen */
    abstract val uiState: T
}
