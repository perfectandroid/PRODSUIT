package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.perfect.prodsuit.R
import java.io.*
import java.util.*
import com.perfect.prodsuit.Helper.Config as Config1

class SiteVisitActivity : AppCompatActivity(), View.OnClickListener  {

    val TAG : String = "SiteVisitActivity"
    lateinit var context: Context
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    var llFromdate: LinearLayout? = null
    var llFromDatePick: LinearLayout? = null
    var llToDate: LinearLayout? = null
    var llToDatePick: LinearLayout? = null
    var llMentionDate: LinearLayout? = null
    var llMentionDatePick: LinearLayout? = null

    var txtFromDate: TextView? = null
    var txtFromSubmit: TextView? = null
    var txtToDate: TextView? = null
    var txtToSubmit: TextView? = null
    var txtMentionDate: TextView? = null
    var txtMentionSubmit: TextView? = null
    var txtLatitude: TextView? = null
    var txtLongitude: TextView? = null

    var imFromDate: ImageView? = null
    var imToDate: ImageView? = null
    var imMentionDate: ImageView? = null


    var datePickerFrom: DatePicker? = null
    var datePickerTo: DatePicker? = null
    var datePickerMention: DatePicker? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_site_visit)
        context = this@SiteVisitActivity

        setRegViews()
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

        datePickerFrom = findViewById(R.id.datePickerFrom) as DatePicker
        datePickerTo = findViewById(R.id.datePickerTo) as DatePicker
        datePickerMention = findViewById(R.id.datePickerMention) as DatePicker

        llFromdate!!.setOnClickListener(this)
        llToDate!!.setOnClickListener(this)
        llMentionDate!!.setOnClickListener(this)

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


        datePickerFrom!!.minDate = Calendar.getInstance().timeInMillis
      //  datePickerFrom!!.minDate = Calendar.getInstance().timeInMillis

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
//                        findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
//                        findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
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

//            findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
//            findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
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
                   Config1.Utils.hideSoftKeyBoard(this@SiteVisitActivity,v)
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
                   Config1.Utils.hideSoftKeyBoard(this@SiteVisitActivity,v)
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
               }
               catch (e: Exception){
                   Log.e(TAG,"Exception   428   "+e.toString())
               }
           }
           R.id.txtToSubmit->{
               try {
                   datePickerTo!!.minDate = Calendar.getInstance().timeInMillis
                   val day: Int = datePickerTo!!.getDayOfMonth()
                   val mon: Int = datePickerTo!!.getMonth()
                   val month: Int = mon+1
                   val year: Int = datePickerTo!!.getYear()
                   var strDay = day.toString()
                   var strMonth = month.toString()
                   var strYear = year.toString()
                   if (strDay.length == 1){
                       strDay ="0"+day
                   }
                   if (strMonth.length == 1){
                       strMonth ="0"+strMonth
                   }
                   txtToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                   llToDatePick!!.visibility=View.GONE
                   toDateMode = "1"
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
               }
               catch (e: Exception){
                   Log.e(TAG,"Exception   428   "+e.toString())
               }
           }

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
            // Permission is not granted
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

        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    var selectedImageUri: Uri = data.getData()!!
                    data.getData()
                    if(strImage.equals("1")) {

                        //   val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
                        imgv_upload1!!.setImageURI(contentURI)
                        image1 = getRealPathFromURI(selectedImageUri)
                        if (image1 != null) {
                        }
                    }
                    if(strImage.equals("2")) {

                        //  val img_image2 = findViewById(R.id.img_image2) as RoundedImageView
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
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        // No explanation needed; request the permission
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
                        //  val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
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
                        //   val img_image2 = findViewById(R.id.img_image2) as RoundedImageView
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