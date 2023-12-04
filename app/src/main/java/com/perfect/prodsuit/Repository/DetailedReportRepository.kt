package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.DetailedReportModel
import com.perfect.prodsuit.Model.DocumentListModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.DocumentListRepository.documentlistSetterGetter
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object DetailedReportRepository {

    private var progressDialog: ProgressDialog? = null
    val actionListTicketReportSetterGetter = MutableLiveData<DetailedReportModel>()
    val TAG: String = "DetailReportRepository"

    fun getServicesApiCall(
        context: Context,
        ReportMode: String?,
        ID_Branch: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_NextAction: String?,
        ID_ActionType: String?,
        ID_Priority: String?,
        ID_Status: String?,
        GroupId: String?,
        ID_AssignedEmployee: String?,
        ID_CollectedBy: String?,
        ID_Category: String?
    ): MutableLiveData<DetailedReportModel> {
        getActionListTicketReport(context,ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId,ID_AssignedEmployee,ID_CollectedBy,ID_Category)
        return actionListTicketReportSetterGetter
    }

    private fun getActionListTicketReport(
        context: Context,
        ReportMode: String?,
        ID_Branch: String?,
        strFromdate: String?,
        strTodate: String?,
        ID_Product: String?,
        ID_NextAction: String?,
        ID_ActionType: String?,
        ID_Priority: String?,
        ID_Status: String?,
        GroupId: String?,
        ID_AssignedEmployee: String?,
        ID_CollectedBy: String?,
        ID_Category: String?
    ) {
        try {
            actionListTicketReportSetterGetter.value = DetailedReportModel("")
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
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)


                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("ReportMode", ProdsuitApplication.encryptStart(GroupId))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(strFromdate))
                requestObject1.put("ToDate", ProdsuitApplication.encryptStart(strTodate))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_AssignedEmployee))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                Log.e(TAG,"ReportMode   456745   "+ReportMode)
                Log.e(TAG,"requestObject1   456745   "+requestObject1)
                Log.v("sfsdfsdfdsfdd","requestObject1 "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getSummaryWiseReport(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<DetailedReportModel>()
                        leads.add(DetailedReportModel(response.body()))
                        val msg = leads[0].message
                        actionListTicketReportSetterGetter.value = DetailedReportModel(msg)
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