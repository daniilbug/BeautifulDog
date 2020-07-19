package com.github.daniilbug.beautifuldog.ui

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.coroutineScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.daniilbug.beautifuldog.R
import com.github.daniilbug.beautifuldog.viewmodel.AndroidRandomDogViewModel
import com.github.daniilbug.beautifuldog.viewmodel.randomdog.RandomDogEvent
import com.github.daniilbug.beautifuldog.viewmodel.randomdog.RandomDogState
import kotlinx.android.synthetic.main.fragment_random_dog.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class RandomDogFragment : BaseFragment(R.layout.fragment_random_dog, needBottomNavigation = true) {
    private val viewModel: AndroidRandomDogViewModel by viewModel()

    private var currentPhotoUrl: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            randomDogMotionLayout.addTransitionListener(object : TransitionAdapter() {
                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    super.onTransitionCompleted(motionLayout, currentId)
                    processMotionState(currentId)
                }
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycle.coroutineScope.launch {
            viewModel.state.collectLatest { state -> setState(state) }
        }
    }

    private fun processMotionState(stateId: Int) {
        when (stateId) {
            R.id.nextDogState -> loadNextDog()
            R.id.likeDogState -> likeDogAndLoadNext()
            R.id.shareDogState -> shareDog()
            else -> return
        }
    }

    private fun likeDogAndLoadNext() {
        currentPhotoUrl?.let { url ->
            viewModel.sendEvent(RandomDogEvent.LikeDog(url))
        }
        loadNextDog()
    }

    private fun shareDog() {
        val shareBody = currentPhotoUrl ?: return
        requireContext().shareText(getString(R.string.dog), shareBody)
        bringCardBackAfterShare()
    }

    private fun loadNextDog() {
        viewModel.sendEvent(RandomDogEvent.GetNewDog)
    }

    private fun setState(state: RandomDogState) {
        when (state) {
            is RandomDogState.NewDog -> showNewDog(state.dogPhotoUrl)
            is RandomDogState.NetworkConnectionProblem -> showNetworkConnectionError()
            is RandomDogState.Loading -> { }
        }
    }

    private fun showNewDog(dogPhotoUrl: String) = view?.run {
        loadPhotoFromUrl(dogPhotoUrl)
        showAnimationFromMotionState()
        currentPhotoUrl = dogPhotoUrl
        randomDogErrorImage.hideWithFadeOut()
        randomDogPhoto.showWithFadeIn()
    }

    private fun showNetworkConnectionError() = view?.run {
        randomDogErrorImage.setImageResource(R.drawable.ic_no_internet)
        randomDogErrorImage.showWithFadeIn()
        randomDogPhoto.hideWithFadeOut()
        if (randomDogMotionLayout.currentState != R.id.defaultState)
            showNewCard()
    }

    private fun View.loadPhotoFromUrl(dogPhotoUrl: String) {
        Glide.with(this)
            .load(dogPhotoUrl)
            .transition(withCrossFade())
            .into(randomDogPhoto)
    }

    private fun View.showAnimationFromMotionState() {
        when (randomDogMotionLayout.currentState) {
            R.id.nextDogState -> showNewCard()
            R.id.likeDogState -> bringCardBackAfterLike()
        }
    }

    private fun bringCardBackAfterShare() = view?.randomDogMotionLayout?.run {
        setTransition(R.id.showCardAfterShare)
        transitionToEnd()
    }

    private fun bringCardBackAfterLike() = view?.randomDogMotionLayout?.run {
        setTransition(R.id.showCardAfterLike)
        transitionToEnd()
    }

    private fun showNewCard() = view?.randomDogMotionLayout?.playTransition(R.id.showCardWithNewDog)
}