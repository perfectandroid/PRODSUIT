package com.perfect.prodsuit.View.Activity
import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.LocationViewModel
import org.json.JSONObject
import java.util.*

class LocationActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener{

    private var progressDialog: ProgressDialog? = null
    private var Id_leadgenrteprod: String? = null
    lateinit var context: Context
    lateinit var locationViewModel: LocationViewModel
    private lateinit var mMap: GoogleMap
    internal var mCurrLocationMarker: Marker? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    private lateinit var currentLocation: Location
    var latitude=""
    var longitude=""
    var count =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        Id_leadgenrteprod = intent.getStringExtra("prodid")

        latitude= intent.getStringExtra("lat")!!
        longitude= intent.getStringExtra("long")!!

        setRegViews()

        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@LocationActivity)
        fetchLocation()


    }
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
            )
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location

                val supportMapFragment =
                    (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
                supportMapFragment!!.getMapAsync(this@LocationActivity)
            }
        }
    }
    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
    }

    companion object {
        var strid= ""
    }



    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.i("locationdet",latitude+"\n"+longitude)


    //    val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)

        val latLng = LatLng(latitude.toDouble(), longitude.toDouble())

        val geocoder: Geocoder
        var addresses: List<Address?>
        geocoder = Geocoder(this, Locale.getDefault())
        addresses = geocoder.getFromLocation(currentLocation.latitude,currentLocation.longitude, 1);
        val city = addresses.get(0)!!.getAddressLine(0);
        Log.i("City",city)


        val markerOptions = MarkerOptions().position(latLng).title(city)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              //  fetchLocation()
            }
        }
    }
}


