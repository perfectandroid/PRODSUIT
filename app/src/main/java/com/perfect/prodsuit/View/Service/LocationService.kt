package com.perfect.prodsuit.View.Service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import java.util.*

class LocationService: Service(), LocationListener {

    private lateinit var locationManager: LocationManager
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null

    override fun onCreate() {
        super.onCreate()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Request location updates
        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        return START_STICKY
    }

    override fun onLocationChanged(location: Location) {
        // Handle location updates here
        val latitude = location.latitude
        val longitude = location.longitude
        // Do something with the latitude and longitude

        Log.e("TAG","SSSS   366633   "+latitude+" : "+longitude)
        try {
            geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1)
            addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1)
            var address = addresses!!.get(0).getAddressLine(0)
            var city = addresses!!.get(0).locality
            var state = addresses!!.get(0).adminArea
            var country = addresses!!.get(0).countryName
            var postalCode = addresses!!.get(0).postalCode
            var knownName = addresses!!.get(0).featureName
            var strLongitue = location.longitude.toString()
            var strLatitude = location.latitude.toString()

            Log.e("TAG","SSSS   366644   "+address+" : "+city+" : "+state+" : "+country+" : "+postalCode+" : "+knownName)
        }catch (e: Exception){
            Log.e("TAG","SSSS   366655   "+e.toString())
        }


    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            // GPS provider enabled
            // Perform actions specific to GPS provider
            Log.e("TAG","SSSS   366666   ")
        } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            // Network provider enabled
            // Perform actions specific to network provider
            Log.e("TAG","SSSS   366677   ")
        }
    }

    override fun onProviderDisabled(provider: String) {
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}

