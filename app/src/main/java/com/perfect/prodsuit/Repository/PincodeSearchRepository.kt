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
import com.perfect.prodsuit.Model.PincodeSearchModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object PincodeSearchRepository {

    private var progressDialog: ProgressDialog? = null
    val pincodeSetterGetter = MutableLiveData<PincodeSearchModel>()
    val TAG: String = "PincodeSearchRepository"

    fun getServicesApiCall(context: Context,strPincode : String): MutableLiveData<PincodeSearchModel> {
        getPinCode(context,strPincode)
        Log.e(TAG,"pincodeSetterGetter   3000    "+pincodeSetterGetter.value)
        return pincodeSetterGetter
    }

    private fun getPinCode(context: Context,strPincode : String) {

        Log.e("TAG","getPinCode  ")
        try {
            pincodeSetterGetter.value = PincodeSearchModel("")
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

//                "ReqMode":"33",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "Pincode":"641231"

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("33"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("Pincode", ProdsuitApplication.encryptStart(strPincode))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))



                Log.e(TAG,"strPincode   79   "+strPincode)
                Log.e(TAG,"requestObject1   79   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.i("resoinsepincode","pin body="+requestObject1.toString())
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getPincodeDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"response  95   "+response.body())
                        val jObject = JSONObject(response.body())
                        val pincode = ArrayList<PincodeSearchModel>()
                        Log.e(TAG,"pincode  95   "+pincode)
                        pincode.add(PincodeSearchModel(response.body()))
                        val msg = pincode[0].message
                        pincodeSetterGetter.value = PincodeSearchModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context, ""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
                            .show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, ""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
                        .show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context, ""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
                .show()
        }
    }
}