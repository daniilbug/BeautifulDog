package com.github.daniilbug.beautifuldog.data.network

import kotlinx.serialization.Serializable

@Serializable
class RandomDogResponse(
    val message: String,
    val status: String
)