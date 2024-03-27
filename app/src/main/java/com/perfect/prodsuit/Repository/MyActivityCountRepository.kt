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
import com.perfect.prodsuit.Model.MyActivityCountModel
import com.perfect.prodsuit.R
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object MyActivityCountRepository {

    private var progressDialog: ProgressDialog? = null
    val myActivityCountSetGet = MutableLiveData<MyActivityCountModel>()
    val TAG: String = "MyActivityCountRepository"

    fun getServicesApiCall(context: Context,ReqMode : String, IdFliter : String): MutableLiveData<MyActivityCountModel> {
        getMyActivityCount(context,ReqMode,IdFliter)
        return myActivityCountSetGet
    }

    private fun getMyActivityCount(context: Context,ReqMode : String, IdFliter : String) {


        try {
            myActivityCountSetGet.value = MyActivityCountModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
                R.drawable.progress))
            progressDialog!!.show()
            val client = okhttp3.OkHttpClient.Builder()
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


             //   {"ReqMode":"1","BankKey":"-500","FK_Company":"1","FK_Employee":"10044","ID_User":"10072","ID_Branch":"3","Token":"F86C44BB-6F23-4BC1-8E00-5819D597785B"}

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("ID_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("IdFliter", ProdsuitApplication.encryptStart(IdFliter))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                Log.e(TAG,"MyActivityCount  8222   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getApiMyActivityCount(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<MyActivityCountModel>()
                        leads.add(MyActivityCountModel(response.body()))
                        val msg = leads[0].message
                        myActivityCountSetGet.value = MyActivityCountModel(msg)
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
//            myActivityCountSetGet.value = MyActivityCountModel("")
//            var msg = "{\n" +
//                    "  \"MyActivitysCountDetails\": {\n" +
//                    "    \"MyActivitysCountList\": [\n" +
//                    "      {\n" +
//                    "        \"ID_Status\": 4,\n" +
//                    "        \"StatusName\": \"Today's\",\n" +
//                    "        \"count\": 12\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Status\": 2,\n" +
//                    "        \"StatusName\": \"Pending\",\n" +
//                    "        \"count\": 5\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Status\": 3,\n" +
//                    "        \"StatusName\": \"Upcoming\",\n" +
//                    "        \"count\": 50\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Status\": 1,\n" +
//                    "        \"StatusName\": \"Completed\",\n" +
//                    "        \"count\": 15\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//            myActivityCountSetGet.value = MyActivityCountModel(msg)
//        }catch (e : Exception){
//
//        }

    }



}