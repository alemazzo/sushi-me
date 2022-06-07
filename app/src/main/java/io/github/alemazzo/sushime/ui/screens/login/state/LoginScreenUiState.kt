package io.github.alemazzo.sushime.ui.screens.login.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import io.github.alemazzo.sushime.ui.utils.UiState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
class LoginScreenUiState(
    var email: @RawValue MutableState<String> = mutableStateOf(""),
    var password: @RawValue MutableState<String> = mutableStateOf(""),
    var errorMessage: @RawValue MutableState<String?> = mutableStateOf(null)
) : UiState()
