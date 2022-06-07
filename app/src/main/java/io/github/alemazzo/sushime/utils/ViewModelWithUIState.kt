package io.github.alemazzo.sushime.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.alemazzo.sushime.ui.utils.UiState

abstract class ViewModelWithUIState<T : UiState>(application: Application) :
    AndroidViewModel(application) {
    abstract val uiState: T
}
