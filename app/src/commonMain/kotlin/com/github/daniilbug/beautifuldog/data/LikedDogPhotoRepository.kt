package com.github.daniilbug.beautifuldog.data

import kotlinx.coroutines.flow.Flow

interface LikedDogPhotoRepository {
    fun getPhotos(): Flow<List<DogPhoto>>

    suspend fun addNewPhoto(photo: DogPhoto)
    suspend fun photoExists(url: String): Boolean
    suspend fun removePhoto(url: String)
}