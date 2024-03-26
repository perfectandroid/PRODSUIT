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
import com.perfect.prodsuit.Model.LeadHistModel

import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object LeadHistRepository {

    private var progressDialog: ProgressDialog? = null
    val leadHistSetterGetter = MutableLiveData<LeadHistModel>()
    val TAG: String = "LeadHistRepository"

    fun getServicesApiCall(
        context: Context,
        ID_LeadSource: String,
        ID_LeadInfo: String,
        fromDate1: String,
        toDate1: String,
        ID_Category: String?,
        ID_ProductType: String?,
        ID_Product: String,
        ID_Employee: String,
        ID_CollectedBy: String?,
        ID_Area: String?,
        ID_NextAction: String,
        ID_ActionType: String,
        ID_Priority: String?,
        ID_SearchBy: String?,
        searchDetails: String?,
        Transmode: String?,
        prodName: String
    ): MutableLiveData<LeadHistModel> {
        getLeadHist(context,
            ID_LeadSource,
            ID_LeadInfo,fromDate1,
            toDate1,
            ID_Category,
            ID_ProductType,
            ID_Product,
            ID_Employee,
            ID_CollectedBy,
            ID_Area,
            ID_NextAction,
            ID_ActionType,
            ID_Priority,
            ID_SearchBy,
            searchDetails,
            Transmode,
            prodName
            )
        return leadHistSetterGetter
    }

    private fun getLeadHist(
        context: Context,
        ID_LeadSource: String,
        ID_LeadInfo: String,
        fromDate1: String,
        toDate1: String,
        ID_Category: String?,
        ID_ProductType: String?,
        ID_Product: String,
        ID_Employee: String,
        ID_CollectedBy: String?,
        ID_Area: String?,
        ID_NextAction: String,
        ID_ActionType: String,
        ID_Priority: String?,
        ID_SearchBy: String?,
        searchDetails: String?,
        Transmode: String?,
        prodName: String,

        ) {

        try {
            leadHistSetterGetter.value = LeadHistModel("")
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


//                {"ReqMode":"rTyE34GbkJc=","BankKey":"\/mXqmq3ZMvs=\n","FK_Employee":"07\/ybAx1yS4=\n","FK_Company":"vJ\/8asrP+O0=\n",
//                    "Token":"Jn9nvIP\/Ms4F16TNnZe3H9KH0+x+sqTJl6afLjsUBvaKVwPJaIvQvQ==\n"}
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("129"))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))

                requestObject1.put("ID_LeadFrom", ProdsuitApplication.encryptStart(ID_LeadSource))
                requestObject1.put("FK_LeadThrough", ProdsuitApplication.encryptStart(ID_LeadInfo))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(fromDate1))
                requestObject1.put("ToDate", ProdsuitApplication.encryptStart(toDate1))
                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("ProdType", ProdsuitApplication.encryptStart(ID_ProductType))
                requestObject1.put("ID_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("Collectedby_ID", ProdsuitApplication.encryptStart(ID_CollectedBy))
                requestObject1.put("Area_ID", ProdsuitApplication.encryptStart(ID_Area))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_NetAction", ProdsuitApplication.encryptStart(ID_NextAction))
                requestObject1.put("FK_ActionType", ProdsuitApplication.encryptStart(ID_ActionType))
                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
                requestObject1.put("SearchBy", ProdsuitApplication.encryptStart(ID_SearchBy))
                requestObject1.put("SearchBydetails", ProdsuitApplication.encryptStart(searchDetails))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(Transmode))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("ProdName", ProdsuitApplication.encryptStart(prodName))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))





                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("UserCode", null)))




                Log.e(TAG,"78144  history    "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getLeadHistDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"histtttt   782  "+response.body())
                        val leads = ArrayList<LeadHistModel>()
                        leads.add(LeadHistModel(response.body()))
                        val msg = leads[0].message
                        leadHistSetterGetter.value = LeadHistModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
            progressDialog!!.dismiss()
        }

    }
}