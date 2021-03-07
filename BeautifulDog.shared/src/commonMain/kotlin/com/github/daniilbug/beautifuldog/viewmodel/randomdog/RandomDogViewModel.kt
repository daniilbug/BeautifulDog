package com.github.daniilbug.beautifuldog.viewmodel.randomdog

import com.github.daniilbug.beautifuldog.data.network.NetworkException
import com.github.daniilbug.beautifuldog.interactors.RandomDogInteractor
import com.github.daniilbug.beautifuldog.viewmodel.KmpViewModel
import com.github.daniilbug.beautifuldog.viewmodel.setValueOnUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class RandomDogViewModel(private val randomDogInteractor: RandomDogInteractor): KmpViewModel() {
    private val mutableState = MutableStateFlow<RandomDogState>(
        RandomDogState.Loading)
    val state: StateFlow<RandomDogState> get() = mutableState

    init {
        produceNewDog()
    }

    fun sendEvent(event: RandomDogEvent) {
        when (event) {
            is RandomDogEvent.GetNewDog -> produceNewDog()
            is RandomDogEvent.LikeDog -> likeDog(event.dogUrl)
        }
    }

    private fun produceNewDog() = scope.launch {
        mutableState.value = RandomDogState.Loading
        try {
            val nextDog = randomDogInteractor.getNewRandomDog()
            mutableState.setValueOnUI(RandomDogState.NewDog(nextDog.url))
        } catch (ex: Exception) {
            mutableState.setValueOnUI(errorToState(ex))
        }
    }

    private fun likeDog(dogUrl: String) = scope.launch {
        randomDogInteractor.likeDog(dogUrl)
    }

    private fun errorToState(ex: Exception): RandomDogState {
        when (ex) {
            is NetworkException -> return RandomDogState.NetworkConnectionProblem
            else -> throw ex
        }
    }
}