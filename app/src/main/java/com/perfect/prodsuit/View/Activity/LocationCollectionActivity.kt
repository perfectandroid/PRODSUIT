package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import java.util.*


class LocationCollectionActivity : AppCompatActivity() , OnMapReadyCallback {

    val TAG = "LocationCollectionActivity"
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    private lateinit var currentLocation: Location
    private lateinit var mMap: GoogleMap
    var latitude="37.4233438"
    var longitude="-122.0728817"
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_collection)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

//        latitude= intent.getStringExtra("lat")!!
//        longitude= intent.getStringExtra("long")!!

//        latitude= "11.2475431"
//        longitude= "75.8341995"
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@LocationCollectionActivity)
        fetchLocation()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun fetchLocation() {

        val ALL_PERMISSIONS = 102

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        if (ContextCompat.checkSelfPermission(
                this@LocationCollectionActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) + ContextCompat.checkSelfPermission(
                this@LocationCollectionActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {

            val supportMapFragment =
                (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
            supportMapFragment!!.getMapAsync(this@LocationCollectionActivity)
        }

//        if (ActivityCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_FINE_LOCATION
//            ) !=
//            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_COARSE_LOCATION
//            ) !=
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
//            )
//            return
//        }
//
//
//
//        val task = fusedLocationProviderClient.lastLocation
//        task.addOnSuccessListener { location ->
//            if (location != null) {
//                currentLocation = location
//
//                Log.e(TAG,"fusedLocationProviderClient  701")
//                val supportMapFragment =
//                    (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
//                supportMapFragment!!.getMapAsync(this@LocationCollectionActivity)
//            }
//        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.i("locationdet",latitude+"\n"+longitude)


        //    val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)

        val latLng = LatLng(latitude.toDouble(), longitude.toDouble())

        val geocoder: Geocoder
        var addresses: List<Address?>
        geocoder = Geocoder(this, Locale.getDefault())
        addresses =
            geocoder.getFromLocation(latitude.toDouble(),longitude.toDouble(), 1) as List<Address?>;
        val city = addresses.get(0)!!.getAddressLine(0);
        Log.i("City",city)


        val markerOptions = MarkerOptions().position(latLng).title(city)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}