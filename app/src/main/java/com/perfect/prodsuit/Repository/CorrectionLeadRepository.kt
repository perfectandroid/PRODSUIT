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
import com.perfect.prodsuit.Model.CorrectionLeadModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object CorrectionLeadRepository {

    private var progressDialog: ProgressDialog? = null
    val correctionLeadSetterGetter = MutableLiveData<CorrectionLeadModel>()
    val TAG: String = "CorrectionLeadRepository"

    fun getServicesApiCall(context: Context,TransMode: String,FK_TransMaster: String,ID_AuthorizationData: String): MutableLiveData<CorrectionLeadModel> {
        getCorrectionLead(context,TransMode,FK_TransMaster,ID_AuthorizationData)
        return correctionLeadSetterGetter
    }

    private fun getCorrectionLead(context: Context,TransMode: String,FK_TransMaster: String,ID_AuthorizationData: String) {

//        try {
//
//            var strValu = "{\n" +
//                    "  \"CorrectionDetails\": {\n" +
//                    "    \"CorrectionDetailList\": [\n" +
//                    "      {\n" +
//                    "        \"ID_Category\": \"1\",\n" +
//                    "        \"Category\": \"Medium Solar Panel\",\n" +
//                    "        \"ID_Product\": \"10\",\n" +
//                    "        \"Product\": \"Amaze\",\n" +
//                    "        \"MRP\": \"200.00\",\n" +
//                    "        \"OfferPrice\": \"150.00\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Category\": \"2\",\n" +
//                    "        \"Category\": \"Mega Solar Panel\",\n" +
//                    "        \"ID_Product\": \"11\",\n" +
//                    "        \"Product\": \"Product 1\",\n" +
//                    "        \"MRP\": \"0.00\",\n" +
//                    "        \"OfferPrice\": \"210.00\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Category\": \"10249\",\n" +
//                    "        \"Category\": \"Medium Solar Panel\",\n" +
//                    "        \"ID_Product\": \"12\",\n" +
//                    "        \"Product\": \"Product 2\",\n" +
//                    "        \"MRP\": \"150.22\",\n" +
//                    "        \"OfferPrice\": \"99.25\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//
//            correctionLeadSetterGetter.value = CorrectionLeadModel(strValu)
//
//        }catch (e : Exception){
//
//        }

//        Log.e("TAG","getCorrectionLead  ")
        try {
            correctionLeadSetterGetter.value = CorrectionLeadModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
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
                val EnterBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EnterBySP.getString("UserCode", null)))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(TransMode))
                requestObject1.put("ID_LeadGenerate", ProdsuitApplication.encryptStart(FK_TransMaster))
                requestObject1.put("FK_AuthorizationData", ProdsuitApplication.encryptStart(ID_AuthorizationData))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"requestObject1   4568   "+requestObject1)
                Log.e(TAG,"TransMode   4568   "+TransMode)
                Log.e(TAG,"ID_LeadGenerate   4568   "+FK_TransMaster)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getAuthorizationCorrectionLeadDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val country = ArrayList<CorrectionLeadModel>()
                        country.add(CorrectionLeadModel(response.body()))
                        val msg = country[0].message
                        correctionLeadSetterGetter.value = CorrectionLeadModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG).show()
        }

    }
}