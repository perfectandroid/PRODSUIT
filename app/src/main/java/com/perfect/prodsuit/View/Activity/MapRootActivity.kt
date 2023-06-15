package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.EmployeeWiseLocationListViewModel
import org.json.JSONArray
import org.json.JSONObject


class MapRootActivity : AppCompatActivity() , OnMapReadyCallback {

    var TAG = "MapRootActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    private lateinit var googleMap: GoogleMap
    private var mapView: MapView? = null
    lateinit var locationList : JSONArray
    var arraylist = ArrayList<String>()
    private val coordinatesList = ArrayList<LatLng>()

    private var FK_Employee:String?=""
    private var strDate:String? = ""

    var EmployeeLocation = 0
    lateinit var employeeWiseLocationListViewModel: EmployeeWiseLocationListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_map_root)
        context = this@MapRootActivity
        employeeWiseLocationListViewModel = ViewModelProvider(this).get(EmployeeWiseLocationListViewModel::class.java)
        mapView = findViewById(R.id.map_view);
        mapView!!.onCreate(savedInstanceState);

        if (getIntent().hasExtra("FK_Employee")) {
            FK_Employee = intent.getStringExtra("FK_Employee")
        }
        if (getIntent().hasExtra("strDate")) {
            strDate = intent.getStringExtra("strDate")
        }

//        var location = Config.createLocation()
//        val jObject = JSONObject(location)
//        val jobjt = jObject.getJSONObject("LocationType")
//        locationList  = jobjt.getJSONArray("LocationDetails")
//        if (locationList.length()>0){
//            mapView!!.getMapAsync(this);
//
//
//        }

        if (!FK_Employee.equals("") && !strDate.equals("")){
            Log.e(TAG,"620   "+FK_Employee+" : "+strDate)
            EmployeeLocation = 0
            getEmployeeWiseList()
        }




    }

    private fun getEmployeeWiseList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeWiseLocationListViewModel.getEmployeeWiseLocationList(this, FK_Employee!!,strDate!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (EmployeeLocation == 0){
                                    EmployeeLocation++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeWiseLocationList")
                                        locationList = jobjt.getJSONArray("EmployeeWiseLocationListData")
                                        if (locationList.length()>0){
                                            mapView!!.getMapAsync(this);
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MapRootActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e:Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+ Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap!!.uiSettings.isCompassEnabled = false
        googleMap!!.uiSettings.isMapToolbarEnabled = false

        var j = 0

        for (i in 0 until locationList.length()) {
            val json = locationList.getJSONObject(i)
            j = i+1
            addMarkerWithIconAndTitle(LatLng(json.getString("LocLattitude").toDouble(), json.getString("LocLongitude").toDouble()),""+j+". "+ json.getString("LocLocationName"), R.drawable.person_location)
            if (i==0){
                googleMap!!.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
                googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(json.getString("LocLattitude").toDouble(), json.getString("LocLongitude").toDouble())))
            }

            coordinatesList.add(LatLng(json.getString("LocLattitude").toDouble(), json.getString("LocLongitude").toDouble()))


//            HardCoded
//            addMarkerWithIconAndTitle(LatLng(json.getString("LocLattitude").toDouble(), json.getString("LocLongitude").toDouble()),""+j+". "+ json.getString("LocLocationName"), R.drawable.person_location)
//            if (i==0){
//                googleMap!!.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
//                googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(json.getString("LocLattitude").toDouble(), json.getString("LocLongitude").toDouble())))
//            }
//
//            coordinatesList.add(LatLng(json.getString("LocLattitude").toDouble(), json.getString("LocLongitude").toDouble()))

        }

        val polylineOptions = PolylineOptions()
            .addAll(coordinatesList)
            .width(5f)
            .color(Color.RED)

        googleMap.addPolyline(polylineOptions)

        // Adjust camera to fit the polyline
//        val bounds = LatLngBounds.Builder().apply {
//            for (coordinate in coordinatesList) {
//                include(coordinate)
//            }
//        }.build()
//        val padding = 100 // Adjust padding as needed
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))


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

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
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


}