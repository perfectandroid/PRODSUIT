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
import android.text.Editable
import android.text.TextWatcher
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
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

    var tie_Date: TextInputEditText? = null
    var tie_Time: TextInputEditText? = null
    var tie_RiskType: TextInputEditText? = null
    var tie_AgentNote: TextInputEditText? = null
    var tie_CustomerNote: TextInputEditText? = null
    var tie_FollowType: TextInputEditText? = null
    var tie_Status: TextInputEditText? = null
    var tie_CustomerMentionDate: TextInputEditText? = null
    var tie_Longitude: TextInputEditText? = null
    var tie_Latitude: TextInputEditText? = null


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
    lateinit var followUpTypeSort : JSONArray
    private var dialogFollowupType : Dialog? = null
    var recyFollowupType: RecyclerView? = null
    lateinit var productStatusViewModel: ProductStatusViewModel
    lateinit var prodStatusArrayList : JSONArray
    lateinit var prodStatusSort : JSONArray
    private var dialogProdStatus : Dialog? = null
    var recyProdStatus: RecyclerView? = null
    lateinit var saveSiteVisitViewModel: SaveSiteVisitViewModel
    lateinit var saveSiteVisitArrayList : JSONArray


    internal var yr: Int =0
    internal var month:Int = 0
    internal var day:Int = 0
    internal var hr:Int = 0
    internal var min:Int = 0
    private var mYear: Int =0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0

    private var DateType:Int = 0

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

        tie_Date = findViewById<TextInputEditText>(R.id.tie_Date)
        tie_Time = findViewById<TextInputEditText>(R.id.tie_Time)
        tie_RiskType = findViewById<TextInputEditText>(R.id.tie_RiskType)
        tie_AgentNote = findViewById<TextInputEditText>(R.id.tie_AgentNote)
        tie_CustomerNote = findViewById<TextInputEditText>(R.id.tie_CustomerNote)
        tie_FollowType = findViewById<TextInputEditText>(R.id.tie_FollowType)
        tie_Status = findViewById<TextInputEditText>(R.id.tie_Status)
        tie_CustomerMentionDate = findViewById<TextInputEditText>(R.id.tie_CustomerMentionDate)
        tie_Latitude = findViewById<TextInputEditText>(R.id.tie_Latitude)
        tie_Longitude = findViewById<TextInputEditText>(R.id.tie_Longitude)

        imgv_upload1 = findViewById(R.id.imgv_upload1) as ImageView
        imgv_upload2 = findViewById(R.id.imgv_upload2) as ImageView


        btnReset = findViewById(R.id.btnReset) as Button
        btnSubmit = findViewById(R.id.btnSubmit) as Button

        tie_Longitude!!.setOnClickListener(this)
        tie_Latitude!!.setOnClickListener(this)
        imgv_upload1!!.setOnClickListener(this)
        imgv_upload2!!.setOnClickListener(this)

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)


        tie_Date!!.setOnClickListener(this)
        tie_Time!!.setOnClickListener(this)
        tie_RiskType!!.setOnClickListener(this)
        tie_FollowType!!.setOnClickListener(this)
        tie_Status!!.setOnClickListener(this)
        tie_CustomerMentionDate!!.setOnClickListener(this)
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

                        tie_Longitude!!.setText(location.longitude.toString())
                        tie_Latitude!!.setText(location.latitude.toString())
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
            tie_Longitude!!.setText(mLastLocation.longitude.toString())
            tie_Latitude!!.setText(mLastLocation.latitude.toString())
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
           R.id.tie_Date->{
               DateType = 0
               openBottomSheet()
           }
           R.id.tie_Time->{
               openBottomSheetTime()
           }
           R.id.tie_RiskType->{
               getRiskType()
           }
           R.id.tie_FollowType->{
               getFollowupType()
           }
           R.id.tie_Status->{
               getProductStatus()
           }

           R.id.tie_CustomerMentionDate->{
               DateType = 1
               openBottomSheet()
           }

           R.id.tie_Latitude->{
               getLastLocation()
           }
           R.id.tie_Longitude->{
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


           R.id.btnReset->{
               removeData()
           }
           R.id.btnSubmit->{
               Config.Utils.hideSoftKeyBoard(context,v)
               Validations(v)
           }

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
            val etsearch = dialogFollowupType!! .findViewById(R.id.etsearch) as EditText

            followUpTypeSort = JSONArray()
            for (k in 0 until followUpTypeArrayList.length()) {
                val jsonObject = followUpTypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpTypeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@SiteVisitActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            val adapter = FollowupTypeAdapter(this@SiteVisitActivity, followUpTypeArrayList)
            val adapter = FollowupTypeAdapter(this@SiteVisitActivity, followUpTypeSort)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@SiteVisitActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpTypeSort = JSONArray()

                    for (k in 0 until followUpTypeArrayList.length()) {
                        val jsonObject = followUpTypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ActnTypeName").length) {
                            if (jsonObject.getString("ActnTypeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                followUpTypeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"followUpTypeSort               7103    "+followUpTypeSort)
                    val adapter = FollowupTypeAdapter(this@SiteVisitActivity, followUpTypeSort)
                    recyFollowupType!!.adapter = adapter
                    adapter.setClickListener(this@SiteVisitActivity)
                }
            })

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductStatus() {
        var prodstatus = 0
        var ReqMode = "15"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productStatusViewModel.getProductStatus(this,ReqMode)!!.observe(
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
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
            val etsearch = dialogProdStatus!! .findViewById(R.id.etsearch) as EditText

            prodStatusSort = JSONArray()
            for (k in 0 until prodStatusArrayList.length()) {
                val jsonObject = prodStatusArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodStatusSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@SiteVisitActivity, 1)
            recyProdStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            val adapter = ProductStatusAdapter(this@SiteVisitActivity, prodStatusArrayList)
            val adapter = ProductStatusAdapter(this@SiteVisitActivity, prodStatusSort)
            recyProdStatus!!.adapter = adapter
            adapter.setClickListener(this@SiteVisitActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodStatusSort = JSONArray()

                    for (k in 0 until prodStatusArrayList.length()) {
                        val jsonObject = prodStatusArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("StatusName").length) {
                            if (jsonObject.getString("StatusName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                prodStatusSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"prodStatusSort               7103    "+prodStatusSort)
                    val adapter = ProductStatusAdapter(this@SiteVisitActivity, prodStatusSort)
                    recyProdStatus!!.adapter = adapter
                    adapter.setClickListener(this@SiteVisitActivity)
                }
            })


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
//            val jsonObject = followUpTypeArrayList.getJSONObject(position)
            val jsonObject = followUpTypeSort.getJSONObject(position)
            Log.e(TAG,"ID_ActionType   "+jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tie_FollowType!!.setText(jsonObject.getString("ActnTypeName"))
        }
        if (data.equals("prodstatus")){
            dialogProdStatus!!.dismiss()
//            val jsonObject = prodStatusArrayList.getJSONObject(position)
            val jsonObject = prodStatusSort.getJSONObject(position)
            Log.e(TAG,"ID_Status   "+jsonObject.getString("ID_Status"))
            ID_Status = jsonObject.getString("ID_Status")
            tie_Status!!.setText(jsonObject.getString("StatusName"))
        }
    }

    private fun Validations(v : View) {
        strAgentNote = tie_AgentNote!!.text.toString()
        strCustomerNote = tie_CustomerNote!!.text.toString()
        strLatitude = tie_Latitude!!.text.toString()
        strLongitude = tie_Longitude!!.text.toString()
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

        tie_Date!!.setText("")
        tie_Time!!.setText("")
        tie_RiskType!!.setText("")
        tie_AgentNote!!.setText("")
        tie_CustomerNote!!.setText("")
        tie_FollowType!!.setText("")
        tie_Status!!.setText("")
        tie_CustomerMentionDate!!.setText("")
        tie_Latitude!!.setText("")
        tie_Longitude!!.setText("")


        image1 = ""
        image2 = ""
        imgv_upload1!!.setImageDrawable(resources.getDrawable(R.drawable.lead_uploads))
        imgv_upload2!!.setImageDrawable(resources.getDrawable(R.drawable.lead_uploads))
    }

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1!!.minDate = System.currentTimeMillis() - 1000

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day1: Int = date_Picker1!!.getDayOfMonth()
                val mon1: Int = date_Picker1!!.getMonth()
                val month1: Int = mon1+1
                val year1: Int = date_Picker1!!.getYear()
                var strDay = day1.toString()
                var strMonth = month1.toString()
                var strYear = year1.toString()

                yr = year1
                month =  month1
                day= day1


                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }

                if (DateType == 0){
                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    strDate = strYear+"-"+strMonth+"-"+strDay
                }
                if (DateType == 1){
                    tie_CustomerMentionDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    strMentionDate = strYear+"-"+strMonth+"-"+strDay
                }




            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun openBottomSheetTime() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_timer, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)
        //   time_Picker1!!.currentMinute = System.currentTimeMillis() - 1000


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {

//                val hour: Int = time_Picker1!!.hour
//                val min: Int = time_Picker1!!.minute

                hr = time_Picker1!!.hour
                min = time_Picker1!!.minute

                strTime = String.format(
                    "%02d:%02d %s", if (hr == 0) 12 else hr,
                    min, if (hr < 12) "AM" else "PM"
                )

                tie_Time!!.setText(strTime)



            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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
                tie_RiskType!!.setText(value)
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

}