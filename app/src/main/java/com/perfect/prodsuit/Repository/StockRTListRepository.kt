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
import com.perfect.prodsuit.Model.StockRTListModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object StockRTListRepository {

    private var progressDialog: ProgressDialog? = null
    val stockRTListSetterGetter = MutableLiveData<StockRTListModel>()
    val TAG: String = "StockRTListRepository"

    fun getServicesApiCall(context: Context, TransMode : String, Detailed : String): MutableLiveData<StockRTListModel> {
        getStockRTList(context, TransMode,Detailed)
        return stockRTListSetterGetter
    }

    private fun getStockRTList(context: Context, TransMode: String, Detailed: String) {

        try {
            stockRTListSetterGetter.value = StockRTListModel("")
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

//                {""BankKey"":""-500"",""FK_Company"":""1"",""Token"":""F5517387-B815-4DCC-B2CC-E0A2F3160E22"",""EntrBy"":""SONAKM"",""TransMode"":""INTR"",
//                ""FK_BranchCodeUser"":""3"",""Detailed"":""0""}


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(TransMode))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("Detailed", ProdsuitApplication.encryptStart(Detailed))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                Log.e(TAG,"requestObject1   8100   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getStockRequestList(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val country = ArrayList<StockRTListModel>()
                        country.add(StockRTListModel(response.body()))
                        val msg = country[0].message
                        stockRTListSetterGetter.value = StockRTListModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(
                            context,
                            ""+ Config.SOME_TECHNICAL_ISSUES,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(
                        context,
                        ""+ Config.SOME_TECHNICAL_ISSUES,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(
                context,
                ""+ Config.SOME_TECHNICAL_ISSUES,
                Toast.LENGTH_LONG
            ).show()
        }


//        try {
//
//            var msg = "{\n" +
//                    "  \"StockRTDetails\": {\n" +
//                    "    \"StockRTList\": [\n" +
//                    "      {\n" +
//                    "        \"Date\": \"04/05/2023\",\n" +
//                    "        \"Branch\": \"Perfect Software Solution\",\n" +
//                    "        \"DepartmentFrom\": \"Service\",\n" +
//                    "        \"DepartmentTo\": \"Head Office Chalappuram\",\n" +
//                    "        \"EmployeesFrom\": \"VYSHAKH PN\",\n" +
//                    "        \"EmployeeTo\": \"Sona\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"Date\": \"04/05/2023\",\n" +
//                    "        \"Branch\": \"Perfect Software Solution\",\n" +
//                    "        \"DepartmentFrom\": \"Sales\",\n" +
//                    "        \"DepartmentTo\": \"Head Office Chalappuram\",\n" +
//                    "        \"EmployeesFrom\": \"Sona\",\n" +
//                    "        \"EmployeeTo\": \"Shan\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"Date\": \"04/05/2023\",\n" +
//                    "        \"Branch\": \"Perfect Software Solution\",\n" +
//                    "        \"DepartmentFrom\": \"Support\",\n" +
//                    "        \"DepartmentTo\": \"Head Office Chalappuram\",\n" +
//                    "        \"EmployeesFrom\": \"Anvin\",\n" +
//                    "        \"EmployeeTo\": \"Shan\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"Date\": \"04/05/2023\",\n" +
//                    "        \"Branch\": \"Perfect Software Solution\",\n" +
//                    "        \"DepartmentFrom\": \"Sales\",\n" +
//                    "        \"DepartmentTo\": \"Head Office Chalappuram\",\n" +
//                    "        \"EmployeesFrom\": \"Shi\",\n" +
//                    "        \"EmployeeTo\": \"Shan\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//
//            stockRTListSetterGetter.value = StockRTListModel(msg)
//        }catch (e: Exception){
//
//        }
    }
}