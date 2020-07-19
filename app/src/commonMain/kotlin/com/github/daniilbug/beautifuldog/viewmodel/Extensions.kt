package com.github.daniilbug.beautifuldog.viewmodel

import com.github.daniilbug.beautifuldog.multiplatform.uiDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
suspend fun <T> MutableStateFlow<T>.setValueOnUI(value: T) = withContext(uiDispatcher) {
    this@setValueOnUI.value = value
}