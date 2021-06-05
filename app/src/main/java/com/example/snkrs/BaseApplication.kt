package com.example.snkrs

import android.app.Application
import timber.log.Timber

class BaseApplication : Application() {
  init{
    Timber.plant(Timber.DebugTree())
  }
}