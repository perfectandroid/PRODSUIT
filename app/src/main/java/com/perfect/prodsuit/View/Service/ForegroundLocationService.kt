package com.perfect.prodsuit.View.Service

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class ForegroundLocationService : Service() {

    var TAG = "ForegroundLocationService"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    lateinit var context: Context
    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null

 //   private var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"250001  ")
        context = this
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { location ->
                    // Handle the obtained location here
//                    Toast.makeText(
//                        this@ForegroundLocationService,
//                        "Current location: ${location.latitude}, ${location.longitude}",
//                        Toast.LENGTH_SHORT
//                    ).show()

                    Log.e(TAG,"25000121  ${location.latitude}, ${location.longitude}")
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
                        Log.e(TAG,"740000 Location           :    "+location.latitude+"  :  "+location.longitude)
                        Log.e(TAG,"123452 City           :    "+address)
                        Log.e(TAG,"123453 batteryPercentage  :   "+batteryPercentage)
                       //    Toast.makeText(context,"Address  "+ address,Toast.LENGTH_SHORT).show()

                        updateLocation(location.latitude.toString(),location.longitude.toString(),address,batteryPercentage.toString(),context)

                    }catch (e : Exception){
                        Log.e(TAG,"123456 Exception  :   "+e.toString())
                    }

                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG,"250002  ")
        startForegroundService()
        requestLocationUpdates()
      //  acquireWakeLock()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG,"250003  ")
        stopForegroundService()
        removeLocationUpdates()
    }

//    private fun acquireWakeLock() {
//        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
//        wakeLock = powerManager.newWakeLock(
//            PowerManager.PARTIAL_WAKE_LOCK,
//            "ForegroundLocationService::WakeLock"
//        )
//        wakeLock?.acquire()
//    }


    private fun startForegroundService() {
        createNotificationChannel()
//
//        val notificationIntent = Intent(this, SplashActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this,
//            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(""+resources.getString(R.string.app_name))
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_location)
            .setSilent(true)
            .setContentIntent(null)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stopForegroundService() {
        Log.e(TAG,"250004  ")
        stopForeground(true)
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Location Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun requestLocationUpdates() {
        TIMER_INTERVAL = Config.getMilliSeconds(context).toLong()
        val locationRequest = LocationRequest.create().apply {
            interval = TIMER_INTERVAL // Update interval in milliseconds (e.g., every 10 seconds)
            fastestInterval = 10 // Fastest update interval in milliseconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
    }

    private fun removeLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val CHANNEL_ID = "LOCATION_SETTINGS_CHANNEL"
        private const val NOTIFICATION_ID = 1
        private var TIMER_INTERVAL: Long = 30000 // 10 second
    }

    private fun updateLocation(latitudes : String,longitude : String,address : String,batteryPercentage : String,context : Context) {

        try {
            Log.e(TAG,"7400002   updateLocation")
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
                    val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)


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
                    requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                    Log.e(TAG,"7400003  requestObject123    "+requestObject1)

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

                            Log.e(TAG,"7400004 response  "+response.body())
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e(TAG,"7400005  Exception "+e.toString())

                        }
                    }
                    override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {

                    }
                })
            }catch (e : Exception){
                e.printStackTrace()

            }
        }catch (e: Exception){

            Log.e(TAG,"7400006    "+e.toString())

        }
    }



}