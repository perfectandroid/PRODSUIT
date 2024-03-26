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
import com.perfect.prodsuit.Model.LocationModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AccountDetailsActivity
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

object LocationRepository {

    val TAG = "LocationRepository"
    val locationSetterGetter = MutableLiveData<LocationModel>()
    private var progressDialog: ProgressDialog? = null

    fun getServicesApiCall(context: Context,ID_LeadGenerateProduct :  String,ID_LeadGenerate :  String): MutableLiveData<LocationModel> {
        getLocation(context,ID_LeadGenerateProduct,ID_LeadGenerate)
        return locationSetterGetter
    }

    private fun getLocation(context: Context,ID_LeadGenerateProduct : String,ID_LeadGenerate :  String) {
        try {
            locationSetterGetter.value = LocationModel("")

            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setMessage("");
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
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

               requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("29"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(ID_LeadGenerateProduct))
                requestObject1.put("ID_LeadGenerate", ProdsuitApplication.encryptStart(ID_LeadGenerate))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"  651 requestObject1     "+requestObject1)
            } catch (e: Exception) {
                progressDialog!!.dismiss()
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getLocationdetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"  652 response     "+response.body())
                        val users = ArrayList<LocationModel>()
                        users.add(LocationModel(response.body()))
                        val msg = users[0].message
                        locationSetterGetter.value = LocationModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        e.printStackTrace()
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
                        Log.e(TAG,"  653 Exception     "+e.toString())
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, ""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    Log.e(TAG,"  654 onFailure     "+t.message)                }
            })
         }
        catch (e: Exception) {
            progressDialog!!.dismiss()
            e.printStackTrace()
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
            Log.e(TAG,"  655 Exception     "+e.toString())
        }
    }

}

