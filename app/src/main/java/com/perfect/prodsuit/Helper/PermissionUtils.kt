package com.perfect.prodsuit.Helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    const val PERMISSIONS_REQUEST_CODE = 100

    fun checkAndRequestPermissions(activity: Activity): Boolean {
        val writeCalendarPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR)
        val readCalendarPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR)

        if (writeCalendarPermission != PackageManager.PERMISSION_GRANTED ||
            readCalendarPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_CALENDAR
            ), PERMISSIONS_REQUEST_CODE)
            return false
        }
        return true
    }
}