package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.SaveSiteVisitModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.SiteVisitActivity
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object SaveSiteVisitRepository {

    private var progressDialog: ProgressDialog? = null
    val saveSiteVisitSetterGetter = MutableLiveData<SaveSiteVisitModel>()
    val TAG: String = "SaveSiteVisitRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<SaveSiteVisitModel> {
        saveSiteVisit(context)
        return saveSiteVisitSetterGetter
    }

    private fun saveSiteVisit(context: Context) {

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

//                "ReqMode":"31",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "TrnsDate":"2022-04-17 03:30:00"
//                "ID_RiskType":"1"
//                "CustomerNote":"Test",
//                "EmployeeNote":"Test",
//                "Id_Status":"1",
//                "CusMensDate":"2022-04-17",
//                "LocLatitude":"75.12364",
//                "LocLongitude":"75.1256360,
//                "LocationLandMark1":"djfdfjkf",
//                "LocationLandMark2":"jgniurhgiurehfu",
//                "ID_LeadGenerateProduct":1
//                "ID_FollowUpType":"1"

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("31"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))

                requestObject1.put("TrnsDate", ProdsuitApplication.encryptStart(SiteVisitActivity.strDateTime))
                requestObject1.put("ID_RiskType", ProdsuitApplication.encryptStart(SiteVisitActivity.strRiskType))
                requestObject1.put("CustomerNote", ProdsuitApplication.encryptStart(SiteVisitActivity.strCustomerNote))
                requestObject1.put("EmployeeNote", ProdsuitApplication.encryptStart(SiteVisitActivity.strAgentNote))
                requestObject1.put("Id_Status", ProdsuitApplication.encryptStart(SiteVisitActivity.ID_Status))
                requestObject1.put("CusMensDate", ProdsuitApplication.encryptStart(SiteVisitActivity.strMentionDate))
                requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(SiteVisitActivity.strLatitude))
                requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(SiteVisitActivity.strLongitude))
                requestObject1.put("LocationLandMark1", ProdsuitApplication.encryptStart(SiteVisitActivity.encode1))
                requestObject1.put("LocationLandMark2", ProdsuitApplication.encryptStart(SiteVisitActivity.encode2))
                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(SiteVisitActivity.ID_LeadGenerateProduct))
                requestObject1.put("ID_FollowUpType", ProdsuitApplication.encryptStart(SiteVisitActivity.ID_ActionType))

                Log.e(TAG,"requestObject1   102   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.updateLeadGenerateAction(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        Log.e(TAG,"onFailure  118   "+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<SaveSiteVisitModel>()
                        leads.add(SaveSiteVisitModel(response.body()))
                        val msg = leads[0].message
                        saveSiteVisitSetterGetter.value = SaveSiteVisitModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Log.e(TAG,"Exception  1401   "+e.toString())
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Log.e(TAG,"onFailure  1402   "+t.message)
                }
            })



        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Log.e(TAG,"Exception  1403   "+e.toString())
        }
    }
}