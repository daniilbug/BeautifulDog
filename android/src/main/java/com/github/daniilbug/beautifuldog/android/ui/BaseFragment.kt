package com.github.daniilbug.beautifuldog.android.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutId: Int, private val needBottomNavigation: Boolean) : Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareParentForTransitions()
    }

    private fun prepareParentForTransitions() = parentFragment?.also { parentFragment ->
        parentFragment.postponeEnterTransition()
        parentFragment.view?.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireActivity() as? MainActivity ?: return
        setBottomNavigationVisibility(activity)
    }

    private fun setBottomNavigationVisibility(activity: MainActivity) {
        if (needBottomNavigation) {
            activity.showBottomNavigation()
        } else {
            activity.hideBottomNavigation()
        }
    }
}