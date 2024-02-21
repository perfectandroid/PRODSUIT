package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R

class MapsAgendaActivity : FragmentActivity() , OnMapReadyCallback, View.OnClickListener{

    var Latitude      : String =""
    var Longitude     : String  =""
    var LocationName  : String =""
    var ID_LeadGenerate  : String =""
    var ID_LeadGenerateProduct  : String =""

    var tv_LocationName: TextView? = null
    var fabSiteVisit: FloatingActionButton? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_maps_agenda)

        setRegViews()

        Latitude      = intent.getStringExtra("Latitude")!!
        Longitude     = intent.getStringExtra("Longitude")!!
        LocationName  = intent.getStringExtra("LocationName")!!
        ID_LeadGenerate  = intent.getStringExtra("ID_LeadGenerate")!!
        ID_LeadGenerateProduct  = intent.getStringExtra("ID_LeadGenerateProduct")!!

//        tv_LocationName = findViewById(R.id.tv_LocationName);
//        if (!LocationName.equals("")){
//            tv_LocationName!!.visibility = View.VISIBLE
//            tv_LocationName!!.setText(LocationName)
//        }


        val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.myMap) as
                SupportMapFragment?)!!
        supportMapFragment.getMapAsync(this@MapsAgendaActivity)

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        val imBack = findViewById<ImageView>(R.id.imBack)
        val imAddRemark = findViewById<ImageView>(R.id.imAddRemark)
        val fabSiteVisit = findViewById<FloatingActionButton>(R.id.fabSiteVisit)
        imBack!!.setOnClickListener(this)
        imAddRemark!!.setOnClickListener(this)
        fabSiteVisit!!.setOnClickListener(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(Latitude!!.toDouble(), Longitude!!.toDouble())
        val markerOptions = MarkerOptions().position(latLng).title(LocationName)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap?.addMarker(markerOptions)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        googleMap!!.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    override fun onClick(v: View) {

        when (v.id){

            R.id.fabSiteVisit->{
//                val i = Intent(this@MapsAgendaActivity, SiteVisitActivity::class.java)
//                i.putExtra("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
//                i.putExtra("ID_LeadGenerate", ID_LeadGenerate)
//                i.putExtra("ActionMode","2")
//                startActivity(i)
                val i = Intent(this@MapsAgendaActivity, FollowUpActivity::class.java)
                i.putExtra("ID_LeadGenerateProduct", ID_LeadGenerateProduct)
                i.putExtra("ID_LeadGenerate", ID_LeadGenerate)
                i.putExtra("ActionMode","2")
                startActivity(i)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

}