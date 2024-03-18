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
import com.perfect.prodsuit.Model.LeadStatusDashModel
import com.perfect.prodsuit.Model.ServiceDashModel
import com.perfect.prodsuit.Model.ServiceStatusDashModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceStatusDashRepository {

    private var progressDialog: ProgressDialog? = null
    val servicestatusdashSetterGetter = MutableLiveData<ServiceStatusDashModel>()
    val TAG: String = "ServiceStatusDashRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ServiceStatusDashModel> {
        getServiceStatusDashboard(context)
        return servicestatusdashSetterGetter
    }

    private fun getServiceStatusDashboard(context: Context) {

        try {
            servicestatusdashSetterGetter.value = ServiceStatusDashModel("")
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
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

//                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("38"))
//                requestObject1.put("SubMode", ProdsuitApplication.encryptStart("2"))
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
//                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
//                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("UserCode", null)))

                //.....................
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("99"))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("UserCode", null)))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                Log.e(TAG,"requestObject1   71   "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            Log.i("response112211","body==="+requestObject1.toString())
           // val call = apiService.getLeadsDashBoardDetails(body)
            val call = apiService.getServiceDashBoardDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        Log.e(TAG,"response  94   "+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<LeadStatusDashModel>()
                        val service = ArrayList<ServiceStatusDashModel>()
                        service.add(ServiceStatusDashModel(response.body()))
                        val msg = service[0].message
                        Log.i("response112211","msg==="+msg)
                        servicestatusdashSetterGetter.value = ServiceStatusDashModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}