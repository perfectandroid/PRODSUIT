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
import com.perfect.prodsuit.Model.ServiceListModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceListRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceListSetterGetter = MutableLiveData<ServiceListModel>()
    val TAG: String = "ServiceListRepository"

    fun getServicesApiCall(context: Context,SubMode : String,ID_Branch : String,FK_Area : String,ID_Employee : String,strFromDate : String,strToDate : String,strCustomer : String,
                           strMobile : String,strTicketNo : String,strDueDays : String): MutableLiveData<ServiceListModel> {
        getServiceList(context,SubMode,ID_Branch,FK_Area,ID_Employee,strFromDate,strToDate,strCustomer,strMobile,strTicketNo,strDueDays)
        return serviceListSetterGetter
    }

    private fun getServiceList(context: Context,SubMode : String,ID_Branch : String,FK_Area : String,ID_Employee : String,strFromDate : String,strToDate : String,strCustomer : String,
                               strMobile : String,strTicketNo : String,strDueDays : String) {
        try {
            serviceListSetterGetter.value = ServiceListModel("")
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
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(SubMode))
                //requestObject1.put("SubMode", ProdsuitApplication.encryptStart("4"))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("FK_ComplaintType", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("Status", ProdsuitApplication.encryptStart("-1"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                requestObject1.put("SortOrder", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("FK_Post", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("FK_Area", ProdsuitApplication.encryptStart(FK_Area))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("DueDays", ProdsuitApplication.encryptStart(strDueDays))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("UserCode", null)))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(strFromDate))
                requestObject1.put("Todate", ProdsuitApplication.encryptStart(strToDate))
                requestObject1.put("CSRTickno", ProdsuitApplication.encryptStart(strTicketNo))
                requestObject1.put("CusName", ProdsuitApplication.encryptStart(strCustomer))
                requestObject1.put("CusMobile", ProdsuitApplication.encryptStart(strMobile))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"requestObject1   82   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getServiceAssignNewDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ServiceListModel>()
                        leads.add(ServiceListModel(response.body()))
                        val msg = leads[0].message
                        serviceListSetterGetter.value = ServiceListModel(msg)
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
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }
    }
}