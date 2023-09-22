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
import com.perfect.prodsuit.Model.CorrectionSplitupModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object CorrectionSplitupRepository {

    val correctionSplitupSetterGetter = MutableLiveData<CorrectionSplitupModel>()
    val TAG: String = "CorrectionSplitupRepository"
    private var progressDialog: ProgressDialog? = null

    fun getServicesApiCall(context: Context, TransMode : String): MutableLiveData<CorrectionSplitupModel> {
        getCorrectionSplitup(context, TransMode)
        return correctionSplitupSetterGetter
    }

    private fun getCorrectionSplitup(context: Context, TransMode: String) {
//        try {
//
//            var strValu = "{\n" +
//                    "  \"ExistCustomerDetails\": {\n" +
//                    "    \"ExistCustomerDetailList\": [\n" +
//                    "      {\n" +
//                    "        \"LeadNo\": \"10249\",\n" +
//                    "        \"Customer\": \"ChackoA\",\n" +
//                    "        \"Mobile\": \"9847112345\",\n" +
//                    "        \"LeadName\": \"Lead Generation\",\n" +
//                    "        \"AssignedTo\": \"Chandrasekaran\",\n" +
//                    "        \"FollowUpDate\": \"12/09/2023\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"LeadNo\": \"10248\",\n" +
//                    "        \"Customer\": \"ChackoA\",\n" +
//                    "        \"Mobile\": \"9847112345\",\n" +
//                    "        \"LeadName\": \"Lead Generation\",\n" +
//                    "        \"AssignedTo\": \"bemp\",\n" +
//                    "        \"FollowUpDate\": \"13/09/2023\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"LeadNo\": \"10247\",\n" +
//                    "        \"Customer\": \"ChackoA\",\n" +
//                    "        \"Mobile\": \"9847112345\",\n" +
//                    "        \"LeadName\": \"Lead Generation\",\n" +
//                    "        \"AssignedTo\": \"B ELIXIH\",\n" +
//                    "        \"FollowUpDate\": \"11/09/2023\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//
//            correctionSplitupSetterGetter.value = CorrectionSplitupModel(strValu)
//
//        }catch (e : Exception){
//
//        }

        try {
            correctionSplitupSetterGetter.value = CorrectionSplitupModel("")
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
                val FK_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

//                {
//                    "BankKey": "-500",
//                    "Token": "F5517387-B815-4DCC-B2CC-E0A2F3160E22",
//                    "FK_Company": "1",
//                    "FK_User": "72",
//                    "Module": "LFLG"
//                }

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_User", ProdsuitApplication.encryptStart(FK_UserSP.getString("ID_User", null)))
                requestObject1.put("Module", ProdsuitApplication.encryptStart(TransMode))


                Log.e(TAG,"126   getAuthorizationCorrectionDetailsList  "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getAuthorizationCorrectionDetailsList(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<CorrectionSplitupModel>()
                        leads.add(CorrectionSplitupModel(response.body()))
                        val msg = leads[0].message
                        correctionSplitupSetterGetter.value = CorrectionSplitupModel(msg)
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
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }
    }

}