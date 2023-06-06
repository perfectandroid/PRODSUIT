package com.perfect.prodsuit.View.Service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationListener
import android.os.BatteryManager
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.View.Activity.EmiCollectionActivity
import com.perfect.prodsuit.View.lifes.MyApp
import java.util.*

class LocationUpdateService : Service() {
   // , LocationListener

    ////   31
    var TAG = "LocationUpdateService"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var timer: Timer
    private var locationStateReceiver: LocationStateReceiver? = null
    lateinit var context: Context
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null


    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"35901   ")
        context = this
        timer = Timer()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { location ->
                    // Handle the received location
                    // ...

                    try {
                        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
                            registerReceiver(null, ifilter)
                        }

                        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                        val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

                        val batteryPercentage = (level.toFloat() / scale.toFloat() * 100).toInt()

                        geocoder = Geocoder(context, Locale.getDefault())
                        addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1)
                        var address = addresses!!.get(0).getAddressLine(0)
                        Log.e(TAG,"35101 Location           :    "+location.latitude+"  :  "+location.longitude)
                        Log.e(TAG,"35102 City           :    "+address)
                        Log.e(TAG,"35103 batteryPercentage  :   "+batteryPercentage)

                    }catch (e : Exception){

                    }

                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startTimer()
        startLocationUpdates()
        Log.e(TAG,"35902   ")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        stopLocationUpdates()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = TIMER_INTERVAL // Update interval in milliseconds
            fastestInterval = 0 // Fastest update interval in milliseconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)


    }

    private fun openLocationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun startTimer() {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Perform the desired task here
                Log.e("MyService  123454  :  ", "Timer task executed")
                val isAppActive = (application as MyApp).isAppActive()
                Log.e("ACIVE  123455   ", "isAppActive: $isAppActive")
                if (isAppActive){
                    if (checkPermissions()) {
                        if (isLocationEnabled()) {
                        }else{
                            openLocationSettings(context)
                        }
                    }

                }

            }
        }, 0, TIMER_INTERVAL)
    }

    private fun stopTimer() {
        timer.cancel()
    }

    companion object {
        private const val TIMER_INTERVAL: Long = 10000 // 10 second
    }


    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }


}