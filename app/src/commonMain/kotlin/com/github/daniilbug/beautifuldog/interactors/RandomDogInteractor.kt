package com.github.daniilbug.beautifuldog.interactors

import com.github.daniilbug.beautifuldog.data.DogPhoto
import com.github.daniilbug.beautifuldog.data.DogPhotoProvider
import com.github.daniilbug.beautifuldog.data.LikedDogPhotoRepository

class RandomDogInteractor(
    private val photoProvider: DogPhotoProvider,
    private val likedRepository: LikedDogPhotoRepository
) {
    suspend fun getNewRandomDog(): DogPhoto = photoProvider.getRandomDogPhoto()

    suspend fun likeDog(photoUrl: String) {
        if (!likedRepository.photoExists(photoUrl))
            likedRepository.addNewPhoto(DogPhoto(photoUrl))
    }
}