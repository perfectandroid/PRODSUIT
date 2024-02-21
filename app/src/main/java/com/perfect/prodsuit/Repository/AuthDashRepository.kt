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
import com.perfect.prodsuit.Model.AuthDashModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object AuthDashRepository {

    private var progressDialog: ProgressDialog? = null
    val authDashSetterGetter = MutableLiveData<AuthDashModel>()
    val TAG: String = "AuthDashRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<AuthDashModel> {
        getAuthDashboard(context)
        return authDashSetterGetter
    }

    private fun getAuthDashboard(context: Context) {

        try {
            authDashSetterGetter.value = AuthDashModel("")
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
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

//                {"BankKey":"-500","ReqMode":"8","Token":"02350582-6179-4097-93F6-17FEFB46CC83","FK_Branch":"3","FK_Company":"1","FK_BranchCodeUser":"3","EntrBy":"SONAKM"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("8"))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))


                Log.e(TAG,"78 getAuthorizationModuleList  "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getAuthorizationDashDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"78 jObject  "+response.body())

                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"78 jObject  "+jObject)
                        val leads = ArrayList<AuthDashModel>()
                        leads.add(AuthDashModel(response.body()))
                        val msg = leads[0].message
                        authDashSetterGetter.value = AuthDashModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"78 jObject  "+e.toString())
                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES+"1", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES+"12", Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }

//        try {
//
//            var msg = "{\n" +
//                    "  \"DashboardDetails\": {\n" +
//                    "    \"DashboardDetailList\": [\n" +
//                    "      {\n" +
//                    "        \"label\": \"Pending\",\n" +
//                    "        \"color\": \"#ad4c68\",\n" +
//                    "        \"count\": \"10\",\n" +
//                    "        \"icon\": \"/Images/Module/Lead.png\",\n" +
//                    "        \"mode\": \"1\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"label\": \"Reject\",\n" +
//                    "        \"color\": \"#4c8dad\",\n" +
//                    "        \"count\": \"15\",\n" +
//                    "        \"icon\": \"/Images/Module/Lead.png\",\n" +
//                    "        \"mode\": \"2\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"label\": \"Correction\",\n" +
//                    "        \"color\": \"#4cad55\",\n" +
//                    "        \"count\": \"18\",\n" +
//                    "        \"icon\": \"/Images/Module/Lead.png\",\n" +
//                    "        \"mode\": \"3\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//
//            authDashSetterGetter.value = AuthDashModel(msg)
//        }catch (e: Exception){
//
//        }

    }

}