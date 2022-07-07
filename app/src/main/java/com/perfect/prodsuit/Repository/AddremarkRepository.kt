package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Model.AddRemarkModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AccountDetailsActivity
import com.perfect.prodsuit.View.Activity.AgendaActivity
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.*

object AddremarkRepository {

    val addRemarkSetterGetter = MutableLiveData<AddRemarkModel>()
    private var progressDialog: ProgressDialog? = null
    fun getServicesApiCall(context: Context): MutableLiveData<AddRemarkModel> {
        getAddRemark(context)
        return addRemarkSetterGetter
    }


    private fun getAddRemark(context: Context) {
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
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("58"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_LeadGenerate", ProdsuitApplication.encryptStart(AgendaActivity.ID_LeadGenerate))
                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(AccountDetailsActivity.ID_LeadGenerateProduct))
                requestObject1.put("CustomerNote", ProdsuitApplication.encryptStart(""))
                requestObject1.put("EmployeeNote", ProdsuitApplication.encryptStart(""))
                Log.i("requestobject",requestObject1.toString()+"\n"+AccountDetailsActivity.ID_ActionType+"\n"+AccountDetailsActivity.ID_LeadGenerateProduct)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getAddremark(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("AddRemark", response.body())
                        val users = ArrayList<AddRemarkModel>()
                        users.add(AddRemarkModel(response.body()))
                        val msg = users[0].message
                        addRemarkSetterGetter.value = AddRemarkModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                    }
                }

                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

