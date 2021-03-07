package com.github.daniilbug.beautifuldog.viewmodel.randomdog

sealed class RandomDogState {
    class NewDog(val dogPhotoUrl: String): RandomDogState()
    object Loading: RandomDogState()
    object NetworkConnectionProblem: RandomDogState()
}