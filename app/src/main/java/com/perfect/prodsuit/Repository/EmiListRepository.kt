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
import com.perfect.prodsuit.Model.EmiListModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object EmiListRepository {

    var TAG = "EmiListRepository"
    val emiListSetterGetter = MutableLiveData<EmiListModel>()
    private var progressDialog: ProgressDialog? = null

    fun getEmiList(context: Context,ReqMode : String,SubMode : String,ID_FinancePlanType : String,AsOnDate : String,ID_Category : String,ID_Area : String,Demand : String): MutableLiveData<EmiListModel> {
        getEmiLists(context,ReqMode,SubMode,ID_FinancePlanType,AsOnDate,ID_Category,ID_Area,Demand)
        return emiListSetterGetter
    }

    private fun getEmiLists(context: Context,ReqMode : String,SubMode : String,ID_FinancePlanType : String,AsOnDate : String,ID_Category : String,ID_Area : String,Demand : String) {
        try {
            emiListSetterGetter.value = EmiListModel("")
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
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
//
//                {"BankKey":"-500","Token":"1E018EC4-9978-4FCE-A763-455F59DF3540","ReqMode":"100","SubMode":"1",
//                    "FK_Company":"1","FK_BranchCodeUser":"3","EntrBy":"VYSHAKH","FK_Branch":"3","FromDate":"2023-05-04","ToDate":"2023-05-04",
//                    "Demand":"30","FK_FinancePlanType"::"2","FK_Product":"1","FK_Customer":"1","CedEMINo":"1233","FK_Area":"1","FK_Category":"1"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(SubMode))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser",null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("UserCode", null)))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(AsOnDate))
                requestObject1.put("ToDate", ProdsuitApplication.encryptStart(AsOnDate))
                requestObject1.put("Demand", ProdsuitApplication.encryptStart(Demand))
                requestObject1.put("FK_FinancePlanType", ProdsuitApplication.encryptStart(ID_FinancePlanType))
                requestObject1.put("FK_Area", ProdsuitApplication.encryptStart(ID_Area))
                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))


                Log.e(TAG,"requestObject1   82   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            Log.i("response2erer","body==="+requestObject1)
            val call = apiService.getEMICollectionList(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<EmiListModel>()
                        leads.add(EmiListModel(response.body()))
                        val msg = leads[0].message
                        emiListSetterGetter.value = EmiListModel(msg)
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