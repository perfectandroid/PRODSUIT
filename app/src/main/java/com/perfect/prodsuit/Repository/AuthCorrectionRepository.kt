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
import com.perfect.prodsuit.Model.AuthCorrectionModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object AuthCorrectionRepository {

    private var progressDialog: ProgressDialog? = null
    val authCorrectionSetterGetter = MutableLiveData<AuthCorrectionModel>()
    val TAG: String = "AuthCorrectionRepository"

    fun getServicesApiCall(context: Context, AuthID : String, ID_Reason : String, strReason : String,ActiveCorrectionOption: String): MutableLiveData<AuthCorrectionModel> {
        getAuthorizationCorrection(context, AuthID,ID_Reason,strReason,ActiveCorrectionOption)
        return authCorrectionSetterGetter
    }

    private fun getAuthorizationCorrection(context: Context, authID: String, ID_Reason : String, strReason : String,ActiveCorrectionOption : String) {
        try {
            authCorrectionSetterGetter.value = AuthCorrectionModel("")
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

//                {"BankKey":"-500","Token":"CBC45CCD-349B-492B-A513-B97B5AD9B61F","FK_Company":"1","AuthID":"17","EntrBy":"VYSHAKH","FK_Reason":"","Reason":""}


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("AuthID", ProdsuitApplication.encryptStart(authID))
                requestObject1.put("FK_Reason", ProdsuitApplication.encryptStart(ID_Reason))
                requestObject1.put("Reason", ProdsuitApplication.encryptStart(strReason))
                requestObject1.put("SkipPrev", ProdsuitApplication.encryptStart(ActiveCorrectionOption))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))


                Log.e(TAG,"requestObject1   8100   "+requestObject1)
                Log.e(TAG,"requestObject1   8100   "+ActiveCorrectionOption)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getAuthorizationCorrection(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val country = ArrayList<AuthCorrectionModel>()
                        country.add(AuthCorrectionModel(response.body()))
                        val msg = country[0].message
                        authCorrectionSetterGetter.value = AuthCorrectionModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(
                            context,
                            ""+ Config.SOME_TECHNICAL_ISSUES,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(
                        context,
                        ""+ Config.SOME_TECHNICAL_ISSUES,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(
                context,
                ""+ Config.SOME_TECHNICAL_ISSUES,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}