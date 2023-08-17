package com.perfect.prodsuit.View.Service

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.Helper.ProdsuitApplication
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class  LocationReceiver: BroadcastReceiver() {
    var TAG = "LocationReceiver"

    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null
   var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e(TAG,"122222   Action   "+intent!!.getAction())
        if ("my.custom.broadcast".equals(intent!!.getAction())) {
            Log.e(TAG, "122222   Start")

            val db = DBHelper(context!!, null)

            try {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                val currentDate = sdf.format(Date())
                Log.e(TAG, "DATE TIME  196  " + currentDate)
                val newDate: Date = sdf.parse(currentDate)
                Log.e(TAG, "newDate  196  " + newDate)
                val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
                val sdfTime1 = SimpleDateFormat("hh:mm aa")


                val locationHelper = LocationHelper(context)
                locationHelper.getCurrentLocation { location ->
                    if (location != null) {
                        // Handle the received location here
                        val latitude = location.latitude
                        val longitude = location.longitude

                        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
                            context.registerReceiver(null, ifilter)
                        }

                        Log.e(TAG, "latitude  19600000  " + latitude+"  :  "+longitude)

                        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                        val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

                        val batteryPercentage = (level.toFloat() / scale.toFloat() * 100).toInt()


                        geocoder = Geocoder(context, Locale.getDefault())
                        addresses = geocoder!!.getFromLocation(location.latitude, location.longitude, 1)
                        var address = addresses!!.get(0).getAddressLine(0)
                        Log.e(TAG,"122222002 Location           :    "+location.latitude+"  :  "+location.longitude)
                        Log.e(TAG,"122222003 City           :    "+address)
                        Log.e(TAG,"122222004 batteryPercentage  :   "+batteryPercentage)
                        // Do something with the location data

//                        val db = DBHelper(context!!, null)
//                        db.addName(sdfDate1.format(newDate), sdfTime1.format(newDate),batteryPercentage.toString(),address)
//                        db.delete()
                        updateLocation(location.latitude.toString(),location.longitude.toString(),address,batteryPercentage.toString(),context)

                    } else {
                        // Failed to get the location
                        Log.e(TAG,"19600000 Failed to get the location  :   ")
                    }
                }

            }catch (e: Exception){

                Log.e(TAG,"Exception   "+e.toString())
            }


        }

    }


    private fun updateLocation(latitudes : String,longitude : String,address : String,batteryPercentage : String,context : Context) {

        try {
            Log.e(TAG,"122222007   updateLocation")
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

                    Log.e(TAG,"122222008  requestObject1    "+requestObject1)

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

                            Log.e(TAG,"122222009 response  "+response.body())
                        } catch (e: Exception) {
                            e.printStackTrace()
                            //  stopSelf()
                            Log.e(TAG,"1222220010  Exception "+e.toString())
                        }
                    }
                    override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    }
                })
            }catch (e : Exception){
                e.printStackTrace()
            }
        }catch (e: Exception){
            Log.e(TAG,"1222220011    "+e.toString())
        }
    }


}