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
import com.perfect.prodsuit.Model.EMIAccountDetailsModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object EmiAccountDetailsRepository {

    private var progressDialog: ProgressDialog? = null
    val emiAccountDetailsSetterGetter = MutableLiveData<EMIAccountDetailsModel>()
    val TAG: String = "EmiAccountDetailsRepository"

    fun getServicesApiCall(context: Context, ID_CustomerWiseEMI: String?,strCurrentDate : String): MutableLiveData<EMIAccountDetailsModel> {
        getEmiAccountDetails(context, ID_CustomerWiseEMI,strCurrentDate)
        return emiAccountDetailsSetterGetter
    }

    private fun getEmiAccountDetails(context: Context, ID_CustomerWiseEMI: String?,strCurrentDate : String) {

        try {
            emiAccountDetailsSetterGetter.value = EMIAccountDetailsModel("")
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
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

//                {"BankKey":"-500","Token":"1E018EC4-9978-4FCE-A763-455F59DF3540","ReqMode":"103",
//                    "ID_CustomerWiseEMI":"14","TrnsDate":"2023-03-10","FK_Company":"1","AccountMode":"2"}


                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("103"))
                requestObject1.put("ID_CustomerWiseEMI", ProdsuitApplication.encryptStart(ID_CustomerWiseEMI))
                requestObject1.put("TrnsDate", ProdsuitApplication.encryptStart(strCurrentDate))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company",null)))
                requestObject1.put("AccountMode", ProdsuitApplication.encryptStart("2"))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e("requestobject  7012  ","\n"+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getEMIAccountDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"7012  response  "+ response.body())
                        val users = ArrayList<EMIAccountDetailsModel>()
                        users.add(EMIAccountDetailsModel(response.body()))
                        val msg = users[0].message
                        emiAccountDetailsSetterGetter.value = EMIAccountDetailsModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
                        Log.e(TAG,"70121    "+e.toString())
                    }
                }

                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    Log.e(TAG,"70122    "+t.message)
                }
            })
        }
        catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
            Log.e(TAG,"70123    "+e.toString())
        }
    }
}