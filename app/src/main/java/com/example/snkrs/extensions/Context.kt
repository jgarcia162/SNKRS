package com.example.snkrs.extensions

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.widget.Toast

val Context.isConnected: Boolean
  get() {
    return (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
      .activeNetworkInfo?.isConnected == true
  }

/**
 * Extension function on [Context] to make "toasting" easier. Builds and displays a basic [Toast]
 * of length Toast.LENGTH_SHORT
 *
 * @param text the String to display in the toast
 * */
fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

