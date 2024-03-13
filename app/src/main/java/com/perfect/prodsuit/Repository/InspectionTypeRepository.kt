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
import com.perfect.prodsuit.Model.InspectionTypeModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object InspectionTypeRepository {

    private var progressDialog: ProgressDialog? = null
    val inspectionTypeSetGet = MutableLiveData<InspectionTypeModel>()
    val TAG: String = "InspectionTypeRepository"

    fun getServicesApiCall(context: Context, ReqMode : String): MutableLiveData<InspectionTypeModel> {
        getInspectionType(context,ReqMode)
        return inspectionTypeSetGet
    }

    private fun getInspectionType(context: Context, ReqMode: String) {

//        try {
//            inspectionTypeSetGet.value = InspectionTypeModel("")
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
//                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
//
//                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("20"))
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
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
//                        val leads = ArrayList<InspectionTypeModel>()
//                        leads.add(InspectionTypeModel(response.body()))
//                        val msg = leads[0].message
//                        inspectionTypeSetGet.value = InspectionTypeModel(msg)
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
            inspectionTypeSetGet.value = InspectionTypeModel("")
            var msg ="{\n" +
                    "  \"InspectionTypeDetails\": {\n" +
                    "    \"InspectionTypeList\": [\n" +
                    "      {\n" +
                    "        \"ID_InspectionType\": \"1\",\n" +
                    "        \"InspectionType\": \"Initial\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_InspectionType\": \"2\",\n" +
                    "        \"InspectionType\": \"Follow Up\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_InspectionType\": \"3\",\n" +
                    "        \"InspectionType\": \"Final\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"
            inspectionTypeSetGet.value = InspectionTypeModel(msg)
        }catch (e: Exception){

        }
    }

}