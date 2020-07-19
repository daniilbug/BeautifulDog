package com.github.daniilbug.beautifuldog.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

class RandomDogApi {
    private val client = HttpClient()

    @UnstableDefault
    suspend fun getRandomDogPhoto(): String {
        val json = client.get<String> {
            url("https://dog.ceo/api/breeds/image/random")
        }
        val randomPhoto = Json.parse(RandomDogResponse.serializer(), json)
        return randomPhoto.message
    }
}