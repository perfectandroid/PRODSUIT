package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.AttanceMarkingUpdateViewModel
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AttendanceMarkingActivity : AppCompatActivity(), View.OnClickListener {

    var TAG = "AttendanceMarkingActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    internal var tv_user: TextView? = null
    internal var tv_userrole: TextView? = null
    internal var tv_Cancel: TextView? = null
    internal var tv_Punch: TextView? = null
    internal var tv_attndnce: TextView? = null

    internal var tv_date: TextView? = null
    internal var tv_time: TextView? = null
    internal var tv_locaddress: TextView? = null
    internal var tv_status: TextView? = null
    internal var profile_image: CircleImageView? = null

    var FK_Employee: String? = ""
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 5000
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null
    var strLatitude: String? = ""
    var strLongitue: String? = ""
    var strLocationAddress: String? = ""
    var strTime: String? = ""
    var strDate: String? = ""
    var strStatus: String? = ""

    lateinit var attanceMarkingUpdateViewModel: AttanceMarkingUpdateViewModel
    var updateCount = 0

    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 2
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_attendance_marking)
        context = this@AttendanceMarkingActivity
        attanceMarkingUpdateViewModel = ViewModelProvider(this).get(AttanceMarkingUpdateViewModel::class.java)

        setRegviews()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        runTimePermission()
        getLastLocation()


        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun runTimePermission() {
        Log.e(TAG,"9400001    runTimePermission  ")

        if (isLocationEnabled()){
            if (checkPermissions()) {
                Log.e(TAG,"9400002    runTimePermission  ")
                if (isLocationEnabled()) {
                    Log.e(TAG,"9400003    runTimePermission  ")
                    getLastLocation()
                } else {
                    Log.e(TAG,"9400004    runTimePermission  ")
                    // Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                    confirmBottomSheet("1")

                }
            } else {
                Log.e(TAG,"9400005    runTimePermission  ")
                // requestPermissions()
                checkPermissions()
            }
        }else{

            confirmBottomSheet("1")

        }

    }

    private fun setRegviews() {
        profile_image = findViewById(R.id.profile_image)
        tv_user = findViewById(R.id.tv_user)
        tv_attndnce= findViewById(R.id.tv_attndnce)
        tv_userrole = findViewById(R.id.tv_userrole)
        tv_Cancel = findViewById(R.id.tv_Cancel)
        tv_Punch = findViewById(R.id.tv_Punch)
        tv_date = findViewById(R.id.tv_date)
        tv_time = findViewById(R.id.tv_time)
        tv_locaddress = findViewById(R.id.tv_locaddress)
        tv_status = findViewById(R.id.tv_status)

        tv_Cancel!!.setOnClickListener(this)
        tv_Punch!!.setOnClickListener(this)
        tv_attndnce!!.setOnClickListener(this)

        getSharedValues()
    }

    private fun getSharedValues() {

        val FK_EmployeeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF2, 0)
        val UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF42, 0)
        val LocLocationNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF60, 0)
        val EnteredDateSP = applicationContext.getSharedPreferences(Config.SHARED_PREF61, 0)
        val EnteredTimeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF62, 0)
        val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)

        FK_Employee = FK_EmployeeSP.getString("FK_Employee", "")
        tv_user!!.setText(""+UserNameSP.getString("UserName",""))
        tv_userrole!!.setText(""+UserRoleSP.getString("UserRole",""))

        tv_date!!.setText(""+EnteredDateSP.getString("EnteredDate",""))
        tv_time!!.setText(""+EnteredTimeSP.getString("EnteredTime",""))
        tv_locaddress!!.setText(""+LocLocationNameSP.getString("LocLocationName",""))

        var status = StatusSP.getString("Status","")
        if (status.equals("0") || status.equals("")){
            tv_status!!.text = "Punch Out"
            profile_image!!.borderColor = Color.RED
            tv_status!!.setTextColor(Color.RED)
            tv_Punch!!.text = "Punch In"

            strStatus = "1"
        }
        else if (status.equals("1")){
            tv_status!!.text = "Punch In"
            tv_status!!.setTextColor(Color.GREEN)
            profile_image!!.borderColor = Color.GREEN
            tv_Punch!!.text = "Punch Out"

            strStatus = "0"
        }


    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.tv_Cancel -> {
                onBackPressed()
            }
            R.id.tv_attndnce -> {
                Config.disableClick(v)
                val i = Intent(this@AttendanceMarkingActivity, AttendanceReportsActivity::class.java)
                startActivity(i)
                finish()
            }

            R.id.tv_Punch -> {
                Config.disableClick(v)
               if (strLatitude.equals("") || strLongitue.equals("")){
                   runTimePermission()
               }else{

                   try {
                       val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                       val currentDate = sdf.format(Date())
                       Log.e(TAG,"DATE TIME  196  "+currentDate)
                       val newDate: Date = sdf.parse(currentDate)
                       Log.e(TAG,"newDate  196  "+newDate)
                       val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")
                       val sdfTime1 = SimpleDateFormat("hh:mm:ss")
                       strDate = sdfDate1.format(newDate)
                       strTime = sdfTime1.format(newDate)

                       Log.e(TAG,"   13800"
                               +"\n  strLatitude         "+strLatitude
                               +"\n  strLongitue         "+strLongitue
                               +"\n  strLocationAddress  "+strLocationAddress
                               +"\n  strDate             "+strDate
                               +"\n  strTime             "+strTime)


                       confirmBottomSheet("2")


                   }catch (e: Exception){

                       Log.e(TAG,"Exception 196  "+e.toString())
                   }
               }
            }
        }
    }




    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
       try {
           mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
               var location: Location? = task.result
               if (location == null) {
                   requestNewLocationData()
               } else {

                   try {
                       geocoder = Geocoder(this, Locale.getDefault())
                       addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1)
                       var address = addresses!!.get(0).getAddressLine(0)
                       var city = addresses!!.get(0).locality
                       var state = addresses!!.get(0).adminArea
                       var country = addresses!!.get(0).countryName
                       var postalCode = addresses!!.get(0).postalCode
                       var knownName = addresses!!.get(0).featureName
                       strLongitue = location.longitude.toString()
                       strLatitude = location.latitude.toString()

                       strLocationAddress = ""
                       if (!address.equals("")) {
                           strLocationAddress = address!!
                       }
                       if (!city.equals("")) {
                           strLocationAddress = strLocationAddress + "," + city!!
                       }
                       if (!state.equals("")) {
                           strLocationAddress = strLocationAddress + "," + state!!
                       }
                       if (!country.equals("")) {
                           strLocationAddress = strLocationAddress + "," + country!!
                       }
                       if (!postalCode.equals("")) {
                           strLocationAddress = strLocationAddress + "," + postalCode!!
                       }

                       Log.e("TAG","SSSS   170113   "+strLocationAddress)
                   }catch (e: Exception){
                       Log.e("TAG","SSSS   170112   "+e.toString())
                   }

               }
           }
       }catch (e : Exception){

       }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            return true
//        }
//        return false

        var result = false
        val locationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermision =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        val commandPermision =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)



        val backgroundPermision =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (coarsePermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (commandPermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
        }
        if (backgroundPermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }


//        if (backgroundPermision != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            result  = true
        }
        return result
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
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

            try {
                geocoder = Geocoder(this@AttendanceMarkingActivity, Locale.getDefault())
                addresses = geocoder!!.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1)
                var address = addresses!!.get(0).getAddressLine(0)
                var city = addresses!!.get(0).locality
                var state = addresses!!.get(0).adminArea
                var country = addresses!!.get(0).countryName
                var postalCode = addresses!!.get(0).postalCode
                var knownName = addresses!!.get(0).featureName
                strLongitue = mLastLocation.longitude.toString()
                strLatitude = mLastLocation.latitude.toString()

                strLocationAddress = ""
                if (!address.equals("")) {
                    strLocationAddress = address!!
                }
                if (!city.equals("")) {
                    strLocationAddress = strLocationAddress + "," + city!!
                }
                if (!state.equals("")) {
                    strLocationAddress = strLocationAddress + "," + state!!
                }
                if (!country.equals("")) {
                    strLocationAddress = strLocationAddress + "," + country!!
                }
                if (!postalCode.equals("")) {
                    strLocationAddress = strLocationAddress + "," + postalCode!!
                }


                Log.e("TAG","SSSS   170113   "+strLocationAddress)
            }catch (e: Exception){
                Log.e("TAG","SSSS   170112   "+e.toString())
            }
        }
    }
    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay!!.toLong())
            getLastLocation()
        }.also { runnable = it }, delay!!.toLong())
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!) //stop handler when activity not visible super.onPause();
    }

    override fun onRestart() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay!!.toLong())
            getLastLocation()
        }.also { runnable = it }, delay!!.toLong())
        super.onRestart()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun confirmBottomSheet(type : String) {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.confirm_popup, null)

        val img_confirm = view.findViewById<ImageView>(R.id.img_confirm)
        val btnNo = view.findViewById<TextView>(R.id.btnNo)
        val btnYes = view.findViewById<TextView>(R.id.btnYes)
        val tv_text = view.findViewById<TextView>(R.id.tv_text)

        if (type.equals("1")){
            tv_text.setText("Please give us access to your GPS Location")
        }
        else if (type.equals("2")){

            if (strStatus.equals("0")){
                img_confirm!!.setImageResource(R.drawable.ic_signout)
                tv_text.setText("Do you want to Punch Out your current session?")
            }else if (strStatus.equals("1")){
                img_confirm!!.setImageResource(R.drawable.ic_signin)
                tv_text.setText("Do you want to Punch In ?")
            }

        }


        btnNo.setOnClickListener {
            dialog.dismiss()
            if (type.equals("1")){
                onBackPressed()
            }
        }
        btnYes.setOnClickListener {
            dialog.dismiss()
            if (type.equals("1")){
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
            else if (type.equals("2")){
                updateCount = 0
                updateAttendance()
            }

        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun updateAttendance() {

        Log.e(TAG,"   13800"
                +"\n  strLatitude         "+strLatitude
                +"\n  strLongitue         "+strLongitue
                +"\n  strLocationAddress  "+strLocationAddress
                +"\n  strDate             "+strDate
                +"\n  strTime             "+strTime)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                attanceMarkingUpdateViewModel.setAttanceMarkingUpdate(this, strLatitude!!,strLongitue!!,strLocationAddress!!,strDate!!,strTime!!,strStatus!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (updateCount == 0) {
                                    updateCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {


                                        val LocLongitudeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF58, 0)
                                        val LocLongitudeEditer = LocLongitudeSP.edit()
                                        LocLongitudeEditer.putString("LocLongitude", strLongitue)
                                        LocLongitudeEditer.commit()

                                        val LocLattitudeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF59, 0)
                                        val LocLattitudeEditer = LocLattitudeSP.edit()
                                        LocLattitudeEditer.putString("LocLattitude", strLatitude)
                                        LocLattitudeEditer.commit()


                                        val LocLocationNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF60, 0)
                                        val LocLocationNameEditer = LocLocationNameSP.edit()
                                        LocLocationNameEditer.putString("LocLocationName", strLocationAddress)
                                        LocLocationNameEditer.commit()

                                        val EnteredDateSP = applicationContext.getSharedPreferences(Config.SHARED_PREF61, 0)
                                        val EnteredDateEditer = EnteredDateSP.edit()
                                        EnteredDateEditer.putString("EnteredDate", strDate)
                                        EnteredDateEditer.commit()

                                        val EnteredTimeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF62, 0)
                                        val EnteredTimeEditer = EnteredTimeSP.edit()
                                        EnteredTimeEditer.putString("EnteredTime", strTime)
                                        EnteredTimeEditer.commit()

                                        val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
                                        val StatusEditer = StatusSP.edit()
                                        StatusEditer.putString("Status", strStatus)
                                        StatusEditer.commit()

                                        getSharedValues()

                                        successPopup(jObject)

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@AttendanceMarkingActivity,
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
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun successPopup(jObject: JSONObject) {
        try {

            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.success_bottom, null)

            val btnOk = view.findViewById<TextView>(R.id.btnOk)
            val tv_success = view.findViewById<TextView>(R.id.tv_success)
            val tv_text = view.findViewById<TextView>(R.id.tv_text)
            tv_text!!.visibility = View.GONE
            tv_success.setText(jObject.getString("EXMessage"))

            btnOk.setOnClickListener {
                dialog.dismiss()
                onBackPressed()

            }
            dialog.setCancelable(false)
            dialog!!.setContentView(view)

            dialog.show()


        }catch (e:Exception){

        }
    }

}

