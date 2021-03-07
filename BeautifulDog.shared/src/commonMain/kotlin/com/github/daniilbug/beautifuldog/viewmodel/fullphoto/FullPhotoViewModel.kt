package com.github.daniilbug.beautifuldog.viewmodel.fullphoto

import com.github.daniilbug.beautifuldog.interactors.FullPhotoInteractor
import com.github.daniilbug.beautifuldog.viewmodel.KmpViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FullPhotoViewModel(private val fullPhotoInteractor: FullPhotoInteractor) : KmpViewModel() {
    private val mutableState = MutableStateFlow<FullPhotoState>(FullPhotoState.Default)
    val state: StateFlow<FullPhotoState> get() = mutableState

    fun sendEvent(event: FullPhotoEvent) {
        when (event) {
            is FullPhotoEvent.Unlike -> unlikePhoto(event.photoUrl)
        }
    }

    private fun unlikePhoto(photoUrl: String) = scope.launch {
        fullPhotoInteractor.unlikePhoto(photoUrl)
        mutableState.value = FullPhotoState.Unliked
    }
}