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
import com.perfect.prodsuit.Model.AuthorizationMixedModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object AuthorizationMixedRepository {

    private var progressDialog: ProgressDialog? = null
    val authorizationMixedSetterGetter = MutableLiveData<AuthorizationMixedModel>()
    val TAG: String = "AuthorizationMixedRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<AuthorizationMixedModel> {
        getAuthorizationMixed(context)
        return authorizationMixedSetterGetter
    }
//
    private fun getAuthorizationMixed(context: Context) {
//
//        try {
//            authorizationMixedSetterGetter.value = AuthorizationMixedModel("")
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
//
//                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
//                val FK_UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
//                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
//
//
//
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(FK_UserCodeSP.getString("UserCode", null)))
//
//
//                Log.e(TAG,"78 getAuthorizationModuleList  "+requestObject1)
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            val body = RequestBody.create(
//                okhttp3.MediaType.parse("application/json; charset=utf-8"),
//                requestObject1.toString()
//            )
//            val call = apiService.getAuthorizationDataList(body)
//            call.enqueue(object : retrofit2.Callback<String> {
//                override fun onResponse(
//                    call: retrofit2.Call<String>, response:
//                    Response<String>
//                ) {
//                    try {
//                        progressDialog!!.dismiss()
//                        Log.e(TAG,"78 jObject  "+response.body())
//
//                        val jObject = JSONObject(response.body())
//                        Log.e(TAG,"78 jObject  "+jObject)
//                        val leads = ArrayList<AuthorizationMixedModel>()
//                        leads.add(AuthorizationMixedModel(response.body()))
//                        val msg = leads[0].message
//                        authorizationMixedSetterGetter.value = AuthorizationMixedModel(msg)
//                    } catch (e: Exception) {
//                        progressDialog!!.dismiss()
//                        Log.e(TAG,"78 jObject  "+e.toString())
//                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES+"1", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
//                    progressDialog!!.dismiss()
//                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES+"12", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }catch (e : Exception){
//            e.printStackTrace()
//            progressDialog!!.dismiss()
//            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
//        }

        try {
////            var msg = "{\n" +
//
////                    "  \"AuthorizationList\": {\n" +
////                    "    \"ListData\": [\n" +
////                    "      {\n" +
////                    "        \"SlNo\": 1,\n" +
////                    "        \"ID_FIELD\": 99,\n" +
////                    "        \"Action\": \"Lead Generate\",\n" +
////                    "        \"TransactionNo\": \"LD-000185\",\n" +
////                    "        \"Date\": \"12/10/2023\",\n" +
////                    "        \"drank\": 1,\n" +
////                    "        \"EnteredBy\": \"VYSHAKH PN \",\n" +
////                    "        \"EnteredOn\": \"12/10/2023\",\n" +
////                    "        \"TotalCount\": 1\n" +
////                    "      },\n" +
////                    "      {\n" +
////                    "        \"SlNo\": 1,\n" +
////                    "        \"ID_FIELD\": 99,\n" +
////                    "        \"Action\": \"Lead Generate\",\n" +
////                    "        \"TransactionNo\": \"LD-000185\",\n" +
////                    "        \"Date\": \"12/10/2023\",\n" +
////                    "        \"drank\": 1,\n" +
////                    "        \"EnteredBy\": \"VYSHAKH PN \",\n" +
////                    "        \"EnteredOn\": \"12/10/2023\",\n" +
////                    "        \"TotalCount\": 1\n" +
////                    "      }\n" +
////                    "    ],\n" +
////                    "    \"ResponseCode\": \"0\",\n" +
////                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
////                    "  },\n" +
////                    "  \"StatusCode\": 0,\n" +
////                    "  \"EXMessage\": \"Transaction Verified\"\n" +
////                    "}"
//
//            authorizationMixedSetterGetter.value = AuthorizationMixedModel()
//
            var msg = "{\n" +
                    "  \"AuthorizationList\": {\n" +
                    "    \"ListData\": [\n" +
                    "      {\n" +
                    "        \"Lead\": [\n" +
                    "          {\n" +
                    "            \"SlNo\": 2,\n" +
                    "            \"ID_FIELD\": \"99\",\n" +
                    "            \"Action\": \"Lead Generate\",\n" +
                    "            \"TransactionNo\": \"LD-000185\",\n" +
                    "            \"Date\": \"12/10/2023\",\n" +
                    "            \"drank\": \"1\",\n" +
                    "            \"EnteredBy\": \"VYSHAKH PN\",\n" +
                    "            \"EnteredOn\": \"12/10/2023\",\n" +
                    "            \"TotalCount\": \"1\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"SlNo\": 2,\n" +
                    "            \"ID_FIELD\": \"99\",\n" +
                    "            \"Action\": \"Lead Generate\",\n" +
                    "            \"TransactionNo\": \"LD-000185\",\n" +
                    "            \"Date\": \"12/10/2023\",\n" +
                    "            \"drank\": \"1\",\n" +
                    "            \"EnteredBy\": \"VYSHAKH PN\",\n" +
                    "            \"EnteredOn\": \"12/10/2023\",\n" +
                    "            \"TotalCount\": \"1\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Project\": [\n" +
                    "          {\n" +
                    "            \"SlNo\": 2,\n" +
                    "            \"ID_FIELD\": \"99\",\n" +
                    "            \"Action\": \"Lead Generate\",\n" +
                    "            \"TransactionNo\": \"LD-000185\",\n" +
                    "            \"Date\": \"12/10/2023\",\n" +
                    "            \"drank\": \"1\",\n" +
                    "            \"EnteredBy\": \"VYSHAKH PN\",\n" +
                    "            \"EnteredOn\": \"12/10/2023\",\n" +
                    "            \"TotalCount\": \"1\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"SlNo\": 2,\n" +
                    "            \"ID_FIELD\": \"99\",\n" +
                    "            \"Action\": \"Lead Generate\",\n" +
                    "            \"TransactionNo\": \"LD-000185\",\n" +
                    "            \"Date\": \"12/10/2023\",\n" +
                    "            \"drank\": \"1\",\n" +
                    "            \"EnteredBy\": \"VYSHAKH PN\",\n" +
                    "            \"EnteredOn\": \"12/10/2023\",\n" +
                    "            \"TotalCount\": \"1\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Service\": [\n" +
                    "          {\n" +
                    "            \"SlNo\": 2,\n" +
                    "            \"ID_FIELD\": \"99\",\n" +
                    "            \"Action\": \"Lead Generate\",\n" +
                    "            \"TransactionNo\": \"LD-000185\",\n" +
                    "            \"Date\": \"12/10/2023\",\n" +
                    "            \"drank\": \"1\",\n" +
                    "            \"EnteredBy\": \"VYSHAKH PN\",\n" +
                    "            \"EnteredOn\": \"12/10/2023\",\n" +
                    "            \"TotalCount\": \"1\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"SlNo\": 2,\n" +
                    "            \"ID_FIELD\": \"99\",\n" +
                    "            \"Action\": \"Lead Generate\",\n" +
                    "            \"TransactionNo\": \"LD-000185\",\n" +
                    "            \"Date\": \"12/10/2023\",\n" +
                    "            \"drank\": \"1\",\n" +
                    "            \"EnteredBy\": \"VYSHAKH PN\",\n" +
                    "            \"EnteredOn\": \"12/10/2023\",\n" +
                    "            \"TotalCount\": \"1\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            authorizationMixedSetterGetter.value = AuthorizationMixedModel(msg)
        }catch (e: Exception){

        }
    }
}