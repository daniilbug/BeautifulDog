package com.github.daniilbug.beautifuldog.data.network

import io.ktor.client.HttpClient
import io.ktor.client.features.json.*
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

@OptIn(UnstableDefault::class)
class RandomDogApi {
    private val client = HttpClient {
        install(JsonFeature)
    }

    suspend fun getRandomDogPhoto(): String {
        val response = client.get<RandomDogResponse> {
            url("https://dog.ceo/api/breeds/image/random")
        }
        return response.message
    }
}