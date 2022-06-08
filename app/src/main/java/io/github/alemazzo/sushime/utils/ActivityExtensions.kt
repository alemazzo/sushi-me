package io.github.alemazzo.sushime.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

inline fun <reified T : ViewModel> ComponentActivity.getViewModel(): T {
    return ViewModelProvider(this).get(T::class.java)
}

@Composable
inline fun <reified T : ViewModel> getViewModel(): T {
    return viewModel(LocalContext.current as ComponentActivity)
}
