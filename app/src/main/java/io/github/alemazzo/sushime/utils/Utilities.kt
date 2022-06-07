package io.github.alemazzo.sushime.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun withCoroutine(dispatcher: CoroutineDispatcher, function: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(dispatcher).launch {
        function()
    }
}

fun withIOContext(function: suspend CoroutineScope.() -> Unit) {
    withCoroutine(Dispatchers.IO, function)
}

fun withMainContext(function: suspend CoroutineScope.() -> Unit) {
    withCoroutine(Dispatchers.Main, function)
}

fun withDefaultContext(function: suspend CoroutineScope.() -> Unit) {
    withCoroutine(Dispatchers.Default, function)
}
