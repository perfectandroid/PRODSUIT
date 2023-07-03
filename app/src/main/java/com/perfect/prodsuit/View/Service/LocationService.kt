package com.perfect.prodsuit.View.Service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.*
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.*
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.lifes.MyApp
import org.json.JSONObject
import java.util.*


class LocationService : Service() {

    var TAG = "LocationService"
    private lateinit var timer: Timer
    lateinit var context: Context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private val notificationChannelId = "LOCATION_SETTINGS_CHANNEL"
    private val notificationId = 1
    private var wakeLock: PowerManager.WakeLock? = null

    fun NotificationService() {}


    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"52011   onCreate")
        context = this
        //val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
       // acquireWakeLock()
        timer = Timer()



    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var TIMER_INTERVAL = Config.getMilliSeconds(context).toLong()

//        acquireWakeLock()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Perform the desired task here

                var isFor = Config.isAppInForeground(context)
                Log.e(TAG,"600001    :   onStartCommand")
                val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
                if (!UtilityListSP.getString("UtilityList", "").equals("")){
                    val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
                    var bTracker = jsonObj!!.getString("LOCATION_TRACKING").toBoolean()
                    Log.e(TAG,"600002    :   bTracker  "+bTracker)
                    if (bTracker){
                        Log.e(TAG,"600002441    :   bTracker"+checkPermissions())
                        if (checkPermissions()) {
                            Log.e(TAG,"600002442    :   bTracker")
                            if (isLocationEnabled()) {
                                Log.e(TAG,"600003    :   bTracker isLocationEnabled")
                                val gpsStatusReceiver = GpsStatusReceiver()
                                val filter1 = IntentFilter(LocationManager.GPS_PROVIDER)
                                registerReceiver(gpsStatusReceiver, filter1)
                                gpsStatusReceiver.startGpsStatusCheck(context)

                            }else{
                                Log.e(TAG,"600004    :   bTracker Disabled")
                                val gpsStatusReceiver = GpsStatusReceiver()
                                val filter1 = IntentFilter(LocationManager.GPS_PROVIDER)
                                registerReceiver(gpsStatusReceiver, filter1)
                                gpsStatusReceiver.startGpsStatusCheck(context)
                            }
                        }else{
                            Log.e(TAG,"600005    :   else")
                           // openLocationSettings(context)
                           // showNotification(context)

                            val gpsStatusReceiver = GpsStatusReceiver()
                            val filter1 = IntentFilter(LocationManager.GPS_PROVIDER)
                            registerReceiver(gpsStatusReceiver, filter1)
                            gpsStatusReceiver.startGpsStatusCheck(context)
                        }
                    }else{
                        Log.e(TAG,"600006    :   onStartCommand")
                        stopForegroundLocationService(context)
                    }
                }


            }
        }, 0,TIMER_INTERVAL)


        return START_STICKY
    }



    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {

        Handler().postDelayed({
            val restartServiceIntent = Intent(applicationContext, this.javaClass)
            restartServiceIntent.setPackage(packageName)
            startService(restartServiceIntent)
            super.onTaskRemoved(rootIntent)
        }, 3000)

    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {

        var result = false

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    private fun openLocationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        Log.e(TAG,"52015   startLocationUpdates")
        TIMER_INTERVAL = Config.getMilliSeconds(context).toLong()
        val locationRequest = LocationRequest.create().apply {
            interval = TIMER_INTERVAL // Update interval in milliseconds
            fastestInterval = 0 // Fastest update interval in milliseconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)


    }

    companion object {

        private var TIMER_INTERVAL: Long = 30000 // 10 second
    }

    private fun createLocationCallback(): LocationCallback {
        Log.e(TAG,"hjdfvfhkjfghkj123")
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                Log.e(TAG,"hjdfvfhkjfghkj1234")
                locationResult?.lastLocation?.let { location ->
                    // Handle location updates here
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // Do something with the coordinates
                    Log.e(TAG,"hjdfvfhkjfghkj")
                }
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                val isLocationAvailable = locationAvailability?.isLocationAvailable ?: false
                // Handle location availability changes here
            }
        }
    }



    private fun getCurrentGPSLocation() {
        var locationRequest =  LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(2500)
            .setFastestInterval(1500)
            .setNumUpdates(5)
    }


    private fun startTimer() {
        Log.e(TAG,"52017   startTimer")
        var TIMER_INTERVAL = Config.getMilliSeconds(context).toLong()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Perform the desired task here
                var isFor = Config.isAppInForeground(context)
                Log.e(TAG,"18444  isFor  :   "+isFor)
                Log.e("MyService  123454  :  ", "Timer task executed")
                val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
                val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
                var bTracker = jsonObj!!.getString("LOCATION_TRACKING").toBoolean()
                if (bTracker){
                    val isAppActive = (application as MyApp).isAppActive()

//                    if (isAppActive){
                 //   if (isAppActive){
                        if (checkPermissions()) {
                            if (isLocationEnabled()) {
                                Log.e("ACIVE  5412361   ", "isAppActive: $isAppActive")
                            }else{
                                Log.e("ACIVE  54123612   ", "isAppActive: $isAppActive")
                                openLocationSettings(context)
                            }
                        }else{
                            Log.e("ACIVE  54123613   ", "isAppActive: $isAppActive")
                        }

                    //}

                }


            }
        }, 0, TIMER_INTERVAL)
    }



    private fun createNotificationChannel(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel only on API 26+ (Android 8.0 and above)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Location Settings",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(context: Context) {
//        val intent = Intent(context, LocationSettingsActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        //context.startActivity(intent)

        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle("Location Settings12")
            .setContentText("Tap to open location settings")
            .setSmallIcon(R.drawable.ic_location)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationId, notification)
    }

    private fun stopForegroundLocationService(context: Context) {
        val serviceIntent = Intent(context, ForegroundLocationService::class.java)
        context?.stopService(serviceIntent)
    }

    private fun acquireWakeLock() {
//        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
//        wakeLock = powerManager.newWakeLock(
//            PowerManager.PARTIAL_WAKE_LOCK,
//            "ForegroundLocationService::WakeLock"
//        )
//        wakeLock?.acquire()

    }

    override fun onDestroy() {
        super.onDestroy()
     //   wakeLock?.release()
//
//        val serviceIntent = Intent(this, LocationService::class.java)
//        startService(serviceIntent)
        Log.e(TAG,"6000012    :   onDestroy")
    }


}