package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.LoginModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.LoginActivity.Companion.strEPhone
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

object LoginActivityRepository {

    private var progressDialog: ProgressDialog? = null
    val loginSetterGetter = MutableLiveData<LoginModel>()

    fun getServicesApiCall(context: Context): MutableLiveData<LoginModel> {
        doLogin(context)
        return loginSetterGetter
    }

    private fun doLogin(context: Context) {
        try {
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            val CERT_NAMESP = context.getSharedPreferences(Config.SHARED_PREF8, 0)
            val BANK_KEYSP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
            val TestingURLpref = context.getSharedPreferences(Config.SHARED_PREF10, 0)
            val TestingMobileNopref = context.getSharedPreferences(Config.SHARED_PREF11, 0)
            val TestingBankKeypref = context.getSharedPreferences(Config.SHARED_PREF12, 0)
            val Testingsslcertificatepref = context.getSharedPreferences(Config.SHARED_PREF13, 0)
            val Loginmobilenumberpref = context.getSharedPreferences(Config.SHARED_PREF14, 0)
            if(TestingMobileNopref.getString("TestingMobileNo", null)!=null
                && strEPhone.equals(TestingMobileNopref.getString("TestingMobileNo", null))){
                val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
                val BASE_URLEditer = BASE_URLSP.edit()
                BASE_URLEditer.putString("BASE_URL", TestingURLpref.getString("TestingURL", null))
                BASE_URLEditer.commit()
                val CERT_NAMESP = context.getSharedPreferences(Config.SHARED_PREF8, 0)
                val CERT_NAMEEditer = CERT_NAMESP.edit()
                CERT_NAMEEditer.putString("CERT_NAME",Testingsslcertificatepref.getString("Testingsslcertificate", null))
                CERT_NAMEEditer.commit()
                val BANK_KEYESP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val BANK_KEYEditer = BANK_KEYESP.edit()
                BANK_KEYEditer.putString("BANK_KEY", TestingBankKeypref.getString("TestingBankKey", null))
                BANK_KEYEditer.commit()
            }else{
                val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
                val BASE_URLEditer = BASE_URLSP.edit()
                BASE_URLEditer.putString("BASE_URL", BASE_URLSP.getString("BASE_URL", null))
                BASE_URLEditer.commit()
                val CERT_NAMESP = context.getSharedPreferences(Config.SHARED_PREF8, 0)
                val CERT_NAMEEditer = CERT_NAMESP.edit()
                CERT_NAMEEditer.putString("CERT_NAME",CERT_NAMESP.getString("CERT_NAME", null))
                CERT_NAMEEditer.commit()
                val BANK_KEYESP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val BANK_KEYEditer = BANK_KEYESP.edit()
                BANK_KEYEditer.putString("BANK_KEY", BANK_KEYSP.getString("BANK_KEY", null))
                BANK_KEYEditer.commit()
            }
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
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("2"))
                requestObject1.put("MobileNumber", ProdsuitApplication.encryptStart(strEPhone))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getLogin(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val users = ArrayList<LoginModel>()
                        users.add(LoginModel(response.body()))
                        val msg = users[0].message
                        loginSetterGetter.value = LoginModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
         }
        catch (e: Exception) {
            e.printStackTrace()
            progressDialog!!.dismiss()
        }
    }

}

