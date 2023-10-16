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
import com.perfect.prodsuit.Model.CommonSearchListModel
import com.perfect.prodsuit.Model.SearchModuleModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object SearchModuleRepository {

    private var progressDialog: ProgressDialog? = null
    val searchModuleSetterGetter = MutableLiveData<SearchModuleModel>()
    val TAG: String = "SearchModuleRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<SearchModuleModel> {
        getSearchModule(context)
        return searchModuleSetterGetter
    }

    private fun getSearchModule(context: Context) {

//        try {
//            searchModuleSetterGetter.value = SearchModuleModel("")
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
//                val FK_UserRoleSP = context.getSharedPreferences(Config.SHARED_PREF41, 0)
//                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
//
////                {"BankKey":"-500","Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","FK_Company":"1","FK_UserGroup":"14","FK_User":"1"}
//
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
//                requestObject1.put("FK_UserGroup", ProdsuitApplication.encryptStart(FK_UserRoleSP.getString("FK_UserRole", null)))
//                requestObject1.put("FK_User", ProdsuitApplication.encryptStart(ID_UserSP.getString("ID_User", null)))
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
//            val call = apiService.getAuthorizationModuleList(body)
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
//                        val leads = ArrayList<SearchModuleModel>()
//                        leads.add(SearchModuleModel(response.body()))
//                        val msg = leads[0].message
//                        searchModuleSetterGetter.value = SearchModuleModel(msg)
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

            var msg = "{\n" +
                    "  \"SearchModuleData\": {\n" +
                    "    \"SearchModuleDataList\": [\n" +
                    "      {\n" +
                    "        \"ID_Module\": \"1\",\n" +
                    "        \"Module\": \"Lead\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Module\": \"2\",\n" +
                    "        \"Module\": \"Service\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Module\": \"3\",\n" +
                    "        \"Module\": \"Pick Up\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            searchModuleSetterGetter.value = SearchModuleModel(msg)
        }catch (e: Exception){

        }
    }

}