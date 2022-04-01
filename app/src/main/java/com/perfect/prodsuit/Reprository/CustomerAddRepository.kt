package com.perfect.prodsuit.Reprository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.CustomerAddModel
import com.perfect.prodsuit.Model.CustomerSearchModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.CustomerSearchActivity
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object CustomerAddRepository {

    private var progressDialog: ProgressDialog? = null
    val customerSetterGetter = MutableLiveData<CustomerAddModel>()
    val TAG: String = "CustomerAddRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<CustomerAddModel> {
        getAddCustomer(context)
        return customerSetterGetter
    }

    private fun getAddCustomer(context: Context) {

        Log.e("TAG","getCustomer  ")
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

//                "ReqMode":"10",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg
//                "Name":"Sreejisha",
//                "Address:"Vadakara",
//                "Email:Sree@gmail.com",
//                "MobileNumber:9539036341"

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("10"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))

                requestObject1.put("Name", ProdsuitApplication.encryptStart(CustomerSearchActivity.strName))
                requestObject1.put("Address", ProdsuitApplication.encryptStart(CustomerSearchActivity.strAddress))
                requestObject1.put("Email", ProdsuitApplication.encryptStart(CustomerSearchActivity.strEmail))
                requestObject1.put("MobileNumber", ProdsuitApplication.encryptStart(CustomerSearchActivity.strPhone))


                Log.e(TAG,"requestObject1   86   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.addCustomerDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val customer = ArrayList<CustomerSearchModel>()
                        customer.add(CustomerSearchModel(response.body()))
                        val msg = customer[0].message
                        customerSetterGetter.value = CustomerAddModel(msg)
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