package com.github.daniilbug.beautifuldog.android.viewmodel

import com.github.daniilbug.beautifuldog.viewmodel.likeddogs.LikedDogsState
import com.github.daniilbug.beautifuldog.viewmodel.likeddogs.LikedDogsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class AndroidLikedDogsViewModel(likedDogsViewModel: LikedDogsViewModel): AndroidViewModel<LikedDogsViewModel>(likedDogsViewModel) {

    val state: StateFlow<LikedDogsState> get() = kmpViewModel.state
}