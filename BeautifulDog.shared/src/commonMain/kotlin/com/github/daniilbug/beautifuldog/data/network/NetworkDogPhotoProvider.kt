package com.github.daniilbug.beautifuldog.data.network

import com.github.daniilbug.beautifuldog.data.DogPhoto
import com.github.daniilbug.beautifuldog.data.DogPhotoProvider
import kotlinx.serialization.UnstableDefault

class NetworkDogPhotoProvider: DogPhotoProvider {
    private val randomDogApi = RandomDogApi()

    @UnstableDefault
    override suspend fun getRandomDogPhoto(): DogPhoto {
        try {
            val randomPhotoUrl = randomDogApi.getRandomDogPhoto()
            return DogPhoto(randomPhotoUrl)
        } catch (e: Exception) {
            throw NetworkException()
        }
    }
}