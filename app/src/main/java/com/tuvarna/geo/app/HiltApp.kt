package com.tuvarna.geo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HiltApp : Application() {

  override fun onCreate() {
    super.onCreate()

    Timber.plant(Timber.DebugTree())
  }
}
