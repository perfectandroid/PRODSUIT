package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.perfect.prodsuit.R
import org.json.JSONArray
import java.io.*
import java.util.*
import com.perfect.prodsuit.Helper.Config
import org.json.JSONObject
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.View.Adapter.FollowupTypeAdapter
import com.perfect.prodsuit.View.Adapter.ProductStatusAdapter
import com.perfect.prodsuit.Viewmodel.FollowUpTypeViewModel
import com.perfect.prodsuit.Viewmodel.ProductStatusViewModel
import com.perfect.prodsuit.Viewmodel.SaveSiteVisitViewModel

class SiteVisitActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG : String = "SiteVisitActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var llFromdate: LinearLayout? = null
    var llFromDatePick: LinearLayout? = null
    var llToDate: LinearLayout? = null
    var llToDatePick: LinearLayout? = null
    var llMentionDate: LinearLayout? = null
    var llMentionDatePick: LinearLayout? = null
    var llRiskType: LinearLayout? = null
    var llFollowType: LinearLayout? = null
    var llStatus: LinearLayout? = null
    var txtFromDate: TextView? = null
    var txtFromSubmit: TextView? = null
    var txtToDate: TextView? = null
    var txtToSubmit: TextView? = null
    var txtMentionDate: TextView? = null
    var txtMentionSubmit: TextView? = null
    var txtLatitude: TextView? = null
    var txtLongitude: TextView? = null
    var txtRiskType: TextView? = null
    var txtFollowType: TextView? = null
    var txtStatus: TextView? = null
    var edtAgentNote: EditText? = null
    var edtCustNote: EditText? = null
    var imFromDate: ImageView? = null
    var imToDate: ImageView? = null
    var imMentionDate: ImageView? = null
    var datePickerFrom: DatePicker? = null
    var datePickerTo: TimePicker? = null
    var datePickerMention: DatePicker? = null
    var btnReset: Button? = null
    var btnSubmit: Button? = null
    var imgv_upload1: ImageView? = null
    var imgv_upload2: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 200
    private var strImage: String? = null
    private var destination: File? = null
    private var image1 = ""
    private var image2 = ""
    private val MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    var fromDateMode : String?= "1"  // GONE
    var toDateMode : String?= "1"  // GONE
    var MentionDateMode : String?= "1"  // GONE
    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var followUpTypeArrayList : JSONArray
    private var dialogFollowupType : Dialog? = null
    var recyFollowupType: RecyclerView? = null
    lateinit var productStatusViewModel: ProductStatusViewModel
    lateinit var prodStatusArrayList : JSONArray
    private var dialogProdStatus : Dialog? = null
    var recyProdStatus: RecyclerView? = null
    lateinit var saveSiteVisitViewModel: SaveSiteVisitViewModel
    lateinit var saveSiteVisitArrayList : JSONArray

    companion object {
        var ID_LeadGenerateProduct :String = ""
        var strDate : String?= ""
        var strTime : String?= ""
        var strDateTime : String?= ""
        var strRiskType : String?= ""
        var strAgentNote : String?= ""
        var strCustomerNote : String?= ""
        var ID_ActionType : String?= ""
        var ID_Status : String?= ""
        var strMentionDate : String?= ""
        var strLatitude : String?= ""
        var strLongitude : String?= ""
        var encode1 : String?= ""
        var encode2 : String?= ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_site_visit)
        context = this@SiteVisitActivity
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        productStatusViewModel = ViewModelProvider(this).get(ProductStatusViewModel::class.java)
        saveSiteVisitViewModel = ViewModelProvider(this).get(SaveSiteVisitViewModel::class.java)
        setRegViews()
        removeData()
        ID_LeadGenerateProduct = intent!!.getStringExtra("ID_LeadGenerateProduct").toString()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        txtFromDate = findViewById(R.id.txtFromDate) as TextView
        txtFromSubmit = findViewById(R.id.txtFromSubmit) as TextView
        txtToDate = findViewById(R.id.txtToDate) as TextView
        txtToSubmit = findViewById(R.id.txtToSubmit) as TextView
        txtMentionDate = findViewById(R.id.txtMentionDate) as TextView
        txtMentionSubmit = findViewById(R.id.txtMentionSubmit) as TextView
        txtLatitude = findViewById(R.id.txtLatitude) as TextView
        txtLongitude = findViewById(R.id.txtLongitude) as TextView
        txtRiskType = findViewById(R.id.txtRiskType) as TextView
        txtFollowType = findViewById(R.id.txtFollowType) as TextView
        txtStatus = findViewById(R.id.txtStatus) as TextView
        edtAgentNote = findViewById(R.id.edtAgentNote) as EditText
        edtCustNote = findViewById(R.id.edtCustNote) as EditText
        imgv_upload1 = findViewById(R.id.imgv_upload1) as ImageView
        imgv_upload2 = findViewById(R.id.imgv_upload2) as ImageView
        imFromDate = findViewById(R.id.imFromDate) as ImageView
        imToDate = findViewById(R.id.imToDate) as ImageView
        imMentionDate = findViewById(R.id.imMentionDate) as ImageView
        llFromdate = findViewById(R.id.llFromdate) as LinearLayout
        llFromDatePick = findViewById(R.id.llFromDatePick) as LinearLayout
        llToDate = findViewById(R.id.llToDate) as LinearLayout
        llToDatePick = findViewById(R.id.llToDatePick) as LinearLayout
        llMentionDate = findViewById(R.id.llMentionDate) as LinearLayout
        llMentionDatePick = findViewById(R.id.llMentionDatePick) as LinearLayout
        llRiskType = findViewById(R.id.llRiskType) as LinearLayout
        llFollowType = findViewById(R.id.llFollowType) as LinearLayout
        llStatus = findViewById(R.id.llStatus) as LinearLayout
        datePickerFrom = findViewById(R.id.datePickerFrom) as DatePicker
        datePickerTo = findViewById(R.id.datePickerTo) as TimePicker
        datePickerMention = findViewById(R.id.datePickerMention) as DatePicker
        btnReset = findViewById(R.id.btnReset) as Button
        btnSubmit = findViewById(R.id.btnSubmit) as Button
        llFromdate!!.setOnClickListener(this)
        llToDate!!.setOnClickListener(this)
        llMentionDate!!.setOnClickListener(this)
        llRiskType!!.setOnClickListener(this)
        llFollowType!!.setOnClickListener(this)
        llStatus!!.setOnClickListener(this)
        txtLatitude!!.setOnClickListener(this)
        txtLongitude!!.setOnClickListener(this)
        txtFromSubmit!!.setOnClickListener(this)
        txtToSubmit!!.setOnClickListener(this)
        txtMentionSubmit!!.setOnClickListener(this)
        imgv_upload1!!.setOnClickListener(this)
        imgv_upload2!!.setOnClickListener(this)
        imFromDate!!.setOnClickListener(this)
        imToDate!!.setOnClickListener(this)
        imMentionDate!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        datePickerFrom!!.minDate = Calendar.getInstance().timeInMillis
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        txtLongitude!!.text = location.longitude.toString()
                        txtLatitude!!.text = location.latitude.toString()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            txtLongitude!!.text = mLastLocation.longitude.toString()
            txtLatitude!!.text = mLastLocation.latitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    override fun onClick(v: View) {
       when(v.id){

           R.id.imback->{
               finish()
           }
           R.id.txtLatitude->{
               getLastLocation()
           }
           R.id.txtLongitude->{
               getLastLocation()
           }
           R.id.imgv_upload1->{
               try
               {
                   Config.Utils.hideSoftKeyBoard(this@SiteVisitActivity,v)
                   strImage="1"
                   showPictureDialog()
               }
               catch(e:java.lang.Exception)
               {
                   if (checkCamera()){} else{
                       requestPermission()
                   }
               }
           }
           R.id.imgv_upload2->{
               try {
                   Config.Utils.hideSoftKeyBoard(this@SiteVisitActivity,v)
                   strImage="2"
                   showPictureDialog()
               }
               catch(e:java.lang.Exception)
               {
                   if (checkCamera()){} else{
                       requestPermission()
                   }
               }
           }

           R.id.llFromdate->{
               if (fromDateMode.equals("0")){
                   llFromDatePick!!.visibility = View.GONE
                   fromDateMode = "1"
               }else{
                   llFromDatePick!!.visibility = View.VISIBLE
                   fromDateMode = "0"
               }
               llToDatePick!!.visibility = View.GONE
               toDateMode = "1"
           }
           R.id.llToDate->{
               if (toDateMode.equals("0")){
                   llToDatePick!!.visibility = View.GONE
                   toDateMode = "1"
               }else{
                   llToDatePick!!.visibility = View.VISIBLE
                   toDateMode = "0"
               }
               llFromDatePick!!.visibility = View.GONE
               fromDateMode = "1"
           }
           R.id.llMentionDate->{
               if (MentionDateMode.equals("0")){
                   llMentionDatePick!!.visibility = View.GONE
                   MentionDateMode = "1"
               }else{
                   llMentionDatePick!!.visibility = View.VISIBLE
                   MentionDateMode = "0"
               }
           }
           R.id.imFromDate->{
               llFromDatePick!!.visibility = View.GONE
               fromDateMode = "1"
           }
           R.id.imToDate->{
               llToDatePick!!.visibility = View.GONE
               toDateMode = "1"
           }
           R.id.imMentionDate->{
               llMentionDatePick!!.visibility = View.GONE
               MentionDateMode = "1"
           }
           R.id.txtFromSubmit->{
               try {
                   datePickerFrom!!.minDate = Calendar.getInstance().timeInMillis
                   val day: Int = datePickerFrom!!.getDayOfMonth()
                   val mon: Int = datePickerFrom!!.getMonth()
                   val month: Int = mon+1
                   val year: Int = datePickerFrom!!.getYear()
                   var strDay = day.toString()
                   var strMonth = month.toString()
                   var strYear = year.toString()
                   if (strDay.length == 1){
                       strDay ="0"+day
                   }
                   if (strMonth.length == 1){
                       strMonth ="0"+strMonth
                   }
                   txtFromDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                   llFromDatePick!!.visibility=View.GONE
                   fromDateMode = "1"
                   strDate = strDay+"-"+strMonth+"-"+strYear
               }
               catch (e: Exception){
                   Log.e(TAG,"Exception   428   "+e.toString())
               }
           }
           R.id.txtToSubmit->{
               try {
                   var am_pm = ""
                   var hour: Int = datePickerTo!!.hour
                   var min: Int = datePickerTo!!.minute
                   when {hour == 0 -> { hour += 12
                       am_pm = "AM"
                   }
                       hour == 12 -> am_pm = "PM"
                       hour > 12 -> { hour -= 12
                           am_pm = "PM"
                       }
                       else -> am_pm = "AM"
                   }
                   var hours : String = ""+hour
                   var minutes : String = ""+min
                   if (hour<10){
                       hours = "0"+hour
                   }
                   if (min<10){
                       minutes = "0"+min
                   }
                   txtToDate!!.setText(""+hours+":"+minutes+":"+"00")
                   llToDatePick!!.visibility=View.GONE
                   toDateMode = "1"
                   strTime = hours+":"+minutes+":"+"00"
               }
               catch (e: Exception){
                   Log.e(TAG,"Exception   428   "+e.toString())
               }
           }
           R.id.txtMentionSubmit->{
               try {
                   datePickerMention!!.minDate = Calendar.getInstance().timeInMillis
                   val day: Int = datePickerMention!!.getDayOfMonth()
                   val mon: Int = datePickerMention!!.getMonth()
                   val month: Int = mon+1
                   val year: Int = datePickerMention!!.getYear()
                   var strDay = day.toString()
                   var strMonth = month.toString()
                   var strYear = year.toString()
                   if (strDay.length == 1){
                       strDay ="0"+day
                   }
                   if (strMonth.length == 1){
                       strMonth ="0"+strMonth
                   }
                   txtMentionDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                   llMentionDatePick!!.visibility=View.GONE
                   MentionDateMode = "1"

                   strMentionDate = strDay+"-"+strMonth+"-"+strYear
               }
               catch (e: Exception){
                   Log.e(TAG,"Exception   428   "+e.toString())
               }
           }
           R.id.llRiskType->{
               getRiskType()
           }
           R.id.llFollowType->{
               getFollowupType()
           }
           R.id.llStatus->{
               getProductStatus()
           }
           R.id.btnReset->{
               removeData()
           }
           R.id.btnSubmit->{
               Config.Utils.hideSoftKeyBoard(context,v)
               Validations(v)
           }

       }
    }

    private fun getRiskType() {
        try {
            val builder = android.app.AlertDialog.Builder(this)
            val inflater1 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.riskstatus_popup, null)
            val lvRiskType  = layout.findViewById<ListView>(R.id.lvRiskType)
            builder.setView(layout)
            val alertDialog = builder.create()
            val listItem = resources.getStringArray(R.array.risk_type)
            val adapter = ArrayAdapter(this, R.layout.spinner_item, android.R.id.text1, listItem
            )
            lvRiskType.setAdapter(adapter)
            lvRiskType.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, l ->
                // TODO Auto-generated method stub
                val value = adapter.getItem(position)
                txtRiskType!!.setText(value)
                if (position == 0) {
                    strRiskType = "1"
                }
                if (position == 1) {
                    strRiskType = "2"
                }
                if (position == 2) {
                    strRiskType = "3"
                }
                alertDialog.dismiss()
            })
            alertDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFollowupType() {
        var followUpType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpTypeViewModel.getFollowupType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                followUpTypeArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
                                if (followUpTypeArrayList.length()>0){
                                    if (followUpType == 0){
                                        followUpType++
                                        followupTypePopup(followUpTypeArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@SiteVisitActivity,
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

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {
        try {
            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!! .setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType = dialogFollowupType!! .findViewById(R.id.recyFollowupType) as RecyclerView
            val lLayout = GridLayoutManager(this@SiteVisitActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = FollowupTypeAdapter(this@SiteVisitActivity, followUpTypeArrayList)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@SiteVisitActivity)
            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductStatus() {
        var prodstatus = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productStatusViewModel.getProductStatus(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   333   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("StatusDetailsList")
                                prodStatusArrayList = jobjt.getJSONArray("StatusList")
                                if (prodStatusArrayList.length()>0){
                                    if (prodstatus == 0){
                                        prodstatus++
                                        productStatusPopup(prodStatusArrayList)
                                    }
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@SiteVisitActivity,
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

    private fun productStatusPopup(prodStatusArrayList: JSONArray) {
        try {
            dialogProdStatus = Dialog(this)
            dialogProdStatus!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdStatus!! .setContentView(R.layout.product_status_popup)
            dialogProdStatus!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdStatus = dialogProdStatus!! .findViewById(R.id.recyProdStatus) as RecyclerView
            val lLayout = GridLayoutManager(this@SiteVisitActivity, 1)
            recyProdStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProductStatusAdapter(this@SiteVisitActivity, prodStatusArrayList)
            recyProdStatus!!.adapter = adapter
            adapter.setClickListener(this@SiteVisitActivity)
            dialogProdStatus!!.show()
            dialogProdStatus!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select From")
        val pictureDialogItems = arrayOf("Gallery", "Camera")
        pictureDialog.setItems(pictureDialogItems   ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    private fun checkCamera(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;

    }

    private fun takePhotoFromCamera() {
        val photo =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(photo, CAMERA)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE)
    }

    fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        if (getContentResolver() != null) {
            val cursor = getContentResolver().query(uri, null, null, null, null)
            if (cursor != null) {
                cursor!!.moveToFirst()
                val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor!!.getString(idx)
                cursor!!.close()
            }
        }
        return path
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG","onActivityResult  256   "+requestCode+ "   "+resultCode+ "  "+data)
        if (requestCode == GALLERY ) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    var selectedImageUri: Uri = data.getData()!!
                    data.getData()
                    if(strImage.equals("1")) {
                        imgv_upload1!!.setImageURI(contentURI)
                        image1 = getRealPathFromURI(selectedImageUri)
                        if (image1 != null) {
                        }
                    }
                    if(strImage.equals("2")) {
                        imgv_upload2!!.setImageURI(contentURI)
                        image2 = getRealPathFromURI(selectedImageUri)
                        if (image2 != null) {
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@SiteVisitActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == CAMERA) {
            if (data != null) {
                try {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        ) {
                        } else {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                            )
                        }
                    }
                    else {
                        val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                        val bytes = ByteArrayOutputStream()
                        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                        destination = File(
                            (Environment.getExternalStorageDirectory()).toString() + "/" +
                                    getString(R.string.app_name),
                            "IMG_" + System.currentTimeMillis() + ".jpg"
                        )
                        val fo: FileOutputStream
                        try {
                            if (!destination!!.getParentFile().exists()) {
                                destination!!.getParentFile().mkdirs()
                            }
                            if (!destination!!.exists()) {
                                destination!!.createNewFile()
                            }
                            fo = FileOutputStream(destination)
                            fo.write(bytes.toByteArray())
                            fo.close()
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        if (strImage.equals("1")) {
                            image1 = destination!!.getAbsolutePath()
                            destination = File(image1)
                            val myBitmap = BitmapFactory.decodeFile(destination.toString())
                            if (imgv_upload1 != null) {
                                imgv_upload1!!.setImageBitmap(myBitmap)
                            }
                            imgv_upload1!!.setImageBitmap(myBitmap)
                            if (image1 != null) {
                            }
                        }
                        if (strImage.equals("2")) {
                            image2 = destination!!.getAbsolutePath()
                            destination = File(image2)
                            val myBitmap = BitmapFactory.decodeFile(destination.toString())
                            if (imgv_upload2 != null) {
                                imgv_upload2!!.setImageBitmap(myBitmap)
                            }
                            imgv_upload2!!.setImageBitmap(myBitmap)
                            if (image2 != null) {
                            }
                        }
                    }
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@SiteVisitActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("followuptype")){
            dialogFollowupType!!.dismiss()
            val jsonObject = followUpTypeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_ActionType   "+jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            txtFollowType!!.setText(jsonObject.getString("ActnTypeName"))
        }
        if (data.equals("prodstatus")){
            dialogProdStatus!!.dismiss()
            val jsonObject = prodStatusArrayList.getJSONObject(position)
            Log.e(TAG,"ID_Status   "+jsonObject.getString("ID_Status"))
            ID_Status = jsonObject.getString("ID_Status")
            txtStatus!!.setText(jsonObject.getString("StatusName"))
        }
    }

    private fun Validations(v : View) {
        strAgentNote = edtAgentNote!!.text.toString()
        strCustomerNote = edtCustNote!!.text.toString()
        strLatitude = txtLatitude!!.text.toString()
        strLongitude = txtLongitude!!.text.toString()
        if (strDate.equals("")){
            Config.snackBars(context,v,"Select Date")
        }
        else if (strTime.equals("")){
            Config.snackBars(context,v,"Select Time")
        }
        else if (strRiskType.equals("")){
            Config.snackBars(context,v,"Select Risk type")
        }
        else if (strAgentNote.equals("")){
            Config.snackBars(context,v,"Enter Agent Note")
        }
        else if (strCustomerNote.equals("")){
            Config.snackBars(context,v,"Enter Customer Note")
        }
        else if (ID_ActionType.equals("")){
            Config.snackBars(context,v,"Select Followup type")
        }
        else if (ID_Status.equals("")){
            Config.snackBars(context,v,"Select Status")
        }
        else if (strMentionDate.equals("")){
            Config.snackBars(context,v,"Select Mention Date")
        }
        else if (strLatitude.equals("")){
            Config.snackBars(context,v,"Select Latitude")
        }
        else if (strLongitude.equals("")){
            Config.snackBars(context,v,"Select Longitude")
        }else{
            strDateTime = strDate+" "+ strTime
            if(image1.equals(""))
            {
                encode1 = ""
            }
            else
            {
                val bitmap = BitmapFactory.decodeFile(image1)
                val stream =  ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    encode1 = Base64.getEncoder().encodeToString(stream.toByteArray());
                } else {
                    encode1 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                }
            }
            if(image2.equals(""))
            {
                encode2 = ""
            }
            else
            {
                val bitmap = BitmapFactory.decodeFile(image2)
                val stream =  ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    encode2 = Base64.getEncoder().encodeToString(stream.toByteArray())
                } else {
                    encode2 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                }
            }
            Log.e(TAG,"SITEVISIT  1003"
                    +"\n"+"ID_LeadGenerateProduct   :  "+ ID_LeadGenerateProduct
                    +"\n"+"strDate                  :  "+ strDate
                    +"\n"+"strTime                  :  "+ strTime
                    +"\n"+"strDateTime              :  "+ strDateTime
                    +"\n"+"strRiskType              :  "+ strRiskType
                    +"\n"+"strAgentNote             :  "+ strAgentNote
                    +"\n"+"strCustomerNote          :  "+ strCustomerNote
                    +"\n"+"Followup ID              :  "+ ID_ActionType
                    +"\n"+"ID_Status                :  "+ ID_Status
                    +"\n"+"strMentionDate           :  "+ strMentionDate
                    +"\n"+"strLatitude              :  "+ strLatitude
                    +"\n"+"strLongitude             :  "+ strLongitude
                    +"\n"+"encode1                  :  "+ encode1
                    +"\n"+"encode2                  :  "+ encode2)
            Log.e(TAG,"SITEVISIT  10031"
                    +"\n"+"encode1          :  "+ encode1)
            Log.e(TAG,"SITEVISIT  10032"
                    +"\n"+"encode2          :  "+ encode2)
            saveSiteVisit()
        }
    }

    private fun saveSiteVisit() {
        var saveSiteVisit = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                saveSiteVisitViewModel.saveSiteVisit(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   1058   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
//                                val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
//                                followUpTypeArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
//                                if (followUpTypeArrayList.length()>0){
//                                    if (saveSiteVisit == 0){
//                                        saveSiteVisit++
//                                        followupTypePopup(followUpTypeArrayList)
//                                    }
//
//                                }

                                val builder = AlertDialog.Builder(
                                    this@SiteVisitActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                    finish()
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@SiteVisitActivity,
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

    private fun removeData() {
        ID_LeadGenerateProduct = ""
        strDate = ""
        strTime = ""
        strDateTime = ""
        strRiskType = ""
        strAgentNote = ""
        strCustomerNote = ""
        ID_ActionType = ""
        ID_Status = ""
        strMentionDate = ""
        strLatitude = ""
        strLongitude = ""
        encode1 = ""
        encode2 = ""
        txtFromDate!!.setText("")
        txtToDate!!.setText("")
        txtRiskType!!.setText("")
        edtAgentNote!!.setText("")
        edtCustNote!!.setText("")
        txtFollowType!!.setText("")
        txtStatus!!.setText("")
        txtMentionDate!!.setText("")
        txtLatitude!!.setText("")
        txtLongitude!!.setText("")
        image1 = ""
        image2 = ""
        imgv_upload1!!.setImageDrawable(resources.getDrawable(R.drawable.uploadimg))
        imgv_upload2!!.setImageDrawable(resources.getDrawable(R.drawable.uploadimg))
    }

}