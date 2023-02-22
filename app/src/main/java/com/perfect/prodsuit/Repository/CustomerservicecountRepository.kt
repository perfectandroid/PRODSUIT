package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.CustomerservicecountModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object CustomerservicecountRepository {

    private var progressDialog: ProgressDialog? = null
    val CustomerservicecountSetterGetter = MutableLiveData<CustomerservicecountModel>()
    val TAG: String = "CustomerservicecountRepository"

    fun getServicesApiCall(context: Context, fkCust: String, fkOthercustomer: String, Prodid: String): MutableLiveData<CustomerservicecountModel> {
        getCustomerservicecount(context,fkCust,fkOthercustomer,Prodid)
        return CustomerservicecountSetterGetter
    }

   /* fun getServicesApiCall(context: Context,ReportMode: String?, ID_Branch: String?, strFromdate: String?, strTodate: String?, ID_Product: String?,
                           ID_NextAction: String?, ID_ActionType: String?, ID_Priority: String?, ID_Status: String?, GroupId: String?): MutableLiveData<ActionListTicketReportModel> {
        getActionListTicketReport(context,ReportMode,ID_Branch,strFromdate,strTodate,ID_Product,ID_NextAction,ID_ActionType,ID_Priority,ID_Status,GroupId)
        return CustomerservicecountSetterGetter
    }*/

    private fun getCustomerservicecount(context: Context, fkCust: String, fkOthercustomer: String, Prodid: String) {
        System.out.println("Datacount 1 :"+fkCust)
        System.out.println("Datacount 2 :"+fkOthercustomer)
        System.out.println("Datacount 3 :"+Prodid)
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



                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("79"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(Prodid))
                requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(fkCust))
                requestObject1.put("FK_CustomerOther", ProdsuitApplication.encryptStart(fkOthercustomer))
                requestObject1.put("BranchCode", ProdsuitApplication.encryptStart("3"))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("UserCode", null)))


                Log.e(TAG,"requestObject1   count   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getCustomerserviceregisterCount(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(CustomerservicecountRepository.TAG,"0974  response    "+response.body())
                        val jObject = JSONObject(response.body())
                        val count = ArrayList<CustomerservicecountModel>()
                        count.add(CustomerservicecountModel(response.body()))
                        val msg = count[0].message
                        CustomerservicecountSetterGetter.value = CustomerservicecountModel(msg)
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