package com.perfect.prodsuit.View.Service

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.*
import android.os.BatteryManager
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.View.Activity.HomeActivity
import com.perfect.prodsuit.View.lifes.MyApp
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat

import java.util.*

class LocationUpdateService : Service() {
   // , LocationListener
//   private lateinit var employeeLocationUpdateViewModel: EmployeeLocationUpdateViewModel
    //   31
    var TAG = "LocationUpdateService"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var timer: Timer
    private var locationStateReceiver: LocationStateReceiver? = null
    lateinit var context: Context
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null

    private lateinit var activity: HomeActivity
    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"52011   onCreate")
   //     employeeLocationUpdateViewModel = ViewModelProvider(this).get(EmployeeLocationUpdateViewModel::class.java)
        context = this
        timer = Timer()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var bTracker = jsonObj!!.getString("LOCATION_TRACKING").toBoolean()
        if (bTracker){
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.lastLocation?.let { location ->
                        // Handle the received location
                        // ...

                        try {
                            val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
                                registerReceiver(null, ifilter)
                            }

                            val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                            val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

                            val batteryPercentage = (level.toFloat() / scale.toFloat() * 100).toInt()

                            geocoder = Geocoder(context, Locale.getDefault())
                            addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1)
                            var address = addresses!!.get(0).getAddressLine(0)
                            Log.e(TAG,"123451 Location           :    "+location.latitude+"  :  "+location.longitude)
                            Log.e(TAG,"123452 City           :    "+address)
                            Log.e(TAG,"123453 batteryPercentage  :   "+batteryPercentage)
                            //   Toast.makeText(context,"Address  "+ address,Toast.LENGTH_SHORT).show()
                            updateLocation(location.latitude.toString(),location.longitude.toString(),address,batteryPercentage.toString(),context)


                        }catch (e : Exception){
                            Log.e(TAG,"123456 Exception  :   "+e.toString())
                        }

                    }
                }
            }
        }else{
            Log.e(TAG,"62222201   ")
            stopLocationUpdates()
        }

    }

    private fun updateLocation(latitudes : String,longitude : String,address : String,batteryPercentage : String,context : Context) {

        try {
            Log.e(TAG,"52012   updateLocation")
            try {
                val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)

                val client = OkHttpClient.Builder()
                    .sslSocketFactory(Config.getSSLSocketFactory(context))
                    .hostnameVerifier(Config.getHostnameVerifier())
                    .build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URLSP.getString("BASE_URL", null))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()
                val apiService = retrofit.create(ApiInterface::class.java!!)
                val requestObject1 = JSONObject()
                try {

                    val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                    val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                    val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                    val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39,0)
                    val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36,0)

                    val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                    val currentDate = sdf.format(Date())

                    val newDate: Date = sdf.parse(currentDate)
                    Log.e(TAG,"newDate  196  "+newDate)
                    val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")
                    val sdfTime1 = SimpleDateFormat("hh:mm:ss")

                    var curDate = sdfDate1.format(newDate)
                    var curTime = sdfTime1.format(newDate)


//                    {"BankKey":"-500","Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","FK_Company":"1","EntrBy":"riyas",
//                        "FK_Employee":"40","LocLatitude":"11.247589511","LocLongitude":"75.834220611",
//                        "LocationAddress":"HiLITE Business Park, 5th floor Hilite Business Park, Poovangal, Pantheeramkavu, Kerala 673014, India,Pantheeramkavu,Kerala,India,673014",
//                        "LocationEnteredDate":"2023-05-31","LocationEnteredTime":"10:43:00","ChargePercentage":"49"}

                    requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                    requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                    requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                    requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                    requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                    requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(latitudes))
                    requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(longitude))
                    requestObject1.put("LocationAddress", ProdsuitApplication.encryptStart(address))
                    requestObject1.put("LocationEnteredDate", ProdsuitApplication.encryptStart(curDate))
                    requestObject1.put("LocationEnteredTime", ProdsuitApplication.encryptStart(curTime))
                    requestObject1.put("ChargePercentage", ProdsuitApplication.encryptStart(batteryPercentage))

                    Log.e(TAG,"17200  requestObject1    "+requestObject1)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val body = RequestBody.create(
                    okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    requestObject1.toString()
                )
                val call = apiService.UpdateEmployeeLocationUpdate(body)
                call.enqueue(object : retrofit2.Callback<String> {
                    override fun onResponse(
                        call: retrofit2.Call<String>, response:
                        Response<String>
                    ) {
                        try {
                            Log.e(TAG,"17200 response  "+response.body())
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e(TAG,"17200  Exception "+e.toString())
                        }
                    }
                    override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {

                    }
                })
            }catch (e : Exception){
                e.printStackTrace()

            }
        }catch (e: Exception){

            Log.e(TAG,"12424    "+e.toString())
        }
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG,"52013   onStartCommand")
        val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var bTracker = jsonObj!!.getString("LOCATION_TRACKING").toBoolean()
        if (bTracker){
            startTimer()
            startLocationUpdates()
        }else{
            stopTimer()
            stopLocationUpdates()
        }

        Log.e(TAG,"35902   ")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG,"52014   onDestroy")
        stopTimer()
        stopLocationUpdates()
    }



    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        Log.e(TAG,"52015   startLocationUpdates")
        TIMER_INTERVAL = Config.getMilliSeconds(context).toLong()
        val locationRequest = LocationRequest.create().apply {
            interval = TIMER_INTERVAL // Update interval in milliseconds
            fastestInterval = 0 // Fastest update interval in milliseconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)


    }

    private fun openLocationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun stopLocationUpdates() {
        Log.e(TAG,"52016   stopLocationUpdates")
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun startTimer() {
        Log.e(TAG,"52017   startTimer")
        TIMER_INTERVAL = Config.getMilliSeconds(context).toLong()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Perform the desired task here
                var isFor = Config.isAppInForeground(context)
                Log.e(TAG,"18444  isFor  :   "+isFor)
                Log.e("MyService  123454  :  ", "Timer task executed")
                val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
                val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
                var bTracker = jsonObj!!.getString("LOCATION_TRACKING").toBoolean()
                if (bTracker){
                    val isAppActive = (application as MyApp).isAppActive()
                    Log.e("ACIVE  123455   ", "isAppActive: $isAppActive")
//                    if (isAppActive){
                    if (isAppActive){
                        if (checkPermissions()) {
                            if (isLocationEnabled()) {

                            }else{
                                openLocationSettings(context)
                            }
                        }

                    }

                }


            }
        }, 0, TIMER_INTERVAL)
    }

    private fun stopTimer() {
        Log.e(TAG,"52018   stopTimer")
        timer.cancel()
    }

    companion object {

        private var TIMER_INTERVAL: Long = 30000 // 10 second
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


}

