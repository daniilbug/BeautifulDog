package com.github.daniilbug.beautifuldog.multiplatform

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val ioDispatcher: CoroutineContext = Dispatchers.IO

actual val uiDispatcher: CoroutineContext = Dispatchers.Main