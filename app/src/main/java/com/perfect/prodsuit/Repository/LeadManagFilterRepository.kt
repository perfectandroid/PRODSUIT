package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.BranchModel
import com.perfect.prodsuit.Model.LeadManagmntFilterModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.LeadGenerationActivity
import com.perfect.prodsuit.View.Activity.OverDueActivity
import com.perfect.prodsuit.View.Activity.TodoListActivity
import com.perfect.prodsuit.View.Activity.UpcomingtaskActivity
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.ArrayList

object LeadManagFilterRepository {

    private var progressDialog: ProgressDialog? = null
    val leadmangfilterSetterGetter = MutableLiveData<LeadManagmntFilterModel>()
    val TAG: String = "LeadMangfilter"
    var submode1=TodoListActivity.submode
    var submode2=OverDueActivity.submode
    var submode3=UpcomingtaskActivity.submode

    fun getServicesApiCall(context: Context): MutableLiveData<LeadManagmntFilterModel> {
        getLeadFilter(context)
        return leadmangfilterSetterGetter
    }

    private fun getLeadFilter(context: Context) {
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
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("24"))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
                if (!submode1.equals(""))
                {
                    requestObject1.put("SubMode", ProdsuitApplication.encryptStart("1"))
                }
                if (!submode2.equals(""))
                {
                    requestObject1.put("SubMode", ProdsuitApplication.encryptStart("2"))
                }
                if (!submode3.equals(""))
                {
                    requestObject1.put("SubMode", ProdsuitApplication.encryptStart("3"))
                }

                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("Name", ProdsuitApplication.encryptStart("name"))

                val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")



                var nxtactndate = ""
               val dateFrom = inputFormat.parse(nxtactndate)
               // val dateFrom = inputFormat.parse("08-04-2022")
                val strNxtactDate = outputFormat.format(dateFrom)




                requestObject1.put("Todate", ProdsuitApplication.encryptStart(strNxtactDate))
                Log.i("request filter",requestObject1.toString()+ submode2+"\n"+strNxtactDate)



            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getLeadManagementDetailsList(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<BranchModel>()
                        leads.add(BranchModel(response.body()))
                        val msg = leads[0].message
                        leadmangfilterSetterGetter.value =
                         LeadManagmntFilterModel(msg)
                    } catch (e: Exception) {
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