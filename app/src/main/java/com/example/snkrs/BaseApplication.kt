package com.example.snkrs

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {
  init{
    Timber.plant(Timber.DebugTree())
  }
}