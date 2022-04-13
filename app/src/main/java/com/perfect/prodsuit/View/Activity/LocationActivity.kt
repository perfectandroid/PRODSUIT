package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.LocationViewModel
import org.json.JSONObject

class LocationActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener{
    private var progressDialog: ProgressDialog? = null
    private var Id_leadgenrteprod: String? = null
    lateinit var context: Context
    lateinit var locationViewModel: LocationViewModel
    private lateinit var mMap: GoogleMap
    internal var mCurrLocationMarker: Marker? = null
    var latitude=""
    var longitude=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

         Id_leadgenrteprod = intent.getStringExtra("prodid")

        setRegViews()
        getLocationDetails()
    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
    }

    companion object {
        var strid= ""
    }

   private fun getLocationDetails() {
       strid = Id_leadgenrteprod!!
       Log.i("Id", strid)
        context = this@LocationActivity
       locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                locationViewModel.getLocation(this)!!.observe(
                    this,
                    Observer { locationSetterGetter ->
                        val msg = locationSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)

                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("LeadImageDetails")
                                latitude = jobjt!!.getString("LocationLatitude")
                                longitude = jobjt!!.getString("LocationLongitude")
                                Log.i("LocationDetails", latitude + "\n" + longitude)

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LocationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()

                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            val lati=intent.getStringExtra("lat")
            val longi=intent.getStringExtra("long")

            if(lati!!.isEmpty()&& longi!!.isEmpty()){
                val dialogBuilder = android.app.AlertDialog.Builder(
                    this@LocationActivity,
                    R.style.MyDialogTheme
                )
                dialogBuilder.setMessage("No data found.")
                    .setCancelable(false)
                    .setPositiveButton(
                        "OK",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                            finish()
                        })
                val alert = dialogBuilder.create()
                alert.show()
                val pbutton =
                    alert.getButton(DialogInterface.BUTTON_POSITIVE)
                pbutton.setTextColor(Color.RED)
            }else {

                mMap = googleMap
                val latLng = LatLng(latitude.toDouble(), longitude.toDouble())

                Log.i("LatLng", latLng.toString())
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.title("Current Location")

                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                mCurrLocationMarker = mMap!!.addMarker(markerOptions)

              //  mMap.addMarker(markerOptions)

                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}
