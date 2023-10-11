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
import com.perfect.prodsuit.Model.ServiceCountModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceCountRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceCountSetterGetter = MutableLiveData<ServiceCountModel>()
    val TAG: String = "ServiceCountRepository"

    fun getServicesApiCall(context: Context,ID_Branch : String,FK_Area : String,ID_Employee : String,strFromDate : String,strToDate : String,strCustomer : String,
                           strMobile : String,strTicketNo : String,strDueDays : String): MutableLiveData<ServiceCountModel> {
        getServiceCount(context, ID_Branch,FK_Area,ID_Employee,strFromDate,strToDate,strCustomer,strMobile,strTicketNo,strDueDays)
        return serviceCountSetterGetter
    }

    private fun getServiceCount(context: Context,ID_Branch : String,FK_Area : String,ID_Employee : String,strFromDate : String,strToDate : String,strCustomer : String,
                                strMobile : String,strTicketNo : String,strDueDays : String) {
        try {
            serviceCountSetterGetter.value = ServiceCountModel("")
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
                val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)

//                {"BankKey":"\/mXqmq3ZMvs=\n","Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","SubMode":"vJ/8asrP+O0=",
//                    "FK_Branch":"KZtbclVmL7w=","FK_Product":"KZtbclVmL7w=",
//                    "FK_ComplaintType":"KZtbclVmL7w=","Status":"4loivAI89ZU=","FK_Company":"vJ\/8asrP+O0=\n","SortOrder":"KZtbclVmL7w=",
//                    "FK_Post":"KZtbclVmL7w=", "FK_Area":"KZtbclVmL7w=",
//                    "FK_Employee":"KZtbclVmL7w=", "DueDays":"KZtbclVmL7w=", "EntrBy":"a5bTsgAqQ2o=", "FromDate":"",
//                    "Todate":"", "CSRTickno":"", "CusName":"", "CusMobile":""
//                }

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart("1"))
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


                Log.e(TAG,"78  getBranch  "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getServiceCountDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"  114 COUNTS "+response.body())
                        val leads = ArrayList<ServiceCountModel>()
                        leads.add(ServiceCountModel(response.body()))
                        val msg = leads[0].message
                        serviceCountSetterGetter.value = ServiceCountModel(msg)
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