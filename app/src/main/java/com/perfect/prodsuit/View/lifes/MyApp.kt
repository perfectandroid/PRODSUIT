package com.perfect.prodsuit.View.lifes

import android.app.Application

class MyApp : Application() {

    private lateinit var appLifecycleListener: AppLifecycleListener

    override fun onCreate() {
        super.onCreate()
        appLifecycleListener = AppLifecycleListener()
        registerActivityLifecycleCallbacks(appLifecycleListener)
    }

    fun isAppActive(): Boolean {
        return appLifecycleListener.isAppActive()
    }
}