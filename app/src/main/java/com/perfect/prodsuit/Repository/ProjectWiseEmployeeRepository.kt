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
import com.perfect.prodsuit.Model.ProjectWiseEmployeeModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ProjectWiseEmployeeRepository {

    private var progressDialog: ProgressDialog? = null
    val projectWiseEmployeeSetGet = MutableLiveData<ProjectWiseEmployeeModel>()
    val TAG: String = "ProjectWiseEmployeeRepository"

    fun getServicesApiCall(context: Context,  ID_Project: String, ID_Stage: String, Criteria: String, ReqMode: String): MutableLiveData<ProjectWiseEmployeeModel> {
        getProjectWiseEmployee(context,ID_Project,ID_Stage,Criteria,ReqMode)
        return projectWiseEmployeeSetGet
    }

    private fun getProjectWiseEmployee(context: Context, ID_Project: String, ID_Stage: String, Criteria: String, ReqMode: String) {

        try {
            projectWiseEmployeeSetGet.value= ProjectWiseEmployeeModel("")
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

               // {"ReqMode":"159","FK_Project":"0","BankKey":"-500","FK_Company":"1","FK_Stages":"0"}


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_Project", ProdsuitApplication.encryptStart(ID_Project))
                requestObject1.put("FK_Stages", ProdsuitApplication.encryptStart(ID_Stage))

                Log.e(TAG,"requestObject1   80000    "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getProjectTransactionEmployeeDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"101    "+response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ProjectWiseEmployeeModel>()
                        leads.add(ProjectWiseEmployeeModel(response.body()))
                        val msg = leads[0].message
                        projectWiseEmployeeSetGet.value = ProjectWiseEmployeeModel(msg)
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
//            projectWiseEmployeeSetGet.value= ProjectWiseEmployeeModel("")
//            val msg = "{\n" +
//                    "  \"EmployeeDetails\": {\n" +
//                    "    \"EmployeeList\": [\n" +
//                    "      {\n" +
//                    "        \"ID_Employee\": \"1\",\n" +
//                    "        \"Employee\": \"Amritha\",\n" +
//                    "        \"Department\": \"Customer Service\",\n" +
//                    "        \"Dessignation\": \"Service Engineer\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Employee\": \"2\",\n" +
//                    "        \"Employee\": \"Sachin\",\n" +
//                    "        \"Department\": \"Sales\",\n" +
//                    "        \"Dessignation\": \"Service Manager\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Employee\": \"3\",\n" +
//                    "        \"Employee\": \"Sona\",\n" +
//                    "        \"Department\": \"Customer Service\",\n" +
//                    "        \"Dessignation\": \"Service specialist\"\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"ID_Employee\": \"4\",\n" +
//                    "        \"Employee\": \"Shan\",\n" +
//                    "        \"Department\": \"HR Account\",\n" +
//                    "        \"Dessignation\": \"HR\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//            projectWiseEmployeeSetGet.value = ProjectWiseEmployeeModel(msg)
//        }catch (e : Exception){
//
//        }
    }
}