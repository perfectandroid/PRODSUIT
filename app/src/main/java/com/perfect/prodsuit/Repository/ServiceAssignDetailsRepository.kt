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
import com.perfect.prodsuit.Model.ServiceAssignDetailsModel
import com.perfect.prodsuit.Model.ServicePriorityModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList


object ServiceAssignDetailsRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceAssignDetailsSetterGetter = MutableLiveData<ServiceAssignDetailsModel>()
    val TAG: String = "ServiceAssignDetailsRepository"

    fun getServicesApiCall(context: Context,ReqMode : String,ID_CustomerServiceRegister : String,FK_CustomerserviceregisterProductDetails:String): MutableLiveData<ServiceAssignDetailsModel> {
        getServiceAssignDetail(context,ReqMode,ID_CustomerServiceRegister,FK_CustomerserviceregisterProductDetails)
        return serviceAssignDetailsSetterGetter
    }

    private fun getServiceAssignDetail(context: Context,ReqMode : String,ID_CustomerServiceRegister : String,FK_CustomerserviceregisterProductDetails:String) {

        try {
            serviceAssignDetailsSetterGetter.value = ServiceAssignDetailsModel("")
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


//                {"BankKey":"\/mXqmq3ZMvs=\n","Token":"UiTI0u3X5Hi1ccJKlEWFJuLY68Chy5zUgJHTcIeSgZL6+Gujr4rHnA==\n","ReqMode":"DT65YrVxy3g=",
//                    "FK_Customerserviceregister":"vJ/8asrP+O0=","FK_Company":"vJ\/8asrP+O0=\n"}


                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("FK_Customerserviceregister", ProdsuitApplication.encryptStart(ID_CustomerServiceRegister))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_CustomerserviceregisterProductDetails", ProdsuitApplication.encryptStart(FK_CustomerserviceregisterProductDetails))



                Log.e(TAG,"requestObject1   78   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getServiceAssignDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        Log.e(ServiceAssignRepository.TAG,"requestObject1   serviceassign   "+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ServiceAssignDetailsModel>()
                        leads.add(ServiceAssignDetailsModel(response.body()))
                        val msg = leads[0].message
                        serviceAssignDetailsSetterGetter.value = ServiceAssignDetailsModel(msg)
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