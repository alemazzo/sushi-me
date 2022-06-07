package io.github.alemazzo.sushime.utils

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> ComponentActivity.getViewModel(): T {
    return ViewModelProvider(this).get(T::class.java)
}
