package com.github.daniilbug.beautifuldog.viewmodel.fullphoto

sealed class FullPhotoState {
    object Default: FullPhotoState()
    object Unliked: FullPhotoState()
}