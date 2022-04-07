package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
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
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.CustomerSearchViewModel
import org.json.JSONObject
import java.util.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.View.Adapter.CustomerAdapter
import org.json.JSONArray

class LeadGeneratnActivity : AppCompatActivity()  , View.OnClickListener, OnMapReadyCallback,
    LocationListener,GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, ItemClickListener {

    val TAG : String = "LeadGeneratnActivity"
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null


    var ll_menu_date: LinearLayout? = null
    var ll_content_date: RelativeLayout? = null
    var ll_menu_location: LinearLayout? = null
    var ll_content_location: LinearLayout? = null
    var ll_menu_customer: LinearLayout? = null
    var ll_content_customer: LinearLayout? = null


    /////////////////
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 2
    private var mLocationPermissionGranted = false
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 8088
    private lateinit var googleMap : GoogleMap

    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null
    var imgClear: ImageView? = null
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


    /////////////////

    lateinit var context: Context
    lateinit var customersearchViewModel: CustomerSearchViewModel
    lateinit var customerArrayList : JSONArray
    var edt_customer: EditText? = null
    var img_search: ImageView? = null
    var btnCustReset: Button? = null
    var btnCustSubmit: Button? = null
    var llCustomerSave: LinearLayout? = null

    var txtCustName: TextView? = null
    var txtCustMobile: TextView? = null
    var txtCustEmail: TextView? = null
    var txtCustAddress: TextView? = null

    var edt_name: EditText? = null
    var edt_phone: EditText? = null
    var edt_email: EditText? = null
    var edt_address: EditText? = null
    private var dialogCustSearch : Dialog? = null

    companion object {
        var strCustomer = ""
        var strName = ""
        var strEmail = ""
        var strPhone = ""
        var strAddress = ""
        var ID_Customer = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_lead_generatn)
        context = this@LeadGeneratnActivity

        setRegViews()
        bottombarnav()
        getLocationPermission()
        checkAndRequestPermissions()

        customersearchViewModel = ViewModelProvider(this).get(CustomerSearchViewModel::class.java)

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)

        ll_menu_date = findViewById<LinearLayout>(R.id.ll_menu_date)
        ll_content_date = findViewById<RelativeLayout>(R.id.ll_content_date)
        ll_menu_location = findViewById<LinearLayout>(R.id.ll_menu_location)
        ll_content_location = findViewById<LinearLayout>(R.id.ll_content_location)
        ll_menu_customer = findViewById<LinearLayout>(R.id.ll_menu_customer)
        ll_content_customer = findViewById<LinearLayout>(R.id.ll_content_customer)

        edtSearch = findViewById<EditText>(R.id.edtSearch)
        edtSearch = findViewById<EditText>(R.id.edtSearch)

        txtCustName = findViewById(R.id.txtCustName) as TextView
        txtCustMobile = findViewById(R.id.txtCustMobile) as TextView
        txtCustEmail = findViewById(R.id.txtCustEmail) as TextView
        txtCustAddress = findViewById(R.id.txtCustAddress) as TextView
        edt_name = findViewById<EditText>(R.id.edt_name)
        edt_email = findViewById<EditText>(R.id.edt_email)
        edt_phone = findViewById<EditText>(R.id.edt_phone)
        edt_address = findViewById<EditText>(R.id.edt_address)
        btnCustReset = findViewById(R.id.btnCustReset) as Button
        btnCustSubmit = findViewById(R.id.btnCustSubmit) as Button
        llCustomerSave = findViewById(R.id.llCustomerSave) as LinearLayout

        txtSearch = findViewById(R.id.txtSearch) as TextView
        txtSubmit = findViewById(R.id.txtSubmit) as TextView

        edt_customer = findViewById<EditText>(R.id.edt_customer)
        img_search = findViewById<ImageView>(R.id.img_search)
        imgClear = findViewById<ImageView>(R.id.imgClear)

        imback!!.setOnClickListener(this)
        imgClear!!.setOnClickListener(this)

        ll_menu_date!!.setOnClickListener(this)
        ll_menu_location!!.setOnClickListener(this)
        ll_menu_customer!!.setOnClickListener(this)

        txtSearch!!.setOnClickListener(this)
        txtSubmit!!.setOnClickListener(this)
        btnCustSubmit!!.setOnClickListener(this)
        btnCustReset!!.setOnClickListener(this)

        img_search!!.setOnClickListener(this)

    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@LeadGeneratnActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@LeadGeneratnActivity, ProfileActivity::class.java)
                        startActivity(i)
                    }
                    R.id.logout -> {
                        doLogout()
                    }
                    R.id.quit -> {
                        quit()
                    }
                }
            }
        })
    }

    private fun doLogout() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.logout_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
            val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                dologoutchanges()
                startActivity(Intent(this@LeadGeneratnActivity, WelcomeActivity::class.java))
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dologoutchanges() {
        val loginSP = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val loginEditer = loginSP.edit()
        loginEditer.putString("loginsession", "No")
        loginEditer.commit()
        val loginmobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        val loginmobileEditer = loginmobileSP.edit()
        loginmobileEditer.putString("Loginmobilenumber", "")
        loginmobileEditer.commit()
    }

    private fun quit() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.quit_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btn_Yes) as Button
            val btn_No = dialog1 .findViewById(R.id.btn_No) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                finish()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity()
                }

            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.ll_menu_date->{
                ll_content_date!!.visibility = View.VISIBLE
                ll_content_location!!.visibility = View.GONE
                ll_content_customer!!.visibility = View.GONE

                ll_menu_date!!.setBackground(context.getDrawable(R.drawable.rectangle))
                ll_menu_location!!.setBackground(context.getDrawable(R.drawable.rectangles))
                ll_menu_customer!!.setBackground(context.getDrawable(R.drawable.rectangles))
            }
            R.id.ll_menu_location->{

                ll_content_date!!.visibility = View.GONE
                ll_content_location!!.visibility = View.VISIBLE
                ll_content_customer!!.visibility = View.GONE

                ll_menu_date!!.setBackground(context.getDrawable(R.drawable.rectangles))
                ll_menu_location!!.setBackground(context.getDrawable(R.drawable.rectangle))
                ll_menu_customer!!.setBackground(context.getDrawable(R.drawable.rectangles))

                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)



            }
            R.id.ll_menu_customer->{

                ll_content_date!!.visibility = View.GONE
                ll_content_location!!.visibility = View.GONE
                ll_content_customer!!.visibility = View.VISIBLE

                ll_menu_date!!.setBackground(context.getDrawable(R.drawable.rectangles))
                ll_menu_location!!.setBackground(context.getDrawable(R.drawable.rectangles))
                ll_menu_customer!!.setBackground(context.getDrawable(R.drawable.rectangle))



            }

            R.id.txtSearch->{
                try {
                    val strNames = edtSearch!!.text.toString()
                    geocoder = Geocoder(this, Locale.getDefault())
                    addresses = geocoder!!.getFromLocationName(strNames,1)

                    if (addresses != null && !addresses!!.equals("")){
                        googleMap.clear()
                        //   val address = addresses!![0]
                        address = addresses!!.get(0).getAddressLine(0)
                        city = addresses!!.get(0).locality
                        state = addresses!!.get(0).adminArea
                        country = addresses!!.get(0).countryName
                        //postalCode = addresses!!.get(0).postalCode
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
            }
            R.id.txtSubmit->{

            }

            R.id.imgClear->{
                edtSearch!!.setText("")
            }

            R.id.img_search->{
                try {
                    Log.e("TAG","img_search  64   ")
                    strCustomer = edt_customer!!.text.toString()
                    Log.e("TAG","strCustomer  64   "+strCustomer)
                    if (strCustomer.equals("")){
                        val snackbar: Snackbar = Snackbar.make(v, "Enter Customer", Snackbar.LENGTH_LONG)
                        snackbar.setActionTextColor(Color.WHITE)
                        snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                        snackbar.show()

                    }else{
                        getCustomerSearch()
                    }
                }catch (e  :Exception){
                    Log.e("TAG","Exception  64   "+e.toString())
                }
            }
            R.id.btnCustSubmit->{

                validations(v)
            }
            R.id.btnCustReset->{

                resetCustomer()
            }

        }
    }

    private fun resetCustomer() {
        edt_customer!!.setText("")
        strCustomer = ""
        strName = ""
        strEmail = ""
        strPhone = ""
        strAddress = ""
        ID_Customer = ""

        llCustomerSave!!.visibility=View.GONE
        txtCustName!!.setText("")
        txtCustMobile!!.setText("")
        txtCustEmail!!.setText("")
        txtCustAddress!!.setText("")

        edt_name!!.setText("")
        edt_email!!.setText("")
        edt_phone!!.setText("")
        edt_address!!.setText("")

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
        googleMap.setOnMapClickListener(GoogleMap.OnMapClickListener { latLng ->
            googleMap.clear()
            if (addresses == null && address.equals("")){
                geocoder = Geocoder(this, Locale.getDefault())
                addresses = geocoder!!.getFromLocation(latLng.latitude, latLng.longitude, 1);
            }

            address = addresses!!.get(0).getAddressLine(0)
            city = addresses!!.get(0).locality
            state = addresses!!.get(0).adminArea
            country = addresses!!.get(0).countryName
         //   postalCode = addresses!!.get(0).postalCode
            knownName = addresses!!.get(0).featureName

            strLongitue = latLng.longitude.toString()
            strLatitude = latLng.latitude.toString()

            val latLng = LatLng(latLng.latitude, latLng.longitude)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title(address + "," + city + "," + state + "," + country + "," + postalCode)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            mCurrLocationMarker = googleMap.addMarker(markerOptions)
            edtSearch!!.setText(address + "," + city + "," + state + "," + country + "," + postalCode)

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

//        geocoder = Geocoder(this, Locale.getDefault())
//        addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1);
        googleMap.clear()
        if (addresses == null && address.equals("")){
            geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1);
        }

        address = addresses!!.get(0).getAddressLine(0)
        city = addresses!!.get(0).locality
        state = addresses!!.get(0).adminArea
        country = addresses!!.get(0).countryName
      //  postalCode = addresses!!.get(0).postalCode
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

    private fun getCustomerSearch() {
        llCustomerSave!!.visibility=View.GONE
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                customersearchViewModel.getCustomer(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("CustomerDetailsList")
                                customerArrayList = jobjt.getJSONArray("CustomerDetails")
                                if (customerArrayList.length()>0){
                                    Log.e(TAG,"msg   1052   "+msg)
                                    customerSearchPopup(customerArrayList)
                                }
                            }
                            else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGeneratnActivity,
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
                        else {
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

    private fun customerSearchPopup(customerArrayList: JSONArray) {
        try {

            dialogCustSearch = Dialog(this)
            dialogCustSearch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCustSearch!! .setContentView(R.layout.customersearch_popup)
            dialogCustSearch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recyCustomer = dialogCustSearch!! .findViewById(R.id.recyCustomer) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGeneratnActivity, 1)
            recyCustomer!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = CustomerAdapter(this@LeadGeneratnActivity, customerArrayList)
            recyCustomer!!.adapter = adapter
            adapter.setClickListener(this@LeadGeneratnActivity)

            dialogCustSearch!!.show()
            dialogCustSearch!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun validations(v: View) {
        llCustomerSave!!.visibility=View.GONE
        strName =  edt_name!!.text.toString()
        strEmail =  edt_email!!.text.toString()
        strPhone =  edt_phone!!.text.toString()
        strAddress =  edt_address!!.text.toString()

        if (strName.equals("")){
            val snackbar: Snackbar = Snackbar.make(v, "Enter Name", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else if (strPhone.equals("") || strPhone.length != 10){
            val snackbar: Snackbar = Snackbar.make(v, "Enter Valid Mobile", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else if (strEmail.equals("")){
            val snackbar: Snackbar = Snackbar.make(v, "Enter Valid Email", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else if (strAddress.equals("")){
            val snackbar: Snackbar = Snackbar.make(v, "Enter Address", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
            snackbar.show()
        }
        else{

            llCustomerSave!!.visibility=View.VISIBLE
            ID_Customer = ""
            edt_customer!!.setText("")

            txtCustName!!.setText(strName)
            txtCustMobile!!.setText(strPhone)
            txtCustEmail!!.setText(strEmail)
            txtCustAddress!!.setText(strAddress)

        }

    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("customer")){
            dialogCustSearch!!.dismiss()
            val jsonObject = customerArrayList.getJSONObject(position)
            val intent = Intent()
//            intent.putExtra("Customer_Mode", "1")
//            intent.putExtra("ID_Customer", jsonObject.getString("ID_Customer"))
//            intent.putExtra("Name", jsonObject.getString("Name"))
//            intent.putExtra("Address", jsonObject.getString("Address"))
//            intent.putExtra("Email", jsonObject.getString("Email"))
//            intent.putExtra("MobileNumber", jsonObject.getString("MobileNumber"))
//            setResult(CUSTOMER_SEARCH!!, intent)
//            finish()

            edt_customer!!.setText(jsonObject.getString("Name"))
            strCustomer = jsonObject.getString("Name")
            strName = jsonObject.getString("Name")
            strEmail = jsonObject.getString("Email")
            strPhone = jsonObject.getString("MobileNumber")
            strAddress = jsonObject.getString("Address")
            ID_Customer = jsonObject.getString("ID_Customer")

            llCustomerSave!!.visibility=View.VISIBLE
            txtCustName!!.setText(strName)
            txtCustMobile!!.setText(strPhone)
            txtCustEmail!!.setText(strEmail)
            txtCustAddress!!.setText(strAddress)


        }
    }
}