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
import com.perfect.prodsuit.Model.SiteVisitDocUploadModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object SiteVisitDocUploadRepository {

    private var progressDialog: ProgressDialog? = null
    val siteVisitDocUploadSetterGetter = MutableLiveData<SiteVisitDocUploadModel>()
    val TAG: String = "SiteVisitDocUploadRepository"

    fun getServicesApiCall(context: Context,TransMode : String, FK_SiteVisit : String, ProjImageName : String,
                           ProjImageType : String, ProjImageDescription : String, ProjImage : String): MutableLiveData<SiteVisitDocUploadModel> {
        getSiteVisitDocUpload(context, TransMode,FK_SiteVisit,ProjImageName,ProjImageType, ProjImageDescription,ProjImage)
        return siteVisitDocUploadSetterGetter
    }

    private fun getSiteVisitDocUpload(context: Context,TransMode : String, FK_SiteVisit : String, ProjImageName : String,
                                      ProjImageType : String, ProjImageDescription : String, ProjImage : String) {
        try {
            siteVisitDocUploadSetterGetter.value = SiteVisitDocUploadModel("")
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
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36,0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

//              {"TransMode":"PRSV","FK_Company":"1","FK_BranchCodeUser":"2","EntrBy":"sreej","FK_SiteVisit":"37","ProjImageName":"1.jpg","ProjImageType":".jpg",
//              "ProjImageDescription":"Test",
//
//"ProjImage":"iVBORw0KGgoAAAANSUhEUgAAAfQAAAF3CAIAAADckC6rAAAAAXNSR0IArs4c6QAAAANzQklUCAgI2"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(TransMode))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))

                requestObject1.put("FK_SiteVisit", ProdsuitApplication.encryptStart(FK_SiteVisit))
                requestObject1.put("ProjImageName", ProdsuitApplication.encryptStart(ProjImageName))
                requestObject1.put("ProjImageType", ProdsuitApplication.encryptStart(ProjImageType))
                requestObject1.put("ProjImageDescription", ProdsuitApplication.encryptStart(ProjImageDescription))
                requestObject1.put("ProjImage", ProjImage)


                Log.e(TAG,"DownloadImage  900001   "+requestObject1)
                Log.e(TAG,"ProjImage  900002   "+ProjImage)
                Log.e(TAG,"ProjImage  900003   "+ProjImage)
                Log.e(TAG,"ProjImage  900004   "+ProjImage)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG,"126543    "+e.toString())
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.saveDownloadImage(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<SiteVisitDocUploadModel>()
                        leads.add(SiteVisitDocUploadModel(response.body()))
                        val msg = leads[0].message
                        siteVisitDocUploadSetterGetter.value = SiteVisitDocUploadModel(msg)
                    } catch (e: Exception) {
                        Log.e(TAG,"126541    "+e.toString())
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    Log.e(TAG,"126544    "+t.message)
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Log.e(TAG,"126542    "+e.toString())
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }
    }
}