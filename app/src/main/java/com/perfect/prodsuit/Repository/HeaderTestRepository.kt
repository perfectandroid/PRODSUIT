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
import com.perfect.prodsuit.Model.HeaderTestModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object HeaderTestRepository {

    private var progressDialog: ProgressDialog? = null
    val accBankBalanceSetGet = MutableLiveData<HeaderTestModel>()
    val TAG: String = "HeaderTestRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<HeaderTestModel> {
        getAccBankBalance(context)
        return accBankBalanceSetGet
    }

    private fun getAccBankBalance(context: Context) {
        try {
            accBankBalanceSetGet.value = HeaderTestModel("")
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

                 val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                val BankKeySP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
//                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
//                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                 val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
//                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                //  val ID_TokenUserSP = getSharedPreferences(Config.SHARED_PREF85, 0)

//                {"ReqMode":"130","FK_Company":"1","FK_Master":"40","EntrBy":"SONAKM"}

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("130"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("FK_Master", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))

                Log.e(TAG,"933331   getCRMTileDashBoardDetails  "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getUserDetailswithHeaderchecking("perfect_url",body)
            call!!.enqueue(object : retrofit2.Callback<String?> {
                override fun onResponse(call: Call<String?>?, response: Response<String?>) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"79992  "+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<HeaderTestModel>()
                        leads.add(HeaderTestModel(response.body()))
                        val msg = leads[0].message
                        accBankBalanceSetGet.value = HeaderTestModel(msg)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        Log.e(TAG,"79993  "+e.toString())
                    }
                }

                override fun onFailure(call: Call<String?>?, t: Throwable?) {
                    Log.e(TAG,"79994  "+t.toString())
                }
            })

//            call.enqueue(object : retrofit2.Callback<String> {
//                override fun onResponse(
//                    call: retrofit2.Call<String>, response:
//                    Response<String>
//                ) {
//                    try {
//                        progressDialog!!.dismiss()
//                        val jObject = JSONObject(response.body())
//                        val leads = ArrayList<HeaderTestModel>()
//                        leads.add(HeaderTestModel(response.body()))
//                        val msg = leads[0].message
//                        accBankBalanceSetGet.value = HeaderTestModel(msg)
//                    } catch (e: Exception) {
//                        progressDialog!!.dismiss()
//                        Log.e(TAG,"1151  "+e)
//                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
//                    }
//                }
//                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
//                    progressDialog!!.dismiss()
//                    Log.e(TAG,"1152  "+t)
//                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
//                }
//            })
        }catch (e : Exception){
            e.printStackTrace()
            Log.e(TAG,"1153  "+e)
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }
    }
}