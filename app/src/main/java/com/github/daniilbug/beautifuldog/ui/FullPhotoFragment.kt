package com.github.daniilbug.beautifuldog.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.daniilbug.beautifuldog.R
import com.github.daniilbug.beautifuldog.viewmodel.AndroidFullPhotoViewModel
import com.github.daniilbug.beautifuldog.viewmodel.fullphoto.FullPhotoEvent
import com.github.daniilbug.beautifuldog.viewmodel.fullphoto.FullPhotoState
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_full_photo.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class FullPhotoFragment: BaseFragment(R.layout.fragment_full_photo, needBottomNavigation = false) {
    private val viewModel: AndroidFullPhotoViewModel by viewModel()

    private val photoUrl: String by lazy {
        arguments?.getString("photoUrl")
            ?: error("There must be photoUrl parameter for ${FullPhotoFragment::class.simpleName}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            setUpTransition(photoUrl)
            loadPhoto(photoUrl)
            setMotionStateListener()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycle.coroutineScope.launch {
            viewModel.state.collectLatest { state ->
                setState(state)
            }
        }
    }

    private fun setState(state: FullPhotoState) {
        when (state) {
            is FullPhotoState.Default -> {}
            is FullPhotoState.Unliked -> goToPhotoList()
        }
    }

    private fun View.setMotionStateListener() {
        fullPhotoMotionLayout.addTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                processMotionState(currentId)
            }
        })
    }

    private fun View.loadPhoto(photoUrl: String) {
        Glide.with(this)
            .load(photoUrl)
            .transform(RoundedCorners(4))
            .into(fullPhotoImage)
    }

    private fun View.setUpTransition(photoUrl: String) {
        fullPhotoImage.transitionName = photoUrl
        val transition = MaterialContainerTransform().apply {
            setPathMotion(MaterialArcMotion())
            scrimColor = Color.TRANSPARENT
        }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    private fun processMotionState(currentId: Int) {
        when (currentId) {
            R.id.unlikeState -> unlikePhoto()
            R.id.shareFullState -> sharePhoto()
            R.id.cardOnLeftState, R.id.cardOnRightState -> navigateBack()
        }
    }

    private fun unlikePhoto() {
        viewModel.sendEvent(FullPhotoEvent.Unlike(photoUrl))
    }

    private fun sharePhoto() {
        requireContext().shareText(getString(R.string.dog), photoUrl)
        bringCardBack()
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun bringCardBack() = view?.fullPhotoMotionLayout?.run {
        setTransition(R.id.bringCardBack)
        transitionToEnd()
    }

    private fun goToPhotoList() = view?.run {
        fullPhotoImage.transitionName = ""
        findNavController().popBackStack()
    }
}