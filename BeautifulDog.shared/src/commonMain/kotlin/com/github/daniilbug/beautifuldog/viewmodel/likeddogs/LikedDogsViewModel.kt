package com.github.daniilbug.beautifuldog.viewmodel.likeddogs

import com.github.daniilbug.beautifuldog.data.DogPhoto
import com.github.daniilbug.beautifuldog.interactors.LikedDogsInteractor
import com.github.daniilbug.beautifuldog.multiplatform.ioDispatcher
import com.github.daniilbug.beautifuldog.multiplatform.uiDispatcher
import com.github.daniilbug.beautifuldog.viewmodel.KmpViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class LikedDogsViewModel(private val likedDogsInteractor: LikedDogsInteractor) : KmpViewModel() {
    private val mutableState = MutableStateFlow<LikedDogsState>(LikedDogsState.Loading)
    val state: StateFlow<LikedDogsState> get() = mutableState

    init {
        listenDogsAndSendToState()
    }

    private fun listenDogsAndSendToState() = scope.launch {
        likedDogsInteractor.getLikedDogs()
            .map { dogs -> dogsToState(dogs) }
            .onStart { emit(LikedDogsState.Loading) }
            .catch { ex -> emit(errorToState(ex)) }
            .collect { state ->
                mutableState.value = state
            }
    }

    private fun dogsToState(dogs: List<DogPhoto>): LikedDogsState {
        return if (dogs.isNotEmpty()) {
            LikedDogsState.Loaded(dogs.map(DogPhoto::url))
        } else {
            LikedDogsState.Empty
        }
    }

    private fun errorToState(ex: Throwable): LikedDogsState {
        return LikedDogsState.Error(ex.message ?: "")
    }
}