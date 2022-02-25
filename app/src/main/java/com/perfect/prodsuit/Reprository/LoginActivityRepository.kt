package com.perfect.prodsuit.Reprository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.LoginModel
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

object LoginActivityRepository {

    val loginSetterGetter = MutableLiveData<LoginModel>()

    fun getServicesApiCall(context: Context): MutableLiveData<LoginModel> {
        doLogin(context)
        return loginSetterGetter
    }

    private fun doLogin(context: Context) {
        try {
            val client = OkHttpClient.Builder()
                .sslSocketFactory(Config.getSSLSocketFactory(context))
                .hostnameVerifier(Config.getHostnameVerifier())
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            val apiService = retrofit.create(ApiInterface::class.java!!)
            val requestObject1 = JSONObject()
            try {
                requestObject1.put("LoginMode", "1")
                requestObject1.put("UserName", "sree")
                requestObject1.put("UserPassword", "123456")
                requestObject1.put("IMEI_Number", "123123123123")
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
                        val jObject = JSONObject(response.body())
                        val users = ArrayList<LoginModel>()
                        users.add(LoginModel(response.body()))

                        val msg = users[0].message
                        loginSetterGetter.value = LoginModel(msg)
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

