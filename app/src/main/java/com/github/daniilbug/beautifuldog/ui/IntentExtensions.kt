package com.github.daniilbug.beautifuldog.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.github.daniilbug.beautifuldog.R

fun Context.shareText(subject: String, text: String) {
    val shareTextIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(shareTextIntent, getString(R.string.share)))
}

fun Context.openUrl(url: String) {
    val openURLIntent = Intent(Intent.ACTION_VIEW)
    openURLIntent.data = Uri.parse(url)
    startActivity(openURLIntent)
}