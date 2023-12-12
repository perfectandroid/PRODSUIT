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
import com.perfect.prodsuit.Model.SiteVisitCountModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object SiteVisitCountRepository {

    private var progressDialog: ProgressDialog? = null
    val siteVisitCountSetterGetter = MutableLiveData<SiteVisitCountModel>()
    val TAG: String = "SiteVisitCountRepository"

    fun getServicesApiCall(context: Context,ReqMode : String): MutableLiveData<SiteVisitCountModel> {
        getSiteVisitCount(context,ReqMode)
        return siteVisitCountSetterGetter
    }

    private fun getSiteVisitCount(context: Context,ReqMode : String) {
        try {
            siteVisitCountSetterGetter.value = SiteVisitCountModel("")
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
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                //      {"BankKey":"","FK_Company":"1","ReqMode":"2"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))


                Log.e(TAG,"74444  getBranch  "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getProjectSiteVisitCount(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"  114 COUNTS "+response.body())
                        val leads = ArrayList<SiteVisitCountModel>()
                        leads.add(SiteVisitCountModel(response.body()))
                        val msg = leads[0].message
                        siteVisitCountSetterGetter.value = SiteVisitCountModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
            progressDialog!!.dismiss()
        }

//        try {
//            siteVisitCountSetterGetter.value = SiteVisitCountModel("")
//            var msg = "{\n" +
//                    "  \"SiteTabDetails\": {\n" +
//                    "    \"SiteTabDetailsList\": [\n" +
//                    "      {\n" +
//                    "        \"ID_Type\": \"1\",\n" +
//                    "        \"Type_Name\": \"New\",\n" +
//                    "        \"Count\": \"\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Type\": \"2\",\n" +
//                    "        \"Type_Name\": \"To Do\",\n" +
//                    "        \"Count\": \"10\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Type\": \"3\",\n" +
//                    "        \"Type_Name\": \"Await\",\n" +
//                    "        \"Count\": \"15\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//            siteVisitCountSetterGetter.value = SiteVisitCountModel(msg)
//        }catch (e : Exception){
//
//        }
    }
}