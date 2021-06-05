package com.example.snkrs.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.onClick(action: (View) -> Unit = {}) {
  setOnClickListener(action)
}

fun View.onClick(listener: View.OnClickListener) {
  setOnClickListener(listener)
}

fun View.onLongClick(action: (View) -> Boolean = { true }) {
  setOnLongClickListener(action)
}

fun View.onClick(listener: View.OnLongClickListener) {
  setOnLongClickListener(listener)
}

/**
 * Extension function on [View] to make Snackbars easier/faster. Builds and displays a basic [Snackbar]
 * of length Snackbar.LENGTH_SHORT
 *
 * @param text the String to display in the toast
 * @param length the length to display the Snackbar
 * */
fun View.snackbar(text: String, length: Int = Snackbar.LENGTH_SHORT) {
  Snackbar.make(this, text, length).show()
}