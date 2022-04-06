package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.perfect.prodsuit.R
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.LocationRequest

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import android.location.Geocoder
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import java.util.*

class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    val TAG  :String="LocationPickerActivity"
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        binding = ActivityLocationPickerBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_location_picker)

        getLocationPermission()
        // Try to obtain the map from the SupportMapFragment.
        checkAndRequestPermissions()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


//        googleMap = mapFragment.getMap();
//        googleMap!!.setMyLocationEnabled(true);

        edtSearch = findViewById(R.id.edtSearch) as EditText
        txtSearch = findViewById(R.id.txtSearch) as TextView
        txtSubmit = findViewById(R.id.txtSubmit) as TextView
        imgSearch = findViewById(R.id.imgSearch) as ImageView

        imgSearch!!.setOnClickListener {
            edtSearch!!.setText("")
        }
        txtSearch!!.setOnClickListener {

            try {
                val strName = edtSearch!!.text.toString()
                geocoder = Geocoder(this, Locale.getDefault())
                addresses = geocoder!!.getFromLocationName(strName,1)

                if (addresses != null && !addresses!!.equals("")){
                    googleMap.clear()
                    //   val address = addresses!![0]
                    address = addresses!!.get(0).getAddressLine(0)
                    city = addresses!!.get(0).locality
                    state = addresses!!.get(0).adminArea
                    country = addresses!!.get(0).countryName
                    postalCode = addresses!!.get(0).postalCode
                    knownName = addresses!!.get(0).featureName
                    strLongitue = addresses!![0].longitude.toString()
                    strLatitude = addresses!![0].latitude.toString()
                    val latLng = LatLng(addresses!![0].latitude, addresses!![0].longitude)
                    googleMap!!.addMarker(MarkerOptions().position(latLng).title(address+","+city+","+state+","+country+","+postalCode))
                    googleMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    edtSearch!!.setText(address+","+city+","+state+","+country+","+postalCode)
                }
            }catch (e: Exception){
                Log.e(TAG,"Exception   108   "+e.toString())
            }
//
        }

        txtSubmit!!.setOnClickListener {
            if (addresses != null && !addresses!!.equals("")){
                intent.putExtra("address", address)
                intent.putExtra("city", city)
                intent.putExtra("state", state)
                intent.putExtra("country", country)
                intent.putExtra("postalCode", postalCode)
                intent.putExtra("knownName", knownName)
                intent.putExtra("strLatitude", strLatitude)
                intent.putExtra("strLongitue", strLongitue)

                setResult(SELECT_LOCATION!!, intent)
                finish()
            }

        }




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

    private fun checkAndRequestPermissions(): Boolean {
        val locationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermision =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (coarsePermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0


//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
              //  googleMap.setMyLocationEnabled(true);
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        }
        else {
            buildGoogleApiClient();
           // googleMap.setMyLocationEnabled(true);
            googleMap.setMyLocationEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        // Setting a click event handler for the map
        googleMap.setOnMapClickListener(OnMapClickListener { latLng ->
            googleMap.clear()
            geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder!!.getFromLocation(latLng.latitude, latLng.longitude, 1);
            address = addresses!!.get(0).getAddressLine(0)
            city = addresses!!.get(0).locality
            state = addresses!!.get(0).adminArea
            country = addresses!!.get(0).countryName
            postalCode = addresses!!.get(0).postalCode
            knownName = addresses!!.get(0).featureName

            strLongitue = latLng.longitude.toString()
            strLatitude = latLng.latitude.toString()

            val latLng = LatLng(latLng.latitude, latLng.longitude)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title(address+","+city+","+state+","+country+","+postalCode)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            mCurrLocationMarker = googleMap.addMarker(markerOptions)
            edtSearch!!.setText(address+","+city+","+state+","+country+","+postalCode)

            //move map camera
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11f))
        })

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
    }

    override fun onConnectionSuspended(i: Int) {}

    override fun onLocationChanged(location: Location) {
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        geocoder = Geocoder(this, Locale.getDefault())
        addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1);
        address = addresses!!.get(0).getAddressLine(0)
        city = addresses!!.get(0).locality
        state = addresses!!.get(0).adminArea
        country = addresses!!.get(0).countryName
        postalCode = addresses!!.get(0).postalCode
        knownName = addresses!!.get(0).featureName
        //Place current location marker

        strLongitue = location.longitude.toString()
        strLatitude = location.latitude.toString()

        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(address+","+city+","+state+","+country+","+postalCode)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        mCurrLocationMarker = googleMap.addMarker(markerOptions)
        edtSearch!!.setText(address+","+city+","+state+","+country+","+postalCode)

        //move map camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11f))

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }
    }


    override fun onConnectionFailed(p0: ConnectionResult) {

    }


}