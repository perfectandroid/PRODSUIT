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
import com.perfect.prodsuit.Model.OtherchargeModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object OtherchargeRepository {

    private var progressDialog: ProgressDialog? = null
    val otherchargeSetterGetter = MutableLiveData<OtherchargeModel>()
    val TAG: String = "OtherchargeRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<OtherchargeModel> {
        getMeasureType(context)
        return otherchargeSetterGetter
    }

    private fun getMeasureType(context: Context) {
//        try {
//            OtherchargeSetterGetter.value = OtherchargeModel("")
//            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
//            progressDialog = ProgressDialog(context, R.style.Progress)
//            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//            progressDialog!!.setCancelable(false)
//            progressDialog!!.setIndeterminate(true)
//            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
//                R.drawable.progress))
//            progressDialog!!.show()
//            val client = OkHttpClient.Builder()
//                .sslSocketFactory(Config.getSSLSocketFactory(context))
//                .hostnameVerifier(Config.getHostnameVerifier())
//                .build()
//            val gson = GsonBuilder()
//                .setLenient()
//                .create()
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URLSP.getString("BASE_URL", null))
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build()
//            val apiService = retrofit.create(ApiInterface::class.java!!)
//            val requestObject1 = JSONObject()
//
//            try {
//                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
//                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
//                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
//
//                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("20"))
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                Log.e(TAG,"getDepartment  78   "+requestObject1)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            val body = RequestBody.create(
//                okhttp3.MediaType.parse("application/json; charset=utf-8"),
//                requestObject1.toString()
//            )
//            val call = apiService.getDepartment(body)
//            call.enqueue(object : retrofit2.Callback<String> {
//                override fun onResponse(
//                    call: retrofit2.Call<String>, response:
//                    Response<String>
//                ) {
//                    try {
//                        progressDialog!!.dismiss()
//                        val jObject = JSONObject(response.body())
//                        val leads = ArrayList<OtherchargeModel>()
//                        leads.add(OtherchargeModel(response.body()))
//                        val msg = leads[0].message
//                        OtherchargeSetterGetter.value = OtherchargeModel(msg)
//                    } catch (e: Exception) {
//                        progressDialog!!.dismiss()
//                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
//                    }
//                }
//                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
//                    progressDialog!!.dismiss()
//                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
//                }
//            })
//        }catch (e : Exception){
//            e.printStackTrace()
//            progressDialog!!.dismiss()
//            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
//        }

        try {
            otherchargeSetterGetter.value = OtherchargeModel("")
            var msg = "{\n" +
                    "  \"OtherChargesDetails\": {\n" +
                    "    \"OtherChargesDetailsList\": [\n" +
                    "      {\n" +
                    "        \"ID_Type\": \"1\",\n" +
                    "        \"Type_Name\": \"Packing charge\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Type\": \"2\",\n" +
                    "        \"Type_Name\": \"Transportation charge\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Type\": \"3\",\n" +
                    "        \"Type_Name\": \"Extra Charges\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Type\": \"4\",\n" +
                    "        \"Type_Name\": \"Vehicle special discount\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Type\": \"5\",\n" +
                    "        \"Type_Name\": \"Fuel Charge\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Type\": \"6\",\n" +
                    "        \"Type_Name\": \"Test Charge\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"
            otherchargeSetterGetter.value = OtherchargeModel(msg)
        }catch (e : Exception){

        }
    }

}