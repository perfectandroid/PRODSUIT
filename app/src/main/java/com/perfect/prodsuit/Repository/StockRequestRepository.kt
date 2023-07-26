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
import com.perfect.prodsuit.Model.StockRequestModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object StockRequestRepository {

    private var progressDialog: ProgressDialog? = null
    val stockRequestSetterGetter = MutableLiveData<StockRequestModel>()
    val TAG: String = "StockRequestRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<StockRequestModel> {
        getStockRequest(context)
        return stockRequestSetterGetter
    }

    private fun getStockRequest(context: Context) {

        try {
            var sss= "{\n" +
                    "  \"StockRequestDetails\": {\n" +
                    "    \"StockRequestList\": [\n" +
                    "      {\n" +
                    "        \"TicketDate\": \"04/05/2023\",\n" +
                    "        \"Branch\": \"Perfect Software Solution\",\n" +
                    "        \"Department\": \"Sales\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"EmployeeTo\": \"Shan\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketDate\": \"05/06/2023\",\n" +
                    "        \"Branch\": \"Head Office Chalappuram\",\n" +
                    "        \"Department\": \"Marketing\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"EmployeeTo\": \"Sona\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketDate\": \"06/07/2023\",\n" +
                    "        \"Branch\": \"Perfect Software Solution\",\n" +
                    "        \"Department\": \"Software Engineer\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"EmployeeTo\": \"Shi\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketDate\": \"07/08/2023\",\n" +
                    "        \"Branch\": \"Head Office Chalappuram\",\n" +
                    "        \"Department\": \"Service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"EmployeeTo\": \"Anvin\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            val jObject = JSONObject(sss)
            stockRequestSetterGetter.value = StockRequestModel(sss)
        }catch (e: Exception){

        }

//        try {
//            stockRequestSetterGetter.value = StockRequestModel("")
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
//            try {
//
////                {"BankKey":"-500","Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","ReqMode":"22","FK_Company":"1","ID_BranchType":"2"}
//
//                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
//                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
//                val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
//
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("22"))
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
//                requestObject1.put("ID_BranchType", ProdsuitApplication.encryptStart("2"))
//
//                Log.e(TAG,"78  getBranchInventory    "+requestObject1)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            val body = RequestBody.create(
//                okhttp3.MediaType.parse("application/json; charset=utf-8"),
//                requestObject1.toString()
//            )
//            val call = apiService.getBranchInventory(body)
//            call.enqueue(object : retrofit2.Callback<String> {
//                override fun onResponse(
//                    call: retrofit2.Call<String>, response:
//                    Response<String>
//                ) {
//                    try {
//                        progressDialog!!.dismiss()
//                        val jObject = JSONObject(response.body())
//                        Log.e("Branchresponse",response.body())
//                        val leads = ArrayList<StockRequestModel>()
//                        leads.add(StockRequestModel(response.body()))
//                        val msg = leads[0].message
//                        stockRequestSetterGetter.value = StockRequestModel(msg)
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
//            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
//            progressDialog!!.dismiss()
//        }
    }
}