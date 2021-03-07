package com.github.daniilbug.beautifuldog.viewmodel.likeddogs

sealed class LikedDogsEvent {
    class Remove(photoUrl: String): LikedDogsEvent()
}