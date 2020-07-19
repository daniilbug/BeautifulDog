package com.github.daniilbug.beautifuldog.viewmodel

import com.github.daniilbug.beautifuldog.viewmodel.fullphoto.FullPhotoEvent
import com.github.daniilbug.beautifuldog.viewmodel.fullphoto.FullPhotoState
import com.github.daniilbug.beautifuldog.viewmodel.fullphoto.FullPhotoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class AndroidFullPhotoViewModel(fullPhotoViewModel: FullPhotoViewModel): AndroidViewModel<FullPhotoViewModel>(fullPhotoViewModel) {

    val state: StateFlow<FullPhotoState> get() = kmpViewModel.state

    fun sendEvent(event: FullPhotoEvent) {
        kmpViewModel.sendEvent(event)
    }
}