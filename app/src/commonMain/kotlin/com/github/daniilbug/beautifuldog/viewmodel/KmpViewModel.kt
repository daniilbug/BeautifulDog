package com.github.daniilbug.beautifuldog.viewmodel

import com.github.daniilbug.beautifuldog.multiplatform.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

abstract class KmpViewModel {
    private val job = SupervisorJob()
    protected val scope = CoroutineScope(ioDispatcher + job)

    fun clear() {
        job.cancel()
    }
}