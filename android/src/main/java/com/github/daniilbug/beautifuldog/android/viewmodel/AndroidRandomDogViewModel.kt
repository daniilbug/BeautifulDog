package com.github.daniilbug.beautifuldog.android.viewmodel

import com.github.daniilbug.beautifuldog.viewmodel.randomdog.RandomDogEvent
import com.github.daniilbug.beautifuldog.viewmodel.randomdog.RandomDogState
import com.github.daniilbug.beautifuldog.viewmodel.randomdog.RandomDogViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class AndroidRandomDogViewModel(randomDogViewModel: RandomDogViewModel): AndroidViewModel<RandomDogViewModel>(randomDogViewModel) {
    val state: StateFlow<RandomDogState> = kmpViewModel.state

    fun sendEvent(randomDogEvent: RandomDogEvent) {
        kmpViewModel.sendEvent(randomDogEvent)
    }
}