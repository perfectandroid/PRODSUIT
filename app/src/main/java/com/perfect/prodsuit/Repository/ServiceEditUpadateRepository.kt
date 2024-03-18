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
import com.perfect.prodsuit.Model.ServiceAssignModel
import com.perfect.prodsuit.Model.ServiceEditUpdateModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceEditUpadateRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceEditUpdateSetterGetter = MutableLiveData<ServiceEditUpdateModel>()
    val TAG: String = "ServiceEditUpadateRepository"

    fun getServicesApiCall(context: Context, ReqMode : String,ID_CustomerServiceRegister : String,strVisitdate  : String,ID_Priority : String,ID_AttendedBy : String,ID_Status : String): MutableLiveData<ServiceEditUpdateModel> {
        getServiceEditUpdate(context,ReqMode,ID_CustomerServiceRegister,strVisitdate,ID_Priority,ID_AttendedBy,ID_Status)
        return serviceEditUpdateSetterGetter
    }

    private fun getServiceEditUpdate(context: Context, ReqMode : String,ID_CustomerServiceRegister : String,strVisitDate  : String,
                                     ID_Priority : String,ID_AttendedBy : String,ID_Status : String) {


        Log.e(TAG,"24   getServiceEditUpdate  "
                +"\n"+"ReqMode                     : "+ReqMode
                +"\n"+"ID_CustomerServiceRegister  : "+ID_CustomerServiceRegister
                +"\n"+"strVisitDate                : "+strVisitDate
                +"\n"+"ID_Priority                 : "+ID_Priority
                +"\n"+"ID_AttendedBy               : "+ID_AttendedBy
                +"\n"+"ID_Status                   : "+ID_Status)

        try {
            serviceEditUpdateSetterGetter.value = ServiceEditUpdateModel("")
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
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

//                {"BankKey":"\/mXqmq3ZMvs=\n","Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","FK_Customerserviceregister":"1",
//                    "Visitdate":"2023-02-08","FK_Priority":"2","FK_Company":"1","FK_BranchCodeUser":"3","EntrBy":"APP","FK_Branch":"3",FK_AttendedBy=2,Status=5}


                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Customerserviceregister", ProdsuitApplication.encryptStart(ID_CustomerServiceRegister))
                requestObject1.put("Visitdate", ProdsuitApplication.encryptStart(strVisitDate))
                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart( FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("FK_AttendedBy", ProdsuitApplication.encryptStart(ID_AttendedBy))
                requestObject1.put("Status", ProdsuitApplication.encryptStart(ID_Status))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"requestObject1   78   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getCustomerserviceassignEdit(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"response  111     "+response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ServiceEditUpdateModel>()
                        leads.add(ServiceEditUpdateModel(response.body()))
                        val msg = leads[0].message
                        serviceEditUpdateSetterGetter.value = ServiceEditUpdateModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }

    }
}