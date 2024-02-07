package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Transformations.map
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.perfect.prodsuit.R
import java.util.*


class AttendancePickerActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    private var mMap: GoogleMap? = null

    internal lateinit var mLocationResult: LocationRequest
    internal lateinit var mLocationCallback: LocationCallback
    internal var mFusedLocationClient: FusedLocationProviderClient? = null

    val TAG  :String="AttendancePickerActivity"
    lateinit var context: Context
    private lateinit var googleMap : GoogleMap
    private var mLocationPermissionGranted = false
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 8088
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 2
    private var SELECT_LOCATION: Int? = 103
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null
    var imgSearch: ImageView? = null
    var imgMylocation: ImageView? = null
    var edtSearch: EditText? = null
    var txtSearch: TextView? = null
    var txtSubmit: TextView? = null
    var address : String = ""
    var city : String = ""
    var state : String = ""
    var country : String = ""
    var postalCode : String = ""
    var knownName : String = ""
    var strLongitue : String = ""
    var strLatitude : String = ""
    var loc : String = ""


    var lm: LocationManager? = null
    var gps_enabled = false
    var network_enabled = false
    var modeLocation : String? = "1"
    var lati : String?= ""
    var longi : String?= ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_attendance_picker)
        context = this@AttendancePickerActivity

        if (getIntent().hasExtra("longi")) {
            longi = intent.getStringExtra("longi")
        }
        if (getIntent().hasExtra("lati")) {
            lati = intent.getStringExtra("lati")
        }
        if (getIntent().hasExtra("loc")) {
            loc = intent.getStringExtra("loc")!!
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    private fun checkLocationEnabled(): Boolean {

        var result = false


        try {
            gps_enabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            network_enabled = lm!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!gps_enabled && !network_enabled) {
                result = true
            }

        } catch (ex: Exception) {
            result = false
        }

        return result

    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, id: Int) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
            .setNegativeButton("No"
            ) { dialog, id -> dialog.cancel()
                finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun checkAndRequestPermissions(): Boolean {

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

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {

        try {

            googleMap = p0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    googleMap.setMyLocationEnabled(false);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                }
            }
            else {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            }

            if (modeLocation.equals("1")){
                googleMap.setOnMapClickListener(GoogleMap.OnMapClickListener { latLng ->
                    googleMap.clear()
                    try {
                        /*  geocoder = Geocoder(this, Locale.getDefault())
                          addresses =
                              geocoder!!.getFromLocation(latLng.latitude, latLng.longitude, 1);
                          address = addresses!!.get(0).getAddressLine(0)
                          city = addresses!!.get(0).locality
                          state = addresses!!.get(0).adminArea
                          country = addresses!!.get(0).countryName
                          postalCode = addresses!!.get(0).postalCode
                          knownName = addresses!!.get(0).featureName
                          strLongitue = latLng.longitude.toString()
                          strLatitude = latLng.latitude.toString()
  */

                        /*   geocoder = Geocoder(this, Locale.getDefault())
                           val latitude: Double = lati!!.toDouble()
                           val longitude: Double = longi!!.toDouble()

                           Log.e(TAG,"LATS"+latitude+"\n"+longitude)
                           val latLng = LatLng(latitude, longitude)
                           geocoder!!.getFromLocation(latitude, longitude, 1);
                           address = addresses!!.get(0).getAddressLine(0)
                           city = addresses!!.get(0).locality
                           state = addresses!!.get(0).adminArea
                           country = addresses!!.get(0).countryName
                           postalCode = addresses!!.get(0).postalCode
                           knownName = addresses!!.get(0).featureName*/

                        val markerOptions = MarkerOptions()


                        markerOptions.position(latLng)


                        //  markerOptions.title(address + "," + city + "," + state + "," + country + "," + postalCode)
                        markerOptions.title(loc)
                        markerOptions.icon(
                            BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_RED
                            )
                        )
                        mCurrLocationMarker = googleMap.addMarker(markerOptions)
                        //  edtSearch!!.setText(address + "," + city + "," + state + "," + country + "," + postalCode)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11f))


                        googleMap.setOnMarkerClickListener(OnMarkerClickListener { marker ->
                            val position = marker.tag as Int
                            Log.e(TAG,"Position"+address + "," + city + "," + state + "," + country + "," + postalCode)
                            //Using position get Value from arraylist
                            false
                        })



                    } catch (e: Exception) {
                        Log.e(TAG, "Exception   286    " + e.toString())
                    }

                })
            }


        }catch (e: Exception){

            Log.e(TAG,"Exception   207    "+e.toString())
        }

    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()


    }

    @SuppressLint("MissingPermission")
    override fun onConnected(bundle: Bundle?) {

        try {
            mLocationRequest = LocationRequest()
            mLocationRequest!!.setInterval(1000)
            mLocationRequest!!.setFastestInterval(1000)
            mLocationRequest!!.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient!!,
                    mLocationRequest!!,
                    this
                )
            }

            /*  mLocationRequest = LocationRequest()
              mLocationRequest.interval = 1000
              mLocationRequest.fastestInterval = 1000
              mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
              if (ContextCompat.checkSelfPermission(this,
                      Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                  mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                  mFusedLocationClient?.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper())
              }*/

        }catch (e: Exception){
            Log.e(TAG,"Exception   2381   "+e.toString())
        }

    }

    override fun onConnectionSuspended(i: Int) {
        Toast.makeText(applicationContext,"connection suspended", Toast.LENGTH_SHORT).show()
    }

    override fun onLocationChanged(location: Location) {

        try {
            mLastLocation = location

            if (mCurrLocationMarker != null) {
                mCurrLocationMarker!!.remove()
            }

            /* geocoder = Geocoder(this, Locale.getDefault())
             addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1);
             address = addresses!!.get(0).getAddressLine(0)
             city = addresses!!.get(0).locality
             state = addresses!!.get(0).adminArea
             country = addresses!!.get(0).countryName
             postalCode = addresses!!.get(0).postalCode
             knownName = addresses!!.get(0).featureName
             strLongitue = location.longitude.toString()
             strLatitude = location.latitude.toString()*/

            val latitude: Double = lati!!.toDouble()
            val longitude: Double = longi!!.toDouble()

            Log.e(TAG,"LATS"+latitude+"\n"+longitude)

            // val sydney = LatLng(-34.0, 151.0)
            val latLng = LatLng(latitude, longitude)


            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title(loc)
            // markerOptions.title(address+","+city+","+state+","+country+","+postalCode)

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

            mCurrLocationMarker = googleMap.addMarker(markerOptions)

            //   edtSearch!!.setText(address+","+city+","+state+","+country+","+postalCode)

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11f))
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
            }







        }catch (e: Exception){
            Log.e(TAG,"Exception   2382   "+e.toString())
        }

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

        Log.e(TAG,"Exception   2382   "+p0.toString())
        Toast.makeText(applicationContext,"connection failed", Toast.LENGTH_SHORT).show()

    }

}