package io.github.alemazzo.sushime.utils

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.ui.utils.UiState

open class AndroidViewModelWithFabButton(application: Application): AndroidViewModel(application) {
    var onFabPress: () -> Unit = {}
}

/**
 * A ViewModel extension that handle also an instance
 * of the UI State of the Screen that is associated with.
 */
abstract class ViewModelWithUIState<T : UiState>(application: Application) :
    AndroidViewModelWithFabButton(application) {

    /** The UI State of the screen */
    abstract val uiState: T
}
