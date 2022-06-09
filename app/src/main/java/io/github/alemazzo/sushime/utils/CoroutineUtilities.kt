package io.github.alemazzo.sushime.utils

import kotlinx.coroutines.*

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


suspend fun <T> withIOContext(block: suspend CoroutineScope.() -> T){
    withContext(Dispatchers.IO) {
        block()
    }
}


suspend fun <T> withMainContext(block: suspend CoroutineScope.() -> T){
    withContext(Dispatchers.Main) {
        block()
    }
}

suspend fun <T> withDefaultContext(block: suspend CoroutineScope.() -> T){
    withContext(Dispatchers.Default) {
        block()
    }
}
