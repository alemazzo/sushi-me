package io.github.alemazzo.sushime.ui.screens.infoget.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import io.github.alemazzo.sushime.ui.utils.UiState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class InfoGetScreenState(
    var email: @RawValue MutableState<String> = mutableStateOf(""),
    var name: @RawValue MutableState<String> = mutableStateOf(""),
    var surname: @RawValue MutableState<String> = mutableStateOf(""),
) : UiState()
