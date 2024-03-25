package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.FollowupStatusUpdateModel
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object FollowupStatusUpdateRepository {

    var TAG = "FollowupStatusUpdateRepository"
    val followupStatusUpdateSetterGetter = MutableLiveData<FollowupStatusUpdateModel>()
    private var progressDialog: ProgressDialog? = null
    fun getServicesApiCall(context: Context,TransMode : String,customer_service_register : String,latitude : String,longitude : String,
                           address : String,curDate : String, curTime : String,journeyType : String): MutableLiveData<FollowupStatusUpdateModel> {
        getFollowupStatusUpdate(context,TransMode,customer_service_register,latitude,longitude,address,curDate, curTime,journeyType)
        return followupStatusUpdateSetterGetter
    }

    private fun getFollowupStatusUpdate(context: Context,TransMode : String,customer_service_register : String,latitude : String,longitude : String,
                                        address : String,curDate : String, curTime : String,journeyType : String) {
        try {


            followupStatusUpdateSetterGetter.value = FollowupStatusUpdateModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
                R.drawable.progress))
            progressDialog!!.show()
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
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)



//                {"BankKey":"-500","Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","FK_Company":"1","TransMode":"CUSA",
//                    "EntrBy":"riyas","FK_Master":"351","LocLatitude":"11.247589511","LocLongitude":"75.834220611",
//                    "Address":"HiLITE Business Park, 5th floor Hilite Business Park, Poovangal, Pantheeramkavu, Kerala 673014, India,Pantheeramkavu,Kerala,India,673014",
//                    "LocationEnteredDate":"2023-05-31","LocationEnteredTime":"10:43:00","Status":"1"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(TransMode))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Master", ProdsuitApplication.encryptStart(customer_service_register))
                requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(latitude))
                requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(longitude))
                requestObject1.put("Address", ProdsuitApplication.encryptStart(address))
                requestObject1.put("LocationEnteredDate", ProdsuitApplication.encryptStart(curDate))
                requestObject1.put("LocationEnteredTime", ProdsuitApplication.encryptStart(curTime))
                requestObject1.put("Status", ProdsuitApplication.encryptStart(journeyType))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"requestObject1  910   "+requestObject1)



            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.UpdateFollowupStatusUpdate(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"108  response  "+response.body())
                        val users = ArrayList<OverDueModel>()
                        users.add(OverDueModel(response.body()))
                        val msg = users[0].message
                        followupStatusUpdateSetterGetter.value = FollowupStatusUpdateModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }
        catch (e: Exception) {
            e.printStackTrace()
            progressDialog!!.dismiss()
         //   Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }
    }
}