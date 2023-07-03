package com.perfect.prodsuit.View.Service

import android.location.Location

interface MyLocationListener {
    fun onLocationChanged(location: Location?)
}