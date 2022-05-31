package com.perfect.prodsuit.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.ResellerModel
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

object SplashresellerActivityRepository {

    val resellerSetterGetter = MutableLiveData<ResellerModel>()

    fun getServicesApiCall(context: Context): MutableLiveData<ResellerModel> {
        doReseller(context)
        return resellerSetterGetter
    }

    private fun doReseller(context: Context) {
        try {
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
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
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("1"))
                Log.e("SplashresellerActivityRepository","requestObject1   52   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getResellerData(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        val jObject = JSONObject(response.body())
                        Log.i("Splashresposne",response.body())
                        val users = ArrayList<ResellerModel>()
                        users.add(ResellerModel(response.body()))
                        val msg = users[0].message
                        resellerSetterGetter.value = ResellerModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                }
            })
         }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

