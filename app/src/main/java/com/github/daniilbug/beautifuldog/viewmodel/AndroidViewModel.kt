package com.github.daniilbug.beautifuldog.viewmodel

import androidx.lifecycle.ViewModel

abstract class AndroidViewModel<VM: KmpViewModel>(protected val kmpViewModel: VM): ViewModel() {

    override fun onCleared() {
        kmpViewModel.clear()
        super.onCleared()
    }
}