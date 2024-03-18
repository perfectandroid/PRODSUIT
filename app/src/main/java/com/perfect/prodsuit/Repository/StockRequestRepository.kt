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

    fun getServicesApiCall(context: Context,FK_BranchFrom : String,FK_DepartmentFrom: String,FK_EmployeeFrom: String,FK_BranchTo: String,FK_DepartmentTo: String,FK_EmployeeTo: String): MutableLiveData<StockRequestModel> {
        getStockRequest(context,FK_BranchFrom,FK_DepartmentFrom,FK_EmployeeFrom,FK_BranchTo,FK_DepartmentTo,FK_EmployeeTo)
        return stockRequestSetterGetter
    }

    private fun getStockRequest(context: Context,FK_BranchFrom : String,FK_DepartmentFrom: String,FK_EmployeeFrom: String,FK_BranchTo: String,FK_DepartmentTo: String,FK_EmployeeTo: String) {


                try {
                    stockRequestSetterGetter.value = StockRequestModel("")
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

        //               {"BankKey":"-500","Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","FK_Company":"1","TransMode":"INTR","FK_BranchCodeUser":"3","FK_DepartmentFrom":"2",
            //               "FK_DepartmentTo":"0","FK_BranchFrom":"3","FK_BranchTo":"0","FK_EmployeeFrom":"0","FK_EmployeeTo":"0"}

                        val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                        val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                        val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                        val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                        val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                        val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)


                        requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                        requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                        requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                        requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                        requestObject1.put("TransMode", ProdsuitApplication.encryptStart("INTR"))
                        requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                        requestObject1.put("FK_DepartmentFrom", ProdsuitApplication.encryptStart(FK_DepartmentFrom))
                        requestObject1.put("FK_DepartmentTo", ProdsuitApplication.encryptStart(FK_DepartmentTo))
                        requestObject1.put("FK_BranchFrom", ProdsuitApplication.encryptStart(FK_BranchFrom))
                        requestObject1.put("FK_BranchTo", ProdsuitApplication.encryptStart(FK_BranchTo))
                        requestObject1.put("FK_EmployeeFrom", ProdsuitApplication.encryptStart(FK_EmployeeFrom))
                        requestObject1.put("FK_EmployeeTo", ProdsuitApplication.encryptStart(FK_EmployeeTo))
                        requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                        Log.e(TAG,"8600  getStockRequestListInTransfer    "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getStockRequestListInTransfer(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e("Branchresponse",response.body())
                        val leads = ArrayList<StockRequestModel>()
                        leads.add(StockRequestModel(response.body()))
                        val msg = leads[0].message
                        stockRequestSetterGetter.value = StockRequestModel(msg)
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
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
            progressDialog!!.dismiss()
        }

//        try {
//            var sss= "{\n" +
//                    "  \"StockRequestDetails\": {\n" +
//                    "    \"StockRequestList\": [\n" +
//                    "      {\n" +
//                    "        \"TicketDate\": \"04/05/2023\",\n" +
//                    "        \"Branch\": \"Perfect Software Solution\",\n" +
//                    "        \"Department\": \"Sales\",\n" +
//                    "        \"Employees\": \"VYSHAKH PN\",\n" +
//                    "        \"EmployeeTo\": \"Shan\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"TicketDate\": \"05/06/2023\",\n" +
//                    "        \"Branch\": \"Head Office Chalappuram\",\n" +
//                    "        \"Department\": \"Marketing\",\n" +
//                    "        \"Employees\": \"VYSHAKH PN\",\n" +
//                    "        \"EmployeeTo\": \"Sona\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"TicketDate\": \"06/07/2023\",\n" +
//                    "        \"Branch\": \"Perfect Software Solution\",\n" +
//                    "        \"Department\": \"Software Engineer\",\n" +
//                    "        \"Employees\": \"VYSHAKH PN\",\n" +
//                    "        \"EmployeeTo\": \"Shi\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"TicketDate\": \"07/08/2023\",\n" +
//                    "        \"Branch\": \"Head Office Chalappuram\",\n" +
//                    "        \"Department\": \"Service\",\n" +
//                    "        \"Employees\": \"VYSHAKH PN\",\n" +
//                    "        \"EmployeeTo\": \"Anvin\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//
//            val jObject = JSONObject(sss)
//            stockRequestSetterGetter.value = StockRequestModel(sss)
//        }catch (e: Exception){
//
//        }


    }
}