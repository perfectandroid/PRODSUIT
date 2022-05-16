package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.AttendanceAddModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object AttendanceAddRepository {

    private var progressDialog: ProgressDialog? = null
    val attendanceAddSetterGetter = MutableLiveData<AttendanceAddModel>()
    val TAG: String = "AttendanceAddRepository"

    fun getServicesApiCall(context: Context, IsOnline : String, strLatitude : String , strLongitue : String , address  :String,SubMode  :String): MutableLiveData<AttendanceAddModel> {
        addAttendance(context,IsOnline,strLatitude,strLongitue,address,SubMode)
        return attendanceAddSetterGetter
    }

    private fun addAttendance(context: Context ,IsOnline : String, strLatitude : String , strLongitue : String , address  :String,SubMode : String) {

        try {
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

//                "ReqMode":"50",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "IsOnline":"1",
//                "LocLatitude":"75.12364",
//                "LocLongitude":"75.1256360,
//                "LocationName:"Kozhikode"

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("50"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))

              //  requestObject1.put("IsOnline", ProdsuitApplication.encryptStart(IsOnline))
                requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(strLatitude))
                requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(strLongitue))
                requestObject1.put("LocationName", ProdsuitApplication.encryptStart(address))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(SubMode))

                Log.e(TAG,"requestObject1   82   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.addUpdateUserLoginStatus(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"response  1000    "+response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<AttendanceAddModel>()
                        leads.add(AttendanceAddModel(response.body()))
                        val msg = leads[0].message
                        attendanceAddSetterGetter.value = AttendanceAddModel(msg)
                    } catch (e: Exception) {
                        Log.e(TAG,"  1000    "+e.toString())
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
        }

    }
}