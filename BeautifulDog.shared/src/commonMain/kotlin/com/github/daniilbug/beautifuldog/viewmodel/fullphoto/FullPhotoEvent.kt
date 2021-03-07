package com.github.daniilbug.beautifuldog.viewmodel.fullphoto

sealed class FullPhotoEvent {
    class Unlike(val photoUrl: String): FullPhotoEvent()
}