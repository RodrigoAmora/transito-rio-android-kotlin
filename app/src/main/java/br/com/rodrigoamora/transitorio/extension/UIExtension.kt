package br.com.rodrigoamora.transitorio.extension

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.hide() {
    this.visibility = View.GONE
}

fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}
