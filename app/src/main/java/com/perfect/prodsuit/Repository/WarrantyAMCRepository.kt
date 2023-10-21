package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.ActionListTicketReportModel
import com.perfect.prodsuit.Model.ServiceFollowUpMappedReplacedProductModel
import com.perfect.prodsuit.Model.ServiceHistModel
import com.perfect.prodsuit.Model.WarrantyAMCModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object WarrantyAMCRepository {

    private var progressDialog: ProgressDialog? = null
    val warrantyAMCSetterGetter = MutableLiveData<WarrantyAMCModel>()
    val TAG: String = "WarrantyAMCRepository"
    fun getServicesApiCall(context: Context): MutableLiveData<WarrantyAMCModel> {
        getServiceHistory(context)
        return warrantyAMCSetterGetter
    }

    private fun getServiceHistory(context: Context) {
        try {
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
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_Employee = context.getSharedPreferences(Config.SHARED_PREF1, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("45"))
                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("FK_CustomerOthers",  ProdsuitApplication.encryptStart("0"))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart("2"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("UserCode", null)))
                requestObject1.put("Critrea3", ProdsuitApplication.encryptStart("10"))
                requestObject1.put("Critrea2", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("Critrea1", ProdsuitApplication.encryptStart("0"))




                Log.e(TAG,"requestObject1   warranty   "+requestObject1)





            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getWarrantyAMC(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e("WarrantyAMC Respose  123   ",response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<WarrantyAMCModel>()
                        leads.add(WarrantyAMCModel(response.body()))
                        val msg = leads[0].message
                        warrantyAMCSetterGetter.value = WarrantyAMCModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
        }
    }
}