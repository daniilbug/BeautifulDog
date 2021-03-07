package com.github.daniilbug.beautifuldog.viewmodel.likeddogs

sealed class LikedDogsState {
    object Loading: LikedDogsState()
    class Error(val message: String): LikedDogsState()
    object Empty: LikedDogsState()
    class Loaded(val dogPhotos: List<String>): LikedDogsState()
}