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
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.Model.ServiceFollowUpAttendanceModel
import com.perfect.prodsuit.Model.ServiceFollowUpHistoryModel
import com.perfect.prodsuit.Model.ServiceFollowUpModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

object ServiceFollowUpHistoryRepository {

    var TAG = "OverDueRepository"
    val serviceFollowUpSetterGetter = MutableLiveData<ServiceFollowUpHistoryModel>()
    private var progressDialog: ProgressDialog? = null
    fun getServicesApiCall(context: Context,customer_service_register:String,ID_Branch : String , ID_Employee : String,FK_Customer:String,FK_CustomerOthers:String,FK_Product:String): MutableLiveData<ServiceFollowUpHistoryModel> {
        Log.v("fsfsfds","branch3 "+ID_Branch)
        getServiceFollowUp(context,customer_service_register,ID_Branch,ID_Employee,FK_Customer,FK_CustomerOthers,FK_Product)
        return serviceFollowUpSetterGetter
    }
    private fun getServiceFollowUp(context: Context,customer_service_register:String,ID_Branch : String , ID_Employee : String,FK_Customer:String,FK_CustomerOther:String,FK_Product:String) {
        try {

            Log.v("fsfsfds","branch2 "+ID_Branch)
            serviceFollowUpSetterGetter.value = ServiceFollowUpHistoryModel("")
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
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                Log.v("asdasdad33fff","BankKey "+BankKeySP.getString("BANK_KEY", null))
                Log.v("asdasdad33fff","Token "+TokenSP.getString("Token", null))
                Log.v("asdasdad33fff","ReqMode "+83)
                Log.v("asdasdad33fff","FK_Company "+FK_CompanySP.getString("FK_Company", null))
                Log.v("asdasdad33fff","FK_BranchCodeUser "+ID_Branch)
                Log.v("asdasdad33fff","EntrBy "+UserCodeSP.getString("UserCode", null))
                Log.v("asdasdad33fff","FK_Employee "+ID_Employee)
                Log.v("asdasdad33fff","FK_Product "+FK_Product)
                Log.v("asdasdad33fff","FK_Customer "+FK_Customer)
                Log.v("asdasdad33fff","FK_CustomerOther "+FK_CustomerOther)
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("83"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("FK_Product",ProdsuitApplication.encryptStart(FK_Product))
                requestObject1.put("FK_Customer",ProdsuitApplication.encryptStart(FK_Customer))
                requestObject1.put("FK_CustomerOthers",ProdsuitApplication.encryptStart(FK_CustomerOther))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                Log.v("asdasdad33fff","requestObject1 "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getServiceFollowUpHistory(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"108  response  "+response.body())
                        val users = ArrayList<OverDueModel>()
                        users.add(OverDueModel(response.body()))
                        val msg = users[0].message
                        serviceFollowUpSetterGetter.value = ServiceFollowUpHistoryModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
                }
            })
         }
        catch (e: Exception) {
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
        }
    }

}

