package com.github.daniilbug.beautifuldog.interactors

import com.github.daniilbug.beautifuldog.data.DogPhoto
import com.github.daniilbug.beautifuldog.data.LikedDogPhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LikedDogsInteractor(private val likedDogPhotoRepository: LikedDogPhotoRepository) {
    fun getLikedDogs(): Flow<List<DogPhoto>> {
        return likedDogPhotoRepository.getPhotos()
    }
}