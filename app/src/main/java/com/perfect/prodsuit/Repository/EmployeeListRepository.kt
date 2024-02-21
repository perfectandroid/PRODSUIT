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
import com.perfect.prodsuit.Model.AreaListModel
import com.perfect.prodsuit.Model.EmployeeListModel
import com.perfect.prodsuit.Model.LeadAllDetailsModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object EmployeeListRepository {

    val employeelistSetterGetter = MutableLiveData<EmployeeListModel>()
    var progressDialog: ProgressDialog? = null
    val TAG: String = "EmployeeListRepository"

    fun getServicesApiCall(context: Context, ID_Employee: String): MutableLiveData<EmployeeListModel> {
        getEmployeeList(context, ID_Employee)
        return employeelistSetterGetter
    }

    private fun getEmployeeList(context: Context,ID_Employee: String){
        try {
            employeelistSetterGetter.value = EmployeeListModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
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


//                "ReqMode":"41",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg
//                "Id_Agenda",1



                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39,0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36,0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("65"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

//                Log.e(AgendaActionRepository.TAG,"Id_Agenda        8222   ")
                Log.e(TAG,"requestObject1   010101   "+ID_Employee)
                Log.e(TAG,"requestObject1   02020   "+requestObject1)
                Log.e(TAG,"requestObject1   03030   "+TokenSP.getString("Token", null))
                Log.e(TAG,"requestObject1   04040   "+BankKeySP.getString("BANK_KEY", null))
                Log.e(TAG,"requestObject1   05050   "+FK_CompanySP.getString("FK_Company", null))
                Log.e(TAG,"requestObject1   06060   "+UserCodeSP.getString("UserCode", null))

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getEmployeeAllDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"3333 response   "+response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<EmployeeListModel>()
                        leads.add(EmployeeListModel(response.body()))
                        val msg = leads[0].message
                        employeelistSetterGetter.value = EmployeeListModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AreaListRepository.progressDialog!!.dismiss()
                        Toast.makeText(context, Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Toast.makeText(context, Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
            progressDialog!!.dismiss()
        }
    }

}