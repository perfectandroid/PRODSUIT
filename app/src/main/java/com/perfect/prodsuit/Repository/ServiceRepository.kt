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
import com.perfect.prodsuit.Model.ProductPriorityModel
import com.perfect.prodsuit.Model.ServiceModel
import com.perfect.prodsuit.Model.ServiceProductModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceSetterGetter = MutableLiveData<ServiceModel>()
    val TAG: String = "ServiceRepository"

    fun getServicesApiCall(context: Context,ReqMode : String, SubMode: String, ID_Product: String): MutableLiveData<ServiceModel> {
        getServiceSetterGetter(context,ReqMode,SubMode,ID_Product)
        return serviceSetterGetter
    }

    private fun getServiceSetterGetter(context: Context,ReqMode : String, SubMode: String, ID_Product: String) {

        try {
            serviceSetterGetter.value = ServiceModel("")
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

                // Service
             //   {"ReqMode":"E2NQgC1tmbk=","BankKey":"\/mXqmq3ZMvs=\n","Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","FK_Product":"vJ/8asrP+O0="}

                 // Complaint
             //   {"ReqMode":"ZWsGPbKR62k=","BankKey":"\/mXqmq3ZMvs=\n","Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","SubMode":"8Ld7pH+WkK0=","FK_Company":"vJ/8asrP+O0="}

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)


                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(""))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(ID_Product))


                Log.e(TAG,"requestObject1   971   "+requestObject1)
                Log.e(TAG,"requestObject1   972   ReqMode  :  "+ReqMode+"   ID_Product  :  "+ID_Product)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getServiceDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ProductPriorityModel>()
                        leads.add(ProductPriorityModel(response.body()))
                        val msg = leads[0].message
                        serviceSetterGetter.value = ServiceModel(msg)
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