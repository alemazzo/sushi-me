package io.github.alemazzo.sushime.ui.screens.infoget.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.parcelize.RawValue


data class InfoGetScreenState(
    var email: MutableState<String> = mutableStateOf(""),
    var name: MutableState<String> = mutableStateOf(""),
    var surname: MutableState<String> = mutableStateOf(""),
)
