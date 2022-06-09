package io.github.alemazzo.sushime.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Execute a coroutine with the specified Context.
 */
fun launchWithCoroutineContext(dispatcher: CoroutineDispatcher, function: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(dispatcher).launch {
        function()
    }
}

/**
 * Execute a coroutine with the IO Context.
 */
fun launchWithIOContext(function: suspend CoroutineScope.() -> Unit) {
    launchWithCoroutineContext(Dispatchers.IO, function)
}

/**
 * Execute a coroutine with the Main Context.
 */
fun launchWithMainContext(function: suspend CoroutineScope.() -> Unit) {
    launchWithCoroutineContext(Dispatchers.Main, function)
}

/**
 * Execute a coroutine with the Defaul Context.
 */
fun launchWithDefaultContext(function: suspend CoroutineScope.() -> Unit) {
    launchWithCoroutineContext(Dispatchers.Default, function)
}
