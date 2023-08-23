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
import com.perfect.prodsuit.Model.SaveUpdateStockRTModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object SaveUpdateStockRTRepository {

    private var progressDialog: ProgressDialog? = null
    val saveUpdateStockRTSetterGetter = MutableLiveData<SaveUpdateStockRTModel>()
    val TAG: String = "SaveUpdateStockRTRepository"

    fun getServicesApiCall(context: Context,UserAction : String,FK_BranchFrom : String,FK_DepartmentFrom : String,FK_EmployeeFrom : String,STRequest : String,strDate : String,
                           FK_BranchTo : String,FK_DepartmentTo : String,FK_EmployeeTo : String,TransMode : String,FK_StockRequest : String,ID_StockTransfer : String,
                           saveDetailArray : JSONArray): MutableLiveData<SaveUpdateStockRTModel> {
        SaveUpdateStockRT(context, UserAction, FK_BranchFrom!!,FK_DepartmentFrom!!,FK_EmployeeFrom!!,STRequest,strDate, FK_BranchTo,FK_DepartmentTo,FK_EmployeeTo,
            TransMode,FK_StockRequest,ID_StockTransfer,saveDetailArray)
        return saveUpdateStockRTSetterGetter
    }

    private fun SaveUpdateStockRT(context: Context,UserAction : String, FK_BranchFrom : String,FK_DepartmentFrom : String,FK_EmployeeFrom : String,STRequest : String,strDate : String,
                                  FK_BranchTo : String,FK_DepartmentTo : String,FK_EmployeeTo : String,TransMode : String,FK_StockRequest : String,ID_StockTransfer : String,
                                  saveDetailArray : JSONArray) {

//        Log.e(TAG,"2888   SaveUpdateStockRT"
//        +"\n FK_BranchFrom      "+FK_BranchFrom
//        +"\n FK_DepartmentFrom  "+FK_DepartmentFrom
//        +"\n FK_EmployeeFrom    "+FK_EmployeeFrom
//        +"\n STRequest          "+STRequest
//        +"\n strDate            "+strDate
//        +"\n FK_BranchTo        "+FK_BranchTo
//        +"\n FK_DepartmentTo    "+FK_DepartmentTo
//        +"\n FK_EmployeeTo      "+FK_EmployeeTo
//        +"\n TransMode          "+TransMode
//        +"\n FK_StockRequest    "+FK_StockRequest
//        +"\n ID_StockTransfer   "+ID_StockTransfer
//        +"\n saveDetailArray    "+saveDetailArray)

        try {
            saveUpdateStockRTSetterGetter.value = SaveUpdateStockRTModel("")
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

//                {
//                    "BankKey": "-500",
//                    "FK_Company": "1",
//                    "Token": "F5517387-B815-4DCC-B2CC-E0A2F3160E22",
//                    "UserAction": 1,
//                    "FK_BranchFrom": "3",
//                    "FK_DepartmentFrom": "2",
//                    "FK_EmployeeFrom": "10044",
//                    "STRequest": "1",
//                    "TransDate": "2023-07-28",
//                    "FK_BranchTo": "2",
//                    "FK_DepartmentTo": "3",
//                    "FK_EmployeeTo": "10072",
//                    "FK_BranchCodeUser": "3",
//                    "EntrBy": "VYSHAKH",
//                    "TransMode": "INTR",
//                    "FK_StockRequest": "0",
//                    "ID_StockTransfer": "0",
//                    "EmployeeStockTransferDetails": [
//                    {
//                        "ID_Product": "1",
//                        "Quantity": "1",
//                        "ID_Stock": "0",
//                        "QuantityStandBy": "0",
//                        "StockMode": "0"
//                    }
//                    ]
//                }

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))

                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(UserAction))

                requestObject1.put("FK_BranchFrom", ProdsuitApplication.encryptStart(FK_BranchFrom))
                requestObject1.put("FK_DepartmentFrom", ProdsuitApplication.encryptStart(FK_DepartmentFrom))
                requestObject1.put("FK_EmployeeFrom", ProdsuitApplication.encryptStart(FK_EmployeeFrom))

                requestObject1.put("STRequest", ProdsuitApplication.encryptStart(STRequest))  //  1= Save .2 Update
                requestObject1.put("TransDate", ProdsuitApplication.encryptStart(strDate))

                requestObject1.put("FK_BranchTo", ProdsuitApplication.encryptStart(FK_BranchTo))
                requestObject1.put("FK_DepartmentTo", ProdsuitApplication.encryptStart(FK_DepartmentTo))
                requestObject1.put("FK_EmployeeTo", ProdsuitApplication.encryptStart(FK_EmployeeTo))

                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(TransMode))
                requestObject1.put("FK_StockRequest", ProdsuitApplication.encryptStart(FK_StockRequest))
                requestObject1.put("ID_StockTransfer", ProdsuitApplication.encryptStart(ID_StockTransfer))

                requestObject1.put("EmployeeStockTransferDetails", saveDetailArray)

                Log.e(TAG,"78  StockRTEmployeeDetails    "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.updateStockTransfer(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e("EmployeeInventory",response.body())
                        val leads = ArrayList<SaveUpdateStockRTModel>()
                        leads.add(SaveUpdateStockRTModel(response.body()))
                        val msg = leads[0].message
                        saveUpdateStockRTSetterGetter.value = SaveUpdateStockRTModel(msg)
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

    }

}