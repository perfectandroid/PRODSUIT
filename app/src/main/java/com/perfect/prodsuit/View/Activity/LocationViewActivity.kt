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
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.perfect.prodsuit.R
import java.util.*

class LocationViewActivity : AppCompatActivity() , OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

    val TAG  :String="LocationViewActivity"
    lateinit var context: Context
    var lm: LocationManager? = null
    var gps_enabled = false
    var network_enabled = false

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 8088
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 2
    private lateinit var mMap: GoogleMap

    var mode: String?= ""
    var latitude: String?= ""
    var longitude: String?= ""

    var mLocationRequest: LocationRequest? = null
    private lateinit var googleMap : GoogleMap
    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null
    var txtLocation: TextView? = null

    var ll_Close: LinearLayout? = null
    var ll_Cancel: LinearLayout? = null
    var ll_Edit: LinearLayout? = null
    var ll_Direction: LinearLayout? = null
    var ll_Update: LinearLayout? = null

    var mapLatitude: String?= ""
    var mapLongitude: String?= ""

    var city = ""
    private var SELECT_LOCATION: Int? = 103

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_location_view)

        context = this@LocationViewActivity

        mode  = intent.getStringExtra("mode")
        latitude  = intent.getStringExtra("latitude")
        longitude  = intent.getStringExtra("longitude")
        setId()

        if (mode.equals("0")){
            ll_Close!!.visibility = View.GONE
            ll_Cancel!!.visibility = View.VISIBLE
            ll_Edit!!.visibility = View.GONE
            ll_Direction!!.visibility = View.GONE
            ll_Update!!.visibility = View.VISIBLE
        }
        if (mode.equals("1")){
            ll_Close!!.visibility = View.VISIBLE
            ll_Cancel!!.visibility = View.GONE
            ll_Edit!!.visibility = View.VISIBLE
            ll_Direction!!.visibility = View.VISIBLE
            ll_Update!!.visibility = View.GONE
        }

        lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (checkLocationEnabled()){
            buildAlertMessageNoGps();
        }
        checkAndRequestPermissions()


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun setId() {
        txtLocation = findViewById(R.id.txtLocation)

        ll_Close = findViewById(R.id.ll_Close)
        ll_Cancel = findViewById(R.id.ll_Cancel)
        ll_Edit = findViewById(R.id.ll_Edit)
        ll_Direction = findViewById(R.id.ll_Direction)
        ll_Update = findViewById(R.id.ll_Update)


        ll_Close!!.setOnClickListener(this)
        ll_Cancel!!.setOnClickListener(this)
        ll_Edit!!.setOnClickListener(this)
        ll_Direction!!.setOnClickListener(this)
        ll_Update!!.setOnClickListener(this)

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

//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
////        latitude = "11.2475921"
////        longitude = "75.8342245"
//        Log.i("locationdet",latitude+"\n"+longitude)
//
//
//        //    val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
//
//        val latLng = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
//
//        val geocoder: Geocoder
//        var addresses: List<Address?>
//        geocoder = Geocoder(this, Locale.getDefault())
//        addresses = geocoder.getFromLocation(latitude!!.toDouble(),longitude!!.toDouble(), 1);
//        val city = addresses.get(0)!!.getAddressLine(0);
//        Log.i("City",city)
//
//
//        val markerOptions = MarkerOptions().position(latLng).title(city)
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
//        googleMap.addMarker(markerOptions)
//    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG,"174  onRestart ")
        if (checkLocationEnabled()){
            buildAlertMessageNoGps();
        }
        checkAndRequestPermissions()
    }

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
        }catch (e: Exception){
            Log.e(TAG,"Exception   2381   "+e.toString())
        }

    }

    override fun onConnectionSuspended(i: Int) {}

    override fun onLocationChanged(location: Location) {

        try {
            mLastLocation = location

            if (mCurrLocationMarker != null) {
                mCurrLocationMarker!!.remove()
            }

            Log.e(TAG,"mode   280   "+mode)

            if (mode.equals("0")){
                mapLatitude = location.latitude.toString()
                mapLongitude = location.longitude.toString()
            }

            if (mode.equals("1")){
                mapLatitude = latitude
                mapLongitude = longitude
            }

            geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder!!.getFromLocation(mapLatitude!!.toDouble(), mapLongitude!!.toDouble(), 1);
            var address = addresses!!.get(0).getAddressLine(0)
            city = addresses!!.get(0).locality
            var state = addresses!!.get(0).adminArea
            var country = addresses!!.get(0).countryName
            var postalCode = addresses!!.get(0).postalCode
            var knownName = addresses!!.get(0).featureName
            var strLongitue = location.longitude.toString()
            var strLatitude = location.latitude.toString()
            val latLng = LatLng(mapLatitude!!.toDouble(), mapLongitude!!.toDouble())

            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)

        //    markerOptions.title(address+","+city+","+state+","+country+","+postalCode)

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

            mCurrLocationMarker = googleMap.addMarker(markerOptions)

            txtLocation!!.setText(address+","+city+","+state+","+country+","+postalCode)

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
    }

    override fun onClick(v: View) {
        when (v!!.id) {

            R.id.ll_Close -> {
                onBackPressed()
            }
            R.id.ll_Cancel -> {

                if (longitude.equals("") || latitude.equals("")){
                    onBackPressed()
                }else{
                    mode = "1"
                    ll_Close!!.visibility = View.VISIBLE
                    ll_Cancel!!.visibility = View.GONE
                    ll_Edit!!.visibility = View.VISIBLE
                    ll_Direction!!.visibility = View.VISIBLE
                    ll_Update!!.visibility = View.GONE

                    val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)
                }

            }
            R.id.ll_Edit -> {
                mode = "0"
                ll_Close!!.visibility = View.GONE
                ll_Cancel!!.visibility = View.VISIBLE
                ll_Edit!!.visibility = View.GONE
                ll_Direction!!.visibility = View.GONE
                ll_Update!!.visibility = View.VISIBLE

                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
            R.id.ll_Direction -> {
                val mapUri =
                    Uri.parse("http://maps.google.com/maps?q=loc:" + mapLatitude + "," + mapLongitude + " (" + city + ")")
                val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
            R.id.ll_Update -> {

                intent.putExtra("address", txtLocation!!.text.toString())
                intent.putExtra("mapLatitude", mapLatitude)
                intent.putExtra("mapLongitude", mapLongitude)
                setResult(SELECT_LOCATION!!, intent)
                finish()
            }
        }
    }


//    override fun onBackPressed() {
//        //super.onBackPressed()
//    }
}



