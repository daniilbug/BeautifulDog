package com.github.daniilbug.beautifuldog.data

interface DogPhotoProvider {
    suspend fun getRandomDogPhoto(): DogPhoto
}