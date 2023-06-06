package com.perfect.prodsuit.View.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import com.perfect.prodsuit.View.lifes.MyApp

class LocationStateReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            // Handle the location state change
            if (isLocationEnabled) {
                // Location is enabled
                Toast.makeText(context, "Location is enabled", Toast.LENGTH_SHORT).show()
            } else {
                // Location is disabled
                Toast.makeText(context, "Location is disabled", Toast.LENGTH_SHORT).show()
              //  openLocationSettings(context)
            }
        }
    }

    private fun openLocationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}