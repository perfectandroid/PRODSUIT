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
import com.perfect.prodsuit.Model.ServiceDetailsModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceDetailsRepository {

    private var progressDialog: ProgressDialog? = null
    val servicedetailsSetterGetter = MutableLiveData<ServiceDetailsModel>()
    val TAG: String = "ServiceDetailsRepository"

    fun getServicesApiCall(context: Context,FK_Product: String, NameCriteria: String,subMode: String): MutableLiveData<ServiceDetailsModel> {
        getServiceDashboard(context,FK_Product,NameCriteria,subMode)
        return servicedetailsSetterGetter
    }

    private fun getServiceDashboard(context: Context,FK_Product: String, NameCriteria: String,subMode: String) {
        try {
            servicedetailsSetterGetter.value = ServiceDetailsModel("")
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
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                //.....................
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("111"))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(subMode))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(FK_Product))
                requestObject1.put("NameCriteria", ProdsuitApplication.encryptStart(NameCriteria))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"requestObject1   request   "+requestObject1)
                Log.e(TAG,"requestObject1   80   "+subMode)
             //   Log.e(TAG,"requestObject1   80   "+FK_Product)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            Log.v("dsfsdd333","body "+requestObject1.toString())
            Log.i("response1122","body==="+requestObject1.toString())
            //     val call = apiService.getLeadsDashBoardDetails(body)
            val call = apiService.getServiceFollowUpServiceDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        Log.e(TAG,"response  94   "+response.body())
                        Log.i("response1122","responseBody==="+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val service = ArrayList<ServiceDetailsModel>()
                        service.add(ServiceDetailsModel(response.body()))
                        val msg = service[0].message
                        Log.i("response1122","msg==="+msg)
                        servicedetailsSetterGetter.value = ServiceDetailsModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
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
            Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}