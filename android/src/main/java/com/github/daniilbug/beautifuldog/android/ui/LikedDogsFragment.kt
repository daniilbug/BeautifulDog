package com.github.daniilbug.beautifuldog.android.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.github.daniilbug.beautifuldog.android.R
import com.github.daniilbug.beautifuldog.android.ui.adapter.LikedDogsAdapter
import com.github.daniilbug.beautifuldog.android.viewmodel.AndroidLikedDogsViewModel
import com.github.daniilbug.beautifuldog.viewmodel.likeddogs.LikedDogsState
import kotlinx.android.synthetic.main.fragment_liked_dogs.*
import kotlinx.android.synthetic.main.fragment_liked_dogs.view.*
import kotlinx.android.synthetic.main.fragment_liked_dogs.view.likedDogsLoadingBar
import kotlinx.android.synthetic.main.layout_error.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class LikedDogsFragment : BaseFragment(R.layout.fragment_liked_dogs, needBottomNavigation = true) {
    private val viewModel: AndroidLikedDogsViewModel by viewModel()
    private lateinit var likedDogsAdapter: LikedDogsAdapter
    private var errorLayout: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnPreDraw { startPostponedEnterTransition() }
        likedDogsAdapter = LikedDogsAdapter(onClick = { photo, url -> showFullPhoto(photo, url) })
        view.likedDogsRecycler.adapter = likedDogsAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycle.coroutineScope.launch {
            viewModel.state.collectLatest { state -> setState(state) }
        }
    }

    private fun setState(state: LikedDogsState) {
        when (state) {
            is LikedDogsState.Loading -> showLoading()
            is LikedDogsState.Empty -> showEmpty()
            is LikedDogsState.Error -> showError(state.message)
            is LikedDogsState.Loaded -> showDogPhotos(state.dogPhotos)
        }
    }

    private fun showLoading() = view?.run {
        likedDogsLoadingBar.showWithFadeIn()
        likedDogsRecycler.hideWithFadeOut()
        errorLayout?.hideWithFadeOut()
    }

    private fun showEmpty() = view?.run {
        likedDogsLoadingBar.hideWithFadeOut()
        setErrorLayoutAndDo {
            errorImage.setImageResource(R.drawable.ic_empty)
            errorText.setText(R.string.empty_liked_dogs)
            showWithFadeIn()
        }
    }

    private fun showError(message: String) = view?.run {
        likedDogsLoadingBar.hideWithFadeOut()
        setErrorLayoutAndDo {
            errorImage.setImageResource(R.drawable.ic_error)
            errorText.text = message
            showWithFadeIn()
        }
    }

    private inline fun View.setErrorLayoutAndDo(body: View.() -> Unit) {
        errorLayout = errorLayout ?: likedDogsViewStub.inflate()
        errorLayout?.body()
    }

    private fun showDogPhotos(dogPhotos: List<String>) = view?.run {
        likedDogsAdapter.submitList(dogPhotos)
        likedDogsRecycler.showWithFadeIn()
        likedDogsLoadingBar.hideWithFadeOut()
        errorLayout?.hideWithFadeOut()
    }

    private fun showFullPhoto(photo: View, url: String) {
        prepareForTransition()
        val extras = FragmentNavigatorExtras(photo to photo.transitionName)
        val args = bundleOf("photoUrl" to url)
        findNavController().navigate(R.id.showFullPhoto, args, null, extras)
    }

    private fun prepareForTransition() {
        postponeEnterTransition()
    }
}