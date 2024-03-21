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
import com.perfect.prodsuit.Model.MaterialUsageSaveModel
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

object MaterialUsageSaveRepository {

    private var progressDialog: ProgressDialog? = null
    val materialUsageSaveSetterGetter = MutableLiveData<MaterialUsageSaveModel>()
    val TAG: String = "MaterialUsageSave"

    fun getServicesApiCall(context: Context,UserAction : String,TransMode : String,ID_ProjectMaterialUsage : String,ID_Stage : String,ID_Project : String,
                           strUsagedate : String,ID_Employee : String,ID_Team : String,saveDetailArray : JSONArray
    ): MutableLiveData<MaterialUsageSaveModel> {
        saveMaterialUsage(context,UserAction,TransMode, ID_ProjectMaterialUsage, ID_Stage, ID_Project, strUsagedate, ID_Employee, ID_Team, saveDetailArray)
        return materialUsageSaveSetterGetter
    }

    private fun saveMaterialUsage(context: Context,UserAction : String,TransMode : String,ID_ProjectMaterialUsage : String,ID_Stage : String,ID_Project : String,
                                  strUsagedate : String,ID_Employee : String,ID_Team : String,saveDetailArray : JSONArray) {

        try {
            materialUsageSaveSetterGetter.value = MaterialUsageSaveModel("")
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

//                {"BankKey":"-500,"Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","FK_Company":"1","UserAction":"1","TransMode":"","ID_ProjectMaterialUsage":"0",
//                    "FK_Stages":"1","FK_Project":"9817369369","ProMatUsageDate":"2023-09-21","FK_Employee":"8","FK_Team":"1","FK_BranchCodeUser":"3","EntrBy":"bemp123",
//                    "MatUsageProductDetails":[
//                    {"ProductID":"1548","Quantity":"23","StockId":"23","Mode":"23"},
//                    {"ProductID":"1549","Quantity":"10122","StockId":"23","Mode":"23"},
//                    {"ProductID":"1547","Quantity":"10123","StockId":"23","Mode":"23"}]}

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_UserRoleSP = context.getSharedPreferences(Config.SHARED_PREF41, 0)
                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)



                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(UserAction))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(TransMode))
                requestObject1.put("ID_ProjectMaterialUsage", ProdsuitApplication.encryptStart(ID_ProjectMaterialUsage))
                requestObject1.put("FK_Stages", ProdsuitApplication.encryptStart(ID_Stage))
                requestObject1.put("FK_Project", ProdsuitApplication.encryptStart(ID_Project))
                requestObject1.put("ProMatUsageDate", ProdsuitApplication.encryptStart(Config.convertDate(strUsagedate)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("FK_Team", ProdsuitApplication.encryptStart(ID_Team))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("MatUsageProductDetails", saveDetailArray)
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                Log.e(TAG,"getUpdateMaterialUsage   1011   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getUpdateMaterialUsage(body)
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
                        val leads = ArrayList<MaterialUsageSaveModel>()
                        leads.add(MaterialUsageSaveModel(response.body()))
                        val msg = leads[0].message
                        materialUsageSaveSetterGetter.value = MaterialUsageSaveModel(msg)
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

    }
}