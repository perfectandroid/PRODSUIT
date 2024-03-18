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
import com.perfect.prodsuit.Model.NewListTicketReportModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object NewListTicketReportRepository {

    private var progressDialog: ProgressDialog? = null
    val newListTicketReportSetterGetter = MutableLiveData<NewListTicketReportModel>()
    val TAG: String = "NewListTicketReportRepository"

    fun getServicesApiCall(context: Context,ReportMode: String?, ID_Branch: String?,ID_Employee : String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                           ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?): MutableLiveData<NewListTicketReportModel> {
        getNewListTicketReport(context,ReportMode,ID_Branch,ID_Employee,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
        return newListTicketReportSetterGetter
    }

    private fun getNewListTicketReport(context: Context,ReportMode: String?, ID_Branch: String?,ID_Employee : String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                                       ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?) {

        try {
            newListTicketReportSetterGetter.value = NewListTicketReportModel("")
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


//                "ReqMode":"53",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//
//                "ReportMode":5",
//                "ID_Branch":1,
//                "Todate":"2022-04-06 "
//                "FromDate":"2022-04-06 ",
//                "ID_Product:"1",
//                "FK_Priority":"1",
//                "ActStatus":"1",
//                "GroupId:"1"
//

//

//
//                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("53"))
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//
//                requestObject1.put("ReportMode", ProdsuitApplication.encryptStart(ReportMode))
//                requestObject1.put("ID_Branch", ProdsuitApplication.encryptStart(ID_Branch))
//                requestObject1.put("Todate", ProdsuitApplication.encryptStart(strTodate))
//                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(strFromdate))
//                requestObject1.put("ID_Product", ProdsuitApplication.encryptStart(ID_Product))
//                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
//                requestObject1.put("ActStatus", ProdsuitApplication.encryptStart(ID_Status))
//                requestObject1.put("GroupId", ProdsuitApplication.encryptStart(GroupId))


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("53"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                requestObject1.put("ReportMode", ProdsuitApplication.encryptStart(ReportMode))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(strFromdate))
                requestObject1.put("Todate", ProdsuitApplication.encryptStart(strTodate))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("ID_Branch", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("Status", ProdsuitApplication.encryptStart(ID_Status))
                requestObject1.put("FK_CollectedBy", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"ReportMode   1241   "+ReportMode)
                Log.e(TAG,"requestObject1   1242   "+strFromdate +"  "+strTodate)
                Log.e(TAG,"requestObject1   1243   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getNewListDetailsReport(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<NewListTicketReportModel>()
                        leads.add(NewListTicketReportModel(response.body()))
                        val msg = leads[0].message
                        newListTicketReportSetterGetter.value = NewListTicketReportModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, ""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}