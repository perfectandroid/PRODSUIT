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
import com.perfect.prodsuit.Model.EmiCountModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object EmiCountRepository {

    var TAG = "EmiCountRepository"
    val emiCountSetterGetter = MutableLiveData<EmiCountModel>()
    private var progressDialog: ProgressDialog? = null
    fun getServicesApiCall(context: Context, ID_FinancePlanType : String,AsOnDate : String,ID_Category : String,ID_Area : String,Demand : String): MutableLiveData<EmiCountModel> {
        getEmiCount(context, ID_FinancePlanType,AsOnDate,ID_Category,ID_Area,Demand)
        return emiCountSetterGetter
    }

    private fun getEmiCount(context: Context, ID_FinancePlanType : String,AsOnDate : String,ID_Category : String,ID_Area : String,Demand : String) {

        try {
            emiCountSetterGetter.value = EmiCountModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
                R.drawable.progress))
            progressDialog!!.setCanceledOnTouchOutside(false)
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



//                {"BankKey":"-500","Token":"1E018EC4-9978-4FCE-A763-455F59DF3540","ReqMode":"100","FK_Company":"1","FK_BranchCodeUser":"3",
//                    "EntrBy":"VYSHAKH","FK_Branch":"3","FromDate":"2023-05-04","ToDate":"2023-05-04","Demand":"100","FK_FinancePlanType":"","FK_Area":"1","FK_Category":"1"}

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("100"))
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
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"requestObject1   901   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            Log.i("response999", "requestObject1==" +requestObject1 )
            val call = apiService.getEMICollectionReportCount(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        progressDialog!!.dismiss()
                        Log.e(TAG,"response   911  "+response.body())
                        val jObject = JSONObject(response.body())
                        val customer = ArrayList<EmiCountModel>()
                        customer.add(EmiCountModel(response.body()))
                        val msg = customer[0].message
                        emiCountSetterGetter.value = EmiCountModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(TAG,"response   912  "+e.toString())
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_LONG)
                            .show()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    Log.e(TAG,"response   913  "+t.message)
                    progressDialog!!.dismiss()
//                    Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
//                        .show()
                }
            })
        }
        catch (e : Exception){
            e.printStackTrace()
            Log.e(TAG,"response   914  "+e.toString())
//            Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
//                .show()
            progressDialog!!.dismiss()
        }
    }
}