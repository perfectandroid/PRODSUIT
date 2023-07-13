package com.perfect.prodsuit.fire

import android.app.ProgressDialog
import android.bluetooth.BluetoothClass
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log

import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DeviceHelper
import com.perfect.prodsuit.Helper.ProdsuitApplication
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object FireBaseConfig {

    var TAG = "FireBaseConfig"
    var deviceId = ""

    fun getToken(context: Context) {

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task: Task<String?> ->
            Log.e("spalsh", task.result!!)
            if (task.isSuccessful){
                Log.e(TAG,"Token  99991    "+ task.result!!)
               // val deviceId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                deviceId = DeviceHelper.getDeviceID(context)
                Log.e(TAG,"uniqueId  99991    "+ deviceId)
                checkUserToken(context,task.result!!,deviceId)
                Log.e(TAG,"uniqueId  99991    "+ deviceId)
             //   fetchFcmServerKey()

            //    updateUserTokenDeviceID(context,task.result!!,deviceId)

            }
        }
    }

    fun checkUserToken(context: Context, userToken: String, deviceId1: String) {

        try {
            val fireBaseTokenSP = context.getSharedPreferences(Config.SHARED_PREF65, 0)
            val fireBaseToken = fireBaseTokenSP.getString("fireBaseToken","")
            val deviceIDSP = context.getSharedPreferences(Config.SHARED_PREF66, 0)
            val deviceIDNew = deviceIDSP.getString("deviceID","")
            Log.e(TAG,"7811100  deviceId   "+deviceId1)
            Log.e(TAG,"7811100  deviceIDNew   "+deviceIDNew)
            if (fireBaseToken.equals("")){
                Log.e(TAG,"78111001  deviceIDNew   "+deviceIDNew)
                updateUserTokenDeviceID(context,userToken,deviceId)
            }
            else if (!fireBaseToken.equals(userToken) || !deviceIDNew.equals(deviceId1)){
                Log.e(TAG,"78111002  deviceIDNew   "+deviceIDNew)
                updateUserTokenDeviceID(context,userToken,deviceId1)
            }
        }catch (e : Exception){

        }

    }


    fun ServiceStart(context: Context) {
        val isMyServiceRunning = Config.isServiceRunning(context, MyFirebaseMessagingService::class.java)
        if (!isMyServiceRunning){
            context.startService(Intent(context, MyFirebaseMessagingService::class.java))
        }

    }

    private fun updateUserTokenDeviceID(context: Context, userToken: String, deviceId1: String) {


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
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
//
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
//                requestObject1.put("LocationEnteredDate", ProdsuitApplication.encryptStart(strDate))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_Employee))

//                {"BankKey":"","Token":"9C19C49E-B5DE-4E55-8F09-546CBEA324F8","ID_User":"1","User_Type":"1","Device_ID":"2",
//                    "User_Token":"Test User Token2","FK_Company":"1"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("User_Type", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("Device_ID", ProdsuitApplication.encryptStart(deviceId1))
                requestObject1.put("User_Token", ProdsuitApplication.encryptStart(userToken))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))


                Log.e(TAG,"78111  deviceId   "+deviceId1)
                Log.e(TAG,"78111  userToken   "+userToken)
                Log.e(TAG,"781112     "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getSaveCustomerFCMToken(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        Log.e(TAG,"7811120    "+response.body())
                        val jObject = JSONObject(response.body())
                        if (jObject.getString("StatusCode") == "0") {

                            val fireBaseTokenSP = context.getSharedPreferences(Config.SHARED_PREF65, 0)
                            val fireBaseTokenEditer = fireBaseTokenSP.edit()
                            fireBaseTokenEditer.putString("fireBaseToken", userToken)
                            fireBaseTokenEditer.commit()

                            val deviceIDSP = context.getSharedPreferences(Config.SHARED_PREF66, 0)
                            val deviceIDEditer = deviceIDSP.edit()
                            deviceIDEditer.putString("deviceID", deviceId1)
                            deviceIDEditer.commit()
                        }

                    } catch (e: Exception) {

                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {

                }
            })



        }catch (e : Exception){
            e.printStackTrace()

        }
    }


}