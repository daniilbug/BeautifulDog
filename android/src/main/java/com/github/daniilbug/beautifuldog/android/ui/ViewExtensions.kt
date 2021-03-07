package com.github.daniilbug.beautifuldog.android.ui

import android.view.View
import androidx.annotation.TransitionRes
import androidx.constraintlayout.motion.widget.MotionLayout

fun MotionLayout.playTransition(@TransitionRes id: Int) {
    setTransition(id)
    progress = 0f
    transitionToEnd()
}

fun View.showWithFadeIn(duration: Long = 300L) {
    alpha = 0f
    visibility = View.VISIBLE
    animate().setDuration(duration).alpha(1f).start()
}

fun View.hideWithFadeOut(duration: Long = 300L) {
    animate()
        .setDuration(duration)
        .alpha(0f)
        .withEndAction { visibility = View.GONE }
        .start()
}