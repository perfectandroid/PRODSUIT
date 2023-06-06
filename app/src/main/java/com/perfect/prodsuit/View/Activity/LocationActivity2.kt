package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.perfect.prodsuit.R
import java.io.IOException
import java.util.*

class LocationActivity2 : FragmentActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    private var locationRequest: LocationRequest? = null
    private val REQUEST_TURN_ON_LOCATION_SETTINGS = 100
    private val OPEN_SETTINGS = 200
    private var mComboAnim: Animation? = null
    private var mImgLocation: ImageView? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationCallback: LocationCallback? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    private var mMap: GoogleMap? = null
    var lat: Double? = null
    var longi: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_location2)
        askPermissionForLocation()
        mImgLocation = findViewById(R.id.imgLocation)
        mComboAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.combo)
        with(mImgLocation) { this?.startAnimation(mComboAnim) }

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API).addConnectionCallbacks(this).build()


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        lat = location.latitude
                        longi = location.longitude
                        val locat = LatLng(lat!!, longi!!)
                        val builder = LatLngBounds.Builder()
                        builder.include(locat)
                        val bounds = builder.build()
                        val padding = 0 // offset from edges of the map in pixels
                        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                        mMap!!.animateCamera(cu)
                        val handler = Handler()
                        handler.postDelayed({ // Do something after 5s = 5000ms
                            sendLocation(location.latitude, location.longitude)
                        }, 3500)
                        break
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
    }

    private fun sendLocation(lat: Double, longi: Double) {
        var getAddress = ""
        try {
            val geocoder: Geocoder
            val addresses: List<Address>
            geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder.getFromLocation(lat, longi, 1)
            val address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            val city = addresses[0].locality
            /*  String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();*/getAddress = "$address.$city"
        } catch (e: IOException) {
            if (true) {
                Log.e("Dbg", e.toString())
            }
        }
        val intent = Intent()
        val bundle = Bundle()
        bundle.putDouble("LONGITUDE", longi)
        bundle.putDouble("LATITUDE", lat)
        bundle.putString("ADDRESS", getAddress)
        intent.putExtras(bundle)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun askPermissionForLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                gotoSettings()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                gotoSettings()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ), 100
                )
            }
        } else promptRequest()
    }

    private fun gotoSettings() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Please grant permission to continue with this section")
            .setCancelable(false)
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which -> this@LocationActivity2.finish() })
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", this@LocationActivity2.getPackageName(), null)
                intent.data = uri
                this@LocationActivity2.startActivityForResult(intent, OPEN_SETTINGS)
            })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
                promptRequest()
            } else {
                Toast.makeText(this, "App must need permission", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun promptRequest() {
        locationRequest = LocationRequest.create()
        locationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest!!.setInterval(10000)
        locationRequest!!.setFastestInterval((10000 / 2).toLong())
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val client = LocationServices.getSettingsClient(this@LocationActivity2)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener(
            this@LocationActivity2,
            OnSuccessListener<LocationSettingsResponse?> {
                if (!mGoogleApiClient!!.isConnecting && !mGoogleApiClient!!.isConnected) {
                    mGoogleApiClient!!.connect()
                }
            })
        task.addOnFailureListener(this@LocationActivity2,
            OnFailureListener { e ->
                if (e is ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        e.startResolutionForResult(
                            this@LocationActivity2, REQUEST_TURN_ON_LOCATION_SETTINGS
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TURN_ON_LOCATION_SETTINGS) {
            if (resultCode == RESULT_OK) {
                if (!mGoogleApiClient!!.isConnecting && !mGoogleApiClient!!.isConnected) {
                    mGoogleApiClient!!.connect()
                }
            }
        } else {
            askPermissionForLocation()
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                this,
                "You need to enable permissions to display location !",
                Toast.LENGTH_SHORT
            ).show()
        }
        try {
            mFusedLocationClient!!.requestLocationUpdates(locationRequest, mLocationCallback, null)
        } catch (e: Exception) {
            Log.e("e", e.toString())
        }
    }
}