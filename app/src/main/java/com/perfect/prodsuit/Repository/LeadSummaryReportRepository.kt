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
import com.perfect.prodsuit.Model.LeadSummaryDetailsReportModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

object LeadSummaryReportRepository {

    private var progressDialog: ProgressDialog? = null
    val leadsummaryreportSetterGetter = MutableLiveData<LeadSummaryDetailsReportModel>()
    val TAG: String = "LeadSummaryReportRepository"

    fun getServicesApiCall(
        context: Context,
        submode: String,
        strFromdate: String,
        strTodate: String,
        ID_Product: String,
        ID_Category: String,
        ID_Branch: String,
        ID_Employee: String,
        ID_AsgndEmployee: String?


    ): MutableLiveData<LeadSummaryDetailsReportModel> {
        getLeadSummaryReportDetail(context,submode,strFromdate,
                strTodate,
                ID_Product,
                ID_Category,
                ID_Branch,
                ID_Employee,ID_AsgndEmployee)
        return leadsummaryreportSetterGetter
    }

    private fun getLeadSummaryReportDetail(
        context: Context, Submode: String, strFromdate: String,
        strTodate: String,
        ID_Product: String,
        ID_Category: String,
        ID_Branch: String,
        ID_Employee: String,
        ID_AsgndEmployee: String?
    ) {

     /*   val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        val dateFrom = inputFormat.parse(strVisitDate)
        val strFromDate = outputFormat.format(dateFrom)*/
        Log.i("PASSDETails",strFromdate+"\n"+strTodate+"\n"+ID_Product+"\n"+ID_Category+"\n"+ID_Branch+"\n"+ID_Employee)


        try {
            leadsummaryreportSetterGetter.value = LeadSummaryDetailsReportModel("")
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
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

//                {"BankKey":"\/mXqmq3ZMvs=\n","Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","FK_Customerserviceregister":"1",
//                    "FK_Employee":"1","Visitdate":"2023-02-01","Visittime":"02:05","FK_Priority":"2","Remark":"Test","FK_Company":"1","FK_BranchCodeUser":"3",
//                    "EntrBy":"APP","FK_Branch":"3",Assignees{["FK_Employee":"1","EmployeeType":"3"]}




                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReportMode", ProdsuitApplication.encryptStart("5"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(strFromdate))
                requestObject1.put("ToDate", ProdsuitApplication.encryptStart(strTodate))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_AsgndEmployee))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(Submode))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))
                requestObject1.put("ID", ProdsuitApplication.encryptStart(ID_Employee))





                Log.e(TAG,"requestObject1   leadsummaryreport   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getLeadSummaryDetailReport(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"response  leadsummarydetailreport     "+response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<LeadSummaryDetailsReportModel>()
                        leads.add(LeadSummaryDetailsReportModel(response.body()))
                        val msg = leads[0].message
                        leadsummaryreportSetterGetter.value = LeadSummaryDetailsReportModel(msg)
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