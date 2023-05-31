package com.perfect.prodsuit.View.lifes

import android.app.Activity
import android.app.Application
import android.os.Bundle

class AppLifecycleListener: Application.ActivityLifecycleCallbacks  {

    private var appActive = false

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // Called when a new activity is created (may not be the foreground activity)
    }

    override fun onActivityStarted(activity: Activity) {
        // Called when the activity becomes visible to the user
        appActive = true
    }

    override fun onActivityResumed(activity: Activity) {
        // Called when the activity is resumed (comes to the foreground)
        appActive = true
    }

    override fun onActivityPaused(activity: Activity) {
        // Called when the activity is paused (goes to the background)
        appActive = false
    }

    override fun onActivityStopped(activity: Activity) {
        // Called when the activity is stopped (no longer visible to the user)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Called before the activity is destroyed to save its state
    }

    override fun onActivityDestroyed(activity: Activity) {
        // Called when the activity is destroyed
    }

    fun isAppActive(): Boolean {
        return appActive
    }
}