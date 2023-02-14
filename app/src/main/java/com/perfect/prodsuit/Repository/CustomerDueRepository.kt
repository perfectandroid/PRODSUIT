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
import com.perfect.prodsuit.Model.CustomerDueModel
import com.perfect.prodsuit.Model.ServiceSalesModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList


object CustomerDueRepository {

    private var progressDialog: ProgressDialog? = null
    val customerDuesSetterGetter = MutableLiveData<CustomerDueModel>()
    val TAG: String = "CustomerDueRepository"

    fun getServicesApiCall(context: Context,ID_Customer: String): MutableLiveData<CustomerDueModel> {
        getCustomerDue(context, ID_Customer)
        return customerDuesSetterGetter
    }

    private fun getCustomerDue(context: Context, ID_Customer: String) {

        try {
            customerDuesSetterGetter.value = CustomerDueModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
                R.drawable.progress))
            progressDialog!!.setCanceledOnTouchOutside(false)
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

//                {"ReqMode":"445q+g40Nfs=","BankKey":"\/mXqmq3ZMvs=\n","Token":"R1+qLb1l9CrjrvoATU5QEw8o64+20c4sms4dhOjNZYuBYl8+NOw0hQ==","SubMode":"vJ/8asrP+O0=",
//                    "FK_Product":"vJ/8asrP+O0=","FK_Customer":"07/ybAx1yS4=","FK_CustomerOther":"KZtbclVmL7w=","FK_Branch":"8Ld7pH+WkK0=",
//                    "FK_Company":"vJ\/8asrP+O0=\n","EntrBy":"a5bTsgAqQ2o=\n"}



                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)

                //  requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("13"))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("77"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart("0"))


                Log.e(TAG,"requestObject1   74   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getProductcategory(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        progressDialog!!.dismiss()
                        Log.e(TAG,"response   911  "+response.body())
                        val jObject = JSONObject(response.body())
                        val customer = ArrayList<CustomerDueModel>()
                        customer.add(CustomerDueModel(response.body()))
                        val msg = customer[0].message
                        customerDuesSetterGetter.value = CustomerDueModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(TAG,"response   912  "+e.toString())
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_LONG)
                            .show()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    Log.e(TAG,"response   913  "+t.message)
                    progressDialog!!.dismiss()
//                    Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
//                        .show()
                }
            })
        }
        catch (e : Exception){
            e.printStackTrace()
            Log.e(TAG,"response   914  "+e.toString())
//            Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
//                .show()
            progressDialog!!.dismiss()
        }

    }
}