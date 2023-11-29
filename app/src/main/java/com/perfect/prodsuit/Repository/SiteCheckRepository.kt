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
import com.perfect.prodsuit.Model.SiteCheckModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object SiteCheckRepository {
    private var progressDialog: ProgressDialog? = null
    val siteCheckSetterGetter = MutableLiveData<SiteCheckModel>()
    val TAG: String = "SiteCheckRepository"

    fun getServicesApiCall(context: Context,ReqMode : String): MutableLiveData<SiteCheckModel> {
        getSiteCheckData(context,ReqMode)
        return siteCheckSetterGetter
    }

    private fun getSiteCheckData(context: Context,ReqMode : String) {
        try {
            siteCheckSetterGetter.value = SiteCheckModel("")
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
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)

               // {"BankKey":"-500","Token":"A80D1ED3-8D06-43FE-874F-A486C92C369A","ReqMode":"119","EntrBy":"SONAKM","FK_Company":"1"}



                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))


                Log.e(TAG,"getDepartment  78   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getProjectcheckDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<SiteCheckModel>()
                        leads.add(SiteCheckModel(response.body()))
                        val msg = leads[0].message
                        siteCheckSetterGetter.value = SiteCheckModel(msg)
                    } catch (e: Exception) {
                        Log.e(TAG,"1000   "+e)
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

//        try {
//            siteCheckSetterGetter.value = SiteCheckModel("")
////            var msg = "{\n" +
////                    "  \"checkDetails\": {\n" +
////                    "    \"checkDetailsList\": [\n" +
////                    "      {\n" +
////                    "        \"ID_Check\": \"000290\",\n" +
////                    "        \"Label_Name\": \"Board\",\n" +
////                    "        \"is_checked\": false\n" +
////                    "      },\n" +
////                    "      {\n" +
////                    "        \"ID_Check\": \"000290\",\n" +
////                    "        \"Label_Name\": \"Board\",\n" +
////                    "        \"is_checked\": false\n" +
////                    "      },\n" +
////                    "      {\n" +
////                    "        \"ID_Check\": \"000290\",\n" +
////                    "        \"Label_Name\": \"Board\",\n" +
////                    "        \"is_checked\": false\n" +
////                    "      }\n" +
////                    "    ],\n" +
////                    "    \"ResponseCode\": \"0\",\n" +
////                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
////                    "  },\n" +
////                    "  \"StatusCode\": 0,\n" +
////                    "  \"EXMessage\": \"Transaction Verified\"\n" +
////                    "}"
//
//            var msg = "{\n" +
//                    "  \"checkDetails\": {\n" +
//                    "    \"checkDetailsList\": [\n" +
//                    "      {\n" +
//                    "        \"ID_Check\": \"000290\",\n" +
//                    "        \"Label_Name\": \"BEDROOM\",\n" +
//                    "        \"is_checked\": false,\n" +
//                    "        \"subArray\": [\n" +
//                    "          {\n" +
//                    "            \"ID_Check\": \"000290\",\n" +
//                    "            \"Label_Name\": \"FURNITURE REQUIREMENTS \",\n" +
//                    "            \"is_checked\": false\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"ID_Check\": \"000290\",\n" +
//                    "            \"Label_Name\": \" SWITCHBOARD \",\n" +
//                    "            \"is_checked\": false\n" +
//                    "          }\n" +
//                    "        ]\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Check\": \"000290\",\n" +
//                    "        \"Label_Name\": \"LIVING ROOM \",\n" +
//                    "        \"is_checked\": false,\n" +
//                    "        \"subArray\": [\n" +
//                    "          {\n" +
//                    "            \"ID_Check\": \"000290\",\n" +
//                    "            \"Label_Name\": \"LENGTH AND WIDTH \",\n" +
//                    "            \"is_checked\": false\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"ID_Check\": \"000290\",\n" +
//                    "            \"Label_Name\": \"CEILING HEIGHT 1\",\n" +
//                    "            \"is_checked\": false\n" +
//                    "          }\n" +
//                    "        ]\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Check\": \"000290\",\n" +
//                    "        \"Label_Name\": \" FURNITURE \",\n" +
//                    "        \"is_checked\": false,\n" +
//                    "        \"subArray\": [\n" +
//                    "          {\n" +
//                    "            \"ID_Check\": \"000290\",\n" +
//                    "            \"Label_Name\": \" ORIENTATION \",\n" +
//                    "            \"is_checked\": false\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"ID_Check\": \"000290\",\n" +
//                    "            \"Label_Name\": \" OTHERS ANY \",\n" +
//                    "            \"is_checked\": false\n" +
//                    "          }\n" +
//                    "        ]\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//            siteCheckSetterGetter.value = SiteCheckModel(msg)
//        }catch (e : Exception){
//
//        }
    }
}