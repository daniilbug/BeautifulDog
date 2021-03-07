package com.github.daniilbug.beautifuldog.data.persistent

import com.github.daniilbug.beautifuldog.data.DogPhoto
import com.github.daniilbug.beautifuldog.data.LikedDogPhotoRepository
import com.github.daniilbug.beautifuldog.multiplatform.DriverFactory
import com.github.daniilbug.beautifuldog.multiplatform.createDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PersistentLikedDogPhotoRepository(driverFactory: DriverFactory): LikedDogPhotoRepository {
    private val db = createDatabase(driverFactory).dogphotoQueries

    override fun getPhotos(): Flow<List<DogPhoto>> {
        return db
            .selectAll()
            .asFlow()
            .mapToList()
            .map { photos -> photos.map { photoUrl -> DogPhoto(photoUrl) } }
    }

    override suspend fun addNewPhoto(photo: DogPhoto) {
        db.insert(photo.url)
    }

    override suspend fun removePhoto(url: String) {
        db.delete(url)
    }

    override suspend fun photoExists(url: String): Boolean {
        return db.photoExists(url).executeAsOne()
    }
}