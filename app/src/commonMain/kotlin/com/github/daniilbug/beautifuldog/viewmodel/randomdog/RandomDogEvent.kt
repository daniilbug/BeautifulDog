package com.github.daniilbug.beautifuldog.viewmodel.randomdog

sealed class RandomDogEvent {
    object GetNewDog: RandomDogEvent()
    class LikeDog(val dogUrl: String): RandomDogEvent()
}