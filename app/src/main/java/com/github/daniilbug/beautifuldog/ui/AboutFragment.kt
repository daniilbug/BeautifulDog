package com.github.daniilbug.beautifuldog.ui

import android.os.Bundle
import android.view.View
import com.github.daniilbug.beautifuldog.R
import kotlinx.android.synthetic.main.fragment_about.view.*

class AboutFragment: BaseFragment(R.layout.fragment_about, needBottomNavigation = true) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.aboutCheckButton.setOnClickListener {
            openApiUrl()
        }
    }

    private fun openApiUrl() {
        requireContext().openUrl(getString(R.string.api_url))
    }
}