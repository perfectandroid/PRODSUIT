package com.perfect.prodsuit.View.Service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.HomeActivity
import com.perfect.prodsuit.View.Activity.LocationEnableActivity
import com.perfect.prodsuit.View.Activity.NotificationActivity
import java.util.*

class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var TAG = "LocationHelper"

    fun getCurrentLocation(callback: (Location?) -> Unit) {

        if (isLocationEnabled()) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.e(TAG,"5600003   ")
                callback(null)
                return

            }

            val locationTask: Task<Location> = fusedLocationClient.getLastLocation()
            locationTask.addOnCompleteListener { task ->
                Log.e(TAG,"5600000   "+task.isSuccessful+"  :  "+task.result)
                if (task.isSuccessful && task.result != null) {
                    Log.e(TAG,"5600001   ")
                    callback(task.result)
                } else {
                    Log.e(TAG,"5600002   ")
                    callback(null)
                }
            }
        }else{
            Log.e("TAG","LocationHelper   isLocationEnabled Not")
//            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)

//            val i = Intent(context, HomeActivity::class.java)
//            context.startActivity(i)

            createNotificationChannel(context)
            showNotification(context)
        }


    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun createNotificationChannel(context: Context) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel only on API 26+ (Android 8.0 and above)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("LOCATION_SETTINGS_CHANNEL",
                "Location Settings", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun showNotification(context: Context) {
        val intent = Intent(context, LocationEnableActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

//        val notificationIntent = Intent(this, NotificationActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
//        context.startActivity(intent)
        val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notification)
        val notification = NotificationCompat.Builder(context, "LOCATION_SETTINGS_CHANNEL")
            .setContentTitle("Location Settings")
            .setContentText("Tap to open location settings")
            .setSmallIcon(R.drawable.ic_location)
            .setSilent(false)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, notification)

      //  textToSpeech(context)

        //  showAlert(context)
    }


}