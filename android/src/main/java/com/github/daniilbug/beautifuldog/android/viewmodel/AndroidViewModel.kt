package com.github.daniilbug.beautifuldog.android.viewmodel

import androidx.lifecycle.ViewModel
import com.github.daniilbug.beautifuldog.viewmodel.KmpViewModel

abstract class AndroidViewModel<VM: KmpViewModel>(protected val kmpViewModel: VM): ViewModel() {

    override fun onCleared() {
        kmpViewModel.clear()
        super.onCleared()
    }
}