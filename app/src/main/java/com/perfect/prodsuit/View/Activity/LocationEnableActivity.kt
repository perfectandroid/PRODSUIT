package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import java.util.*

class LocationEnableActivity : AppCompatActivity() {

    var TAG = "LocationEnableActivity"
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 2
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var btnEnable: TextView? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_location_enable)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        btnEnable = findViewById<TextView>(R.id.btnEnable)
        runTimePermission()
        getLastLocation()

        btnEnable!!.setOnClickListener {

          //  finishAffinity()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun runTimePermission() {
        Log.e(TAG,"9400001    runTimePermission  ")

        if (isLocationEnabled()){
            if (checkPermissions()) {
                Log.e(TAG,"9400002    runTimePermission  ")
                if (isLocationEnabled()) {
                    Log.e(TAG,"9400003    runTimePermission  ")
                    getLastLocation()
                } else {
                    Log.e(TAG,"9400004    runTimePermission  ")
                    // Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                 //   confirmBottomSheet("1")

                }
            } else {
                Log.e(TAG,"9400005    runTimePermission  ")
                // requestPermissions()
                onBackPressed()
            }
        }else{

            Log.e(TAG,"9400006    runTimePermission  ")
          //  confirmBottomSheet("1")


        }

    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            return true
//        }
//        return false

        var result = false
        val locationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermision =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        val commandPermision =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)



        val backgroundPermision =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (coarsePermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (commandPermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
        }
        if (backgroundPermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }


//        if (backgroundPermision != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            result  = true
        }
        return result
    }


    private fun confirmBottomSheet(type : String) {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.confirm_popup, null)

        val img_confirm = view.findViewById<ImageView>(R.id.img_confirm)
        val btnNo = view.findViewById<TextView>(R.id.btnNo)
        val btnYes = view.findViewById<TextView>(R.id.btnYes)
        val tv_text = view.findViewById<TextView>(R.id.tv_text)

        if (type.equals("1")){
            tv_text.setText("Please give us access to your GPS Location")
        }


        btnNo.setOnClickListener {
            dialog.dismiss()
            if (type.equals("1")){
                onBackPressed()
            }
        }
        btnYes.setOnClickListener {
            dialog.dismiss()
            if (type.equals("1")){
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        try {
            mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                var location: Location? = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {

                    try {

                        onBackPressed()
                    }catch (e: Exception){
                        Log.e("TAG","SSSS   170112   "+e.toString())
                    }

                }
            }
        }catch (e : Exception){

        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation

            try {

                Log.e("TAG","SSSS   170113   ")
            }catch (e: Exception){
                Log.e("TAG","SSSS   170112   "+e.toString())
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG,"940000551    onRestart  ")
        runTimePermission()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Log.e(TAG,"940000552    onTopResumedActivityChanged  ")
    }
}