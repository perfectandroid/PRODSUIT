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
import com.perfect.prodsuit.Model.AddSortLeadmngmntModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AccountDetailsActivity
import com.perfect.prodsuit.View.Activity.OverDueActivity
import com.perfect.prodsuit.View.Activity.TodoListActivity
import com.perfect.prodsuit.View.Activity.UpcomingtaskActivity
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.*

object SortLeadMangeListRepository {

    val sortleadmngeSetterGetter = MutableLiveData<AddSortLeadmngmntModel>()
    private var progressDialog: ProgressDialog? = null
    var submode1= TodoListActivity.submode
    var submode2= OverDueActivity.submode
    var submode3=UpcomingtaskActivity.submode
    fun getServicesApiCall(context: Context): MutableLiveData<AddSortLeadmngmntModel> {
        getSortLeadMangmnt(context)
        return sortleadmngeSetterGetter
    }


    private fun getSortLeadMangmnt(context: Context) {
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
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("24"))
                if (!submode1.equals(""))
                {
                    requestObject1.put("SubMode", ProdsuitApplication.encryptStart("1"))
                    requestObject1.put("criteria", ProdsuitApplication.encryptStart(TodoListActivity.criteria))
                }
                if (!submode2.equals(""))
                {
                    requestObject1.put("SubMode", ProdsuitApplication.encryptStart("2"))
                    requestObject1.put("criteria", ProdsuitApplication.encryptStart(OverDueActivity.criteria))
                    Log.i("Criteria",OverDueActivity.criteria)
                }
                if (!submode3.equals(""))
                {
                    requestObject1.put("SubMode", ProdsuitApplication.encryptStart("3"))
                    requestObject1.put("criteria", ProdsuitApplication.encryptStart(UpcomingtaskActivity.criteria))
                }



//
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                requestObject1.put("Name", ProdsuitApplication.encryptStart(OverDueActivity.name1))
//                requestObject1.put("Todate", ProdsuitApplication.encryptStart(OverDueActivity.date))

                Log.i("requestobject",requestObject1.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getLeadManagementDetailsList(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("SortLeadMng", response.body())
                        val users = ArrayList<AddSortLeadmngmntModel>()
                        users.add(AddSortLeadmngmntModel(response.body()))
                        val msg = users[0].message
                        sortleadmngeSetterGetter.value = AddSortLeadmngmntModel(msg)
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

