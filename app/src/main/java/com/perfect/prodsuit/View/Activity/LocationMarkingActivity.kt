package com.perfect.prodsuit.View.Activity

import android.content.Context
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R


class LocationMarkingActivity : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {

    lateinit var context: Context
    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null

    var TamWorth = LatLng(-31.083332, 150.916672)
    var NewCastle = LatLng(-32.916668, 151.750000)
    var Brisbane = LatLng(-27.470125, 153.021072)
    private var locationArrayList: ArrayList<LatLng>? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_location_marking)

        context = this@LocationMarkingActivity
        setRegViews()
//
//        locationArrayList!!.add(TamWorth)
//        locationArrayList!!.add(NewCastle)
//        locationArrayList!!.add(Brisbane)
//
//        mapView = findViewById(R.id.map_view);
//        mapView!!.onCreate(savedInstanceState);
//        mapView!!.getMapAsync(this);

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)

        imback!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
//        mMap = googleMap
//        // inside on map ready method
//        // we will be displaying all our markers.
//        // for adding markers we are running for loop and
//        // inside that we are drawing marker on our map.

//        googleMap = map
//        for (i in locationArrayList!!.indices) {
//
//            // below line is use to add marker to each location of our array list.
////            mMap!!.addMarker(MarkerOptions().position(locationArrayList!![i]).title("Marker").snippet("fgfgfgfdxzxzxds"))
////
////            // below line is use to zoom our camera on map.
////            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
////
////            // below line is use to move our camera to the specific location.
////            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!![i]))
//
//            addMarkerWithIconAndTitle(locationArrayList!![i], "Marker 1", R.drawable.person_location)
//
//            googleMap!!.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
//            googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!![i]))
//        }


        // Add markers with icons and titles

        googleMap = map

        // Add markers with icons and titles
        addMarkerWithIconAndTitle(LatLng(11.2590, 75.7863), "Calicut MOFUSIAL BUS STAND", R.drawable.person_location)
        //  addMarkerWithIconAndTitle(LatLng(37.7897, -122.4145), "Marker 2", R.drawable.person_location)
        // Add more markers...

        // Center the map on the first marker
        googleMap!!.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(11.2590, 75.7863)))

    }

    private fun addMarkerWithIconAndTitle(position: LatLng, title: String, iconResId: Int) {
        val options = MarkerOptions()
            .position(position)
            .title(title)

        // Inflate the custom marker layout
        val markerView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_marker_layout, null)

        // Set the icon
        val markerIconImageView: ImageView = markerView.findViewById(R.id.marker_icon)
        markerIconImageView.setImageResource(iconResId)

        // Set the title
        val markerTitleTextView: TextView = markerView.findViewById(R.id.marker_title)
        markerTitleTextView.setText(title)

        // Convert the custom marker view to a bitmap
        val markerIcon = createBitmapDescriptorFromView(markerView)

        // Set the custom marker icon
        options.icon(markerIcon)

        // Add the marker to the map
        googleMap!!.addMarker(options)
    }

    private fun createBitmapDescriptorFromView(view: View): BitmapDescriptor {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val width: Int = view.getMeasuredWidth()
        val height: Int = view.getMeasuredHeight()
        view.layout(0, 0, width, height)
        val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

}