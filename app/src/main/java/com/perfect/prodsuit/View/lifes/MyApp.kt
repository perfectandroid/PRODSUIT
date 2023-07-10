package com.perfect.prodsuit.View.lifes

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApp : Application() {

    private lateinit var appLifecycleListener: AppLifecycleListener

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        appLifecycleListener = AppLifecycleListener()
        registerActivityLifecycleCallbacks(appLifecycleListener)
    }

    fun isAppActive(): Boolean {
        return appLifecycleListener.isAppActive()
    }
}