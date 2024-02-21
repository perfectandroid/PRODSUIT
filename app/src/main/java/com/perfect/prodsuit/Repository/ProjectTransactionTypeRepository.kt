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
import com.perfect.prodsuit.Model.ProjectTransactionTypeModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ProjectTransactionTypeRepository {

    private var progressDialog: ProgressDialog? = null
    val projectTransactionTypeSetGet = MutableLiveData<ProjectTransactionTypeModel>()
    val TAG: String = "ProjectTransactionTypeRepository"

    fun getServicesApiCall(context: Context,ReqMode : String): MutableLiveData<ProjectTransactionTypeModel> {
        getProjectTransactionType(context,ReqMode)
        return projectTransactionTypeSetGet
    }

    private fun getProjectTransactionType(context: Context,ReqMode : String) {

        try {
            projectTransactionTypeSetGet.value= ProjectTransactionTypeModel("")
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

                // {"ReqMode":"127","BankKey":"-500","Token":"F513239B-E6F7-4CB9-AFF9-58C54FEE3CE5","FK_Company":"1"}


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                Log.e(TAG,"requestObject1   78000    "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getTransactionTypeDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"101    "+response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ProjectTransactionTypeModel>()
                        leads.add(ProjectTransactionTypeModel(response.body()))
                        val msg = leads[0].message
                        projectTransactionTypeSetGet.value = ProjectTransactionTypeModel(msg)
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

//        try {
//            Log.e(TAG,"366666  ")
//            projectTransactionTypeSetGet.value = ProjectTransactionTypeModel("")
//
//            val msg = "{\n" +
//                    "  \"TransTypeDetails\": {\n" +
//                    "    \"TransTypeList\": [\n" +
//                    "      {\n" +
//                    "        \"ID_TransType\": \"1\",\n" +
//                    "        \"TransType\": \"Fund Allocation\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_TransType\": \"2\",\n" +
//                    "        \"TransType\": \"Fund Spend\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_TransType\": \"3\",\n" +
//                    "        \"TransType\": \"Fund Return\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_TransType\": \"4\",\n" +
//                    "        \"TransType\": \"Project Transaction\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_TransType\": \"5\",\n" +
//                    "        \"TransType\": \"Petty Cash Inward\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_TransType\": \"6\",\n" +
//                    "        \"TransType\": \"Petty Cash Outward\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//            projectTransactionTypeSetGet.value = ProjectTransactionTypeModel(msg)
//        }catch (e : Exception){
//
//        }



    }

}