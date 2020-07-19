package com.github.daniilbug.beautifuldog.interactors

import com.github.daniilbug.beautifuldog.data.LikedDogPhotoRepository

class FullPhotoInteractor(private val likedDogPhotoRepository: LikedDogPhotoRepository) {
    suspend fun unlikePhoto(url: String) {
        likedDogPhotoRepository.removePhoto(url)
    }
}