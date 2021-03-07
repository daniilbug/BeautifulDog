package com.github.daniilbug.beautifuldog.android.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.github.daniilbug.beautifuldog.android.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        val navController = Navigation.findNavController(this, R.id.fragmentContainer)
        NavigationUI.setupWithNavController(mainBottomNavigation, navController)
    }

    fun showBottomNavigation() {
        mainBottomNavigation.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        mainBottomNavigation.visibility = View.GONE
    }
}