package io.github.alemazzo.sushime.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Execute a coroutine with the specified Context.
 */
fun withCoroutine(dispatcher: CoroutineDispatcher, function: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(dispatcher).launch {
        function()
    }
}

/**
 * Execute a coroutine with the IO Context.
 */
fun withIOContext(function: suspend CoroutineScope.() -> Unit) {
    withCoroutine(Dispatchers.IO, function)
}

/**
 * Execute a coroutine with the Main Context.
 */
fun withMainContext(function: suspend CoroutineScope.() -> Unit) {
    withCoroutine(Dispatchers.Main, function)
}

/**
 * Execute a coroutine with the Defaul Context.
 */
fun withDefaultContext(function: suspend CoroutineScope.() -> Unit) {
    withCoroutine(Dispatchers.Default, function)
}
