package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent.getIntent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication_demmo.Distance
import com.example.myapplication_demmo.MapData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.internal.it
import com.google.gson.Gson
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.EmployeeWiseLocationListViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat

class RootViewActivity : AppCompatActivity(), OnMapReadyCallback {
    private var FK_Employee: String? = ""
    private var item_response: String? = ""
    private var strDate: String? = ""
    var TAG = "RootViewActivity"

    //    val apiKey = "AIzaSyBpV-dAr8kv728_st35n8XMmt9L3qrqwhc"
    lateinit var context: Context
    lateinit var employeeWiseLocationListViewModel: EmployeeWiseLocationListViewModel
    private var progressDialog: ProgressDialog? = null
    var EmployeeLocation = 0
    private lateinit var mMap: GoogleMap
    private var originLatitude: Double = 0.0
    private var originLongitude: Double = 0.0
    private var destinationLatitude: Double = 0.0
    private var destinationLongitude: Double = 0.0
    lateinit var locationList: JSONArray
    lateinit var txt_distance: TextView
    lateinit var lin_distance: LinearLayout
//    var LocLattitudeStart = ""
//    var LocLongitudeStart = ""
//    var LocLattitudeEnd = ""
//    var LocLongitudeEnd = ""
    var StartingPoint = ""
    var EndingPoint = ""
    var DistanceTravelled = 0.0
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_root_view)
        context = this@RootViewActivity
        txt_distance = findViewById(R.id.txt_distance)
        lin_distance = findViewById(R.id.lin_distance)
        item_response = intent.getStringExtra("item_response")
        StartingPoint = intent.getStringExtra("StartingPoint").toString()
        EndingPoint = intent.getStringExtra("EndingPoint").toString()
        Log.v("sdfsdfsdfdsd","json "+item_response)
        Log.v("sdfsdfsdfdsd","StartingPoint "+StartingPoint)
        Log.v("sdfsdfsdfdsd","EndingPoint "+EndingPoint)
        val partsStarting = StartingPoint.split(",")
        originLatitude=partsStarting[0].toDouble()
        originLongitude=partsStarting[1].toDouble()

        val partsEnding = EndingPoint.split(",")
        destinationLatitude=partsEnding[0].toDouble()
        destinationLongitude=partsEnding[1].toDouble()

        loadTravelPath(item_response)

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun loadTravelPath(itemResponse: String?) {

        var mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.getMapAsync {
            mMap = it
            val originLocation = LatLng(originLatitude.toDouble(), originLongitude.toDouble())
            mMap.addMarker(MarkerOptions().position(originLocation))
            val destinationLocation = LatLng(destinationLatitude.toDouble(), destinationLongitude.toDouble())
            mMap.addMarker(MarkerOptions().position(destinationLocation))
//            val urll = getDirectionURL(originLocation, destinationLocation, apiKey)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 14F))
            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(itemResponse, MapData::class.java)
                Log.v("fdadasdsds", "size " + respObj.routes[0].legs.size)
                val path = ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs.size) {
                    for (j in 0 until respObj.routes[0].legs[i].steps.size) {

                        path.addAll(decodePolyline(respObj.routes[0].legs[i].steps[j].polyline.points))
                    }
                    var Dist = 0
                    Log.v("fdadasdsds", "i " + i)
                    Log.v("fdadasdsds", "valeu " + respObj.routes[0].legs[i].distance.value)
                    try {
                        var Dist = respObj.routes[0].legs[i].distance.value
                        DistanceTravelled = DistanceTravelled + Dist
                    } catch (e: Exception) {
                        Dist = 0
                        DistanceTravelled = DistanceTravelled + Dist
                    }
                }
//
//
                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.v("sdfsfsdfds", "e " + e)
            }
            Log.v("sfsdfdsddd", "onPostExecute")
            val lineoption = PolylineOptions()
            for (i in result.indices) {
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.RED)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
            try {
                var km = DistanceTravelled / 1000
                val pattern = "###,###.#"
                val decimalFormat = DecimalFormat(pattern)
                val formattedNumber = decimalFormat.format(km)
                txt_distance.setText("" + DistanceTravelled + " m / " + formattedNumber + " km")
            } catch (e: java.lang.Exception) {
                lin_distance.visibility = View.GONE
            }
        }
    }


//    private fun getEmployeeWiseList() {
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//                employeeWiseLocationListViewModel.getEmployeeWiseLocationList(
//                    this,
//                    FK_Employee!!,
//                    strDate!!
//                )!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//
//                        try {
//                            val msg = serviceSetterGetter.message
//                            if (msg!!.length > 0) {
//
//                                if (EmployeeLocation == 0) {
//                                    EmployeeLocation++
//
//                                    val jObject = JSONObject(msg)
//                                    Log.e(TAG, "msg   1224   " + msg)
//                                    if (jObject.getString("StatusCode") == "0") {
//                                        val jobjt =
//                                            jObject.getJSONObject("EmployeeWiseLocationList")
//                                        locationList =
//                                            jobjt.getJSONArray("EmployeeWiseLocationListData")
//                                        if (locationList.length() > 0) {
////                                            mapView!!.getMapAsync(this);
//                                            loadArrayForDirection(locationList)
//                                        }
//                                    } else {
//                                        val builder = AlertDialog.Builder(
//                                            this@RootViewActivity,
//                                            R.style.MyDialogTheme
//                                        )
//                                        builder.setMessage(jObject.getString("EXMessage"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()
//                                    }
//                                }
//
//                            } else {
////                                Toast.makeText(
////                                    applicationContext,
////                                    "Some Technical Issues.",
////                                    Toast.LENGTH_LONG
////                                ).show()
//                            }
//                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "" + Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
//    }

//    private fun loadArrayForDirection(locationList: JSONArray) {
//        Log.v("sfsdfdsddd", "locationList " + locationList.toString())
//        val locationListNonRepeat = JSONArray()
//        var LocLocationName1 = ""
//        for (i in 0 until locationList.length()) {
//            val jsonObject = locationList.getJSONObject(i)
//            val LocLocationName = jsonObject.getString("LocLocationName")
//            val LocLattitude = jsonObject.getString("LocLattitude")
//            val LocLongitude = jsonObject.getString("LocLongitude")
//            val EnteredTime = jsonObject.getString("EnteredTime")
//            Log.v("sfsdfdsddd", "LocLocationName " + LocLocationName)
//            Log.v("sfsdfdsddd", "LocLocationName1 " + LocLocationName1)
//            Log.v("sfsdfdsddd", "EnteredTime " + EnteredTime)
//            if (LocLocationName1 == LocLocationName) {
//                Log.v("sfsdfdsddd", "in ")
//            } else {
//                Log.v("sfsdfdsddd", "else ")
//                LocLocationName1 = LocLocationName
//                val jsonObject1 = JSONObject()
//                jsonObject1.put("LocLocationName", LocLocationName)
//                jsonObject1.put("LocLattitude", LocLattitude)
//                jsonObject1.put("LocLongitude", LocLongitude)
//                jsonObject1.put("EnteredTime", EnteredTime)
//                locationListNonRepeat.put(jsonObject1)
//            }
//        }
//        Log.v("sfsdfdsddd", "locationListNonRepeat " + locationListNonRepeat.toString())
//        loadPath(locationListNonRepeat)
//    }

//    private fun loadPath(locationListNonRepeat: JSONArray) {
//        try {
//
//            var selectedItems = JSONArray()
//            val numberOfItemsToFetch = 10
//            if (locationListNonRepeat.length() > numberOfItemsToFetch) {
//                val interval = locationListNonRepeat.length() / numberOfItemsToFetch
//                for (i in 0 until numberOfItemsToFetch) {
//                    val index = i * interval
//                    val jsonObject = locationListNonRepeat.getJSONObject(index)
//                    val LocLocationName = jsonObject.getString("LocLocationName")
//                    val LocLattitude = jsonObject.getString("LocLattitude")
//                    val LocLongitude = jsonObject.getString("LocLongitude")
//                    val EnteredTime = jsonObject.getString("EnteredTime")
//                    val jsonObject1 = JSONObject()
//                    jsonObject1.put("LocLocationName", LocLocationName)
//                    jsonObject1.put("LocLattitude", LocLattitude)
//                    jsonObject1.put("LocLongitude", LocLongitude)
//                    jsonObject1.put("EnteredTime", EnteredTime)
//                    selectedItems.put(jsonObject1)
//                }
//            } else {
//                selectedItems = locationListNonRepeat
//            }
//            Log.v("sfsdfdsddd", "selectedItems " + selectedItems.toString())
//            var finalLocation = ""
//            for (i in 0 until selectedItems.length()) {
//                val jsonObject = selectedItems.getJSONObject(i)
//                val LocLattitude = jsonObject.getString("LocLattitude")
//                val LocLongitude = jsonObject.getString("LocLongitude")
//                var location = ""
//                if (i == selectedItems.length() - 1) {
//                    location = LocLattitude + "," + LocLongitude
//                    Log.v("sdfsdfsdddddd", "location1 " + location)
//                } else {
//                    location = LocLattitude + "," + LocLongitude + "|"
//                    Log.v("sdfsdfsdddddd", "location2 " + location)
//                }
//                finalLocation = finalLocation + location
//            }
//
//            val jsonObjectStart = selectedItems.getJSONObject(0)
//            LocLattitudeStart = jsonObjectStart.getString("LocLattitude")
//            LocLongitudeStart = jsonObjectStart.getString("LocLongitude")
//            var locationStart = LocLattitudeStart + "," + LocLongitudeStart
//            val jsonObjectEnd = selectedItems.getJSONObject(selectedItems.length() - 1)
//            LocLattitudeEnd = jsonObjectEnd.getString("LocLattitude")
//            LocLongitudeEnd = jsonObjectEnd.getString("LocLongitude")
//            var locationEnd = LocLattitudeEnd + "," + LocLongitudeEnd
//
//            Log.v("sdfsdfsdddddd", "finalLocation " + finalLocation)
//
//            var url =
//                "https://maps.googleapis.com/maps/api/directions/json?origin=" + locationStart + "&destination=" + locationEnd + "&waypoints=" + finalLocation + "&key=" + apiKey
//            Log.v("sdfsdfsdddddd", "url " + url)
//
//
//            var mapFragment =
//                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//            mapFragment.getMapAsync(this)
//            mapFragment.getMapAsync {
//                Log.v("sfsdfdsddd", "getMapAsync")
//                Log.v("sdfsdfdeee", "LocLattitudeStart " + LocLattitudeStart)
//                Log.v("sdfsdfdeee", "LocLongitudeStart " + LocLongitudeStart)
//                mMap = it
//                val originLocation =
//                    LatLng(LocLattitudeStart.toDouble(), LocLongitudeStart.toDouble())
//                mMap.addMarker(MarkerOptions().position(originLocation))
//                val destinationLocation =
//                    LatLng(LocLattitudeEnd.toDouble(), LocLattitudeEnd.toDouble())
//                mMap.addMarker(MarkerOptions().position(destinationLocation))
////            val urll = getDirectionURL(originLocation, destinationLocation, apiKey)
//                GetDirection(url).execute()
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 14F))
//            }
//        } catch (E: Exception) {
//
//        }
//    }


//    @SuppressLint("StaticFieldLeak")
//    private inner class GetDirection(val url: String) :
//        AsyncTask<Void, Void, List<List<LatLng>>>() {
//        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
//            Log.v("sfsdfdsddd", "GetDirection")
//            val client = OkHttpClient()
//            val request = Request.Builder().url(url).build()
//            val response = client.newCall(request).execute()
//            val data = response.body().string()
//            val result = ArrayList<List<LatLng>>()
//            try {
//                val respObj = Gson().fromJson(data, MapData::class.java)
//                Log.v("fdadasdsds", "size " + respObj.routes[0].legs.size)
//                val path = ArrayList<LatLng>()
//                for (i in 0 until respObj.routes[0].legs.size) {
//                    for (j in 0 until respObj.routes[0].legs[i].steps.size) {
//
//                        path.addAll(decodePolyline(respObj.routes[0].legs[i].steps[j].polyline.points))
//                    }
//                    var Dist = 0
//                    Log.v("fdadasdsds", "i " + i)
//                    Log.v("fdadasdsds", "valeu " + respObj.routes[0].legs[i].distance.value)
//                    try {
//                        var Dist = respObj.routes[0].legs[i].distance.value
//                        DistanceTravelled = DistanceTravelled + Dist
//                    } catch (e: Exception) {
//                        Dist = 0
//                        DistanceTravelled = DistanceTravelled + Dist
//                    }
//
//                }
////
////
//                result.add(path)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.v("sdfsfsdfds", "e " + e)
//            }
//            return result
//        }
//
//        override fun onPostExecute(result: List<List<LatLng>>) {
//            Log.v("sfsdfdsddd", "onPostExecute")
//            val lineoption = PolylineOptions()
//            for (i in result.indices) {
//                lineoption.addAll(result[i])
//                lineoption.width(10f)
//                lineoption.color(Color.GREEN)
//                lineoption.geodesic(true)
//            }
//            mMap.addPolyline(lineoption)
//            try {
//                var km = DistanceTravelled / 1000
//                val pattern = "###,###.#"
//                val decimalFormat = DecimalFormat(pattern)
//                val formattedNumber = decimalFormat.format(km)
//                txt_distance.setText("" + DistanceTravelled + " m / " + formattedNumber + " km")
//            } catch (e: java.lang.Exception) {
//                lin_distance.visibility = View.GONE
//            }
//
//        }
//    }

    fun decodePolyline(encoded: String): List<LatLng> {
        Log.v("sfsdfdsddd", "decodePolyline")
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }

    override fun onMapReady(p0: GoogleMap) {
//        try {
//            Log.v("sdfsdfdsddd", "onMapReady")
//            mMap = p0!!
//            val originLocation = LatLng(LocLattitudeStart.toDouble(), LocLongitudeEnd.toDouble())
//            mMap.clear()
//            mMap.addMarker(MarkerOptions().position(originLocation))
//            mMap.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                    this, R.raw.mapstyle_retro
//                )
//            );
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 18F))
//        }
//        catch (e:Exception)
//        {
//
//        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}