package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.LeadGenerateReportModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object LeadGenerateReportRepository {
    val TAG : String = "LeadGenerateReportRepository"
    val leadGenerateReportSetterGetter = MutableLiveData<LeadGenerateReportModel>()
    private var progressDialog: ProgressDialog? = null
    fun getServicesApiCall(context: Context,strFromdate : String,strTodate : String,strDashboardTypeId : String): MutableLiveData<LeadGenerateReportModel> {
        getLeadGenerateReport(context,strFromdate,strTodate,strDashboardTypeId)
        return leadGenerateReportSetterGetter
    }

    private fun getLeadGenerateReport(context: Context,strFromdate : String,strTodate : String,strDashboardTypeId : String) {
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

//                "ReqMode":"26",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "ID_ReportSettings":15,
//                "Todate":"2022-04-06 "
//                "FromDate":"2022-04-06 "

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)


                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("26"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))

                requestObject1.put("ID_ReportSettings", ProdsuitApplication.encryptStart(strDashboardTypeId))
                requestObject1.put("Todate", ProdsuitApplication.encryptStart(strTodate))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(strFromdate))


                Log.e(TAG,"requestObject1   81    "+requestObject1)

            } catch (e: Exception) {
                Log.i("Exception",e.toString());
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getLeadGenerateReport(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("LeadGenerateReportModel Respose",response.body())
                        val users = ArrayList<LeadGenerateReportModel>()
                        users.add(LeadGenerateReportModel(response.body()))
                        val msg = users[0].message
                        leadGenerateReportSetterGetter.value = LeadGenerateReportModel(msg)
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
            progressDialog!!.dismiss()
        }
    }

}