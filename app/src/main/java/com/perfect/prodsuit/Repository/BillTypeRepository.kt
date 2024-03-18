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
import com.perfect.prodsuit.Model.BilltTypeModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object BillTypeRepository {

    private var progressDialog: ProgressDialog? = null
    val billTypeSetGet = MutableLiveData<BilltTypeModel>()
    val TAG: String = "BillTypeRepository"

    fun getServicesApiCall(context: Context,ID_Project : String,ID_Employee : String): MutableLiveData<BilltTypeModel> {
        getBillType(context,ID_Project,ID_Employee)
        return billTypeSetGet
    }

    private fun getBillType(context: Context,ID_Project : String,ID_Employee : String) {
        try {
            billTypeSetGet.value = BilltTypeModel("")
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

                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)

                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_DepartmentSP = context.getSharedPreferences(Config.SHARED_PREF55, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)



//                {"BankKey":"-500","Token":"F513239B-E6F7-4CB9-AFF9-58C54FEE3CE5","FK_Project":"1","FK_Company":"1","FK_Employee":"1"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                requestObject1.put("FK_Project", ProdsuitApplication.encryptStart(ID_Project))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                Log.e(TAG,"844441   getCRMTileDashBoardDetails  "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getProjectBillTypeDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<BilltTypeModel>()
                        leads.add(BilltTypeModel(response.body()))
                        val msg = leads[0].message
                        billTypeSetGet.value = BilltTypeModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"1151  "+e)
                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Log.e(TAG,"1152  "+t)
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Log.e(TAG,"1153  "+e)
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }


//        try {
//            billTypeSetGet.value = BilltTypeModel("")
//            val msg = "{\n" +
//                    "  \"BillDetails\": {\n" +
//                    "    \"BillList\": [\n" +
//                    "      {\n" +
//                    "        \"ID_BillType\": \"1\",\n" +
//                    "        \"BillType\": \"Project\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_BillType\": \"2\",\n" +
//                    "        \"BillType\": \"Project bill\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_BillType\": \"3\",\n" +
//                    "        \"BillType\": \"Project emi\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_BillType\": \"4\",\n" +
//                    "        \"BillType\": \"Project Transaction\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//            billTypeSetGet.value = BilltTypeModel(msg)
//
//        }catch (e: Exception){
//
//        }
    }
}