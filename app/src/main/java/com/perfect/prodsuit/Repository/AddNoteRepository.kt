package com.perfect.prodsuit.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.AddNoteModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.View.Activity.AccountDetailsActivity
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

object AddNoteRepository {

    val addnoteSetterGetter = MutableLiveData<AddNoteModel>()

    fun getServicesApiCall(context: Context): MutableLiveData<AddNoteModel> {
        getAddNote(context)
        return addnoteSetterGetter
    }

    private fun getAddNote(context: Context) {
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
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("32"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(AccountDetailsActivity.strid))
                Log.i("prodct",AccountDetailsActivity.strid)
                requestObject1.put("CustomerNote",  ProdsuitApplication.encryptStart("test"))
                requestObject1.put("EmployeeNote", ProdsuitApplication.encryptStart("test"))
                requestObject1.put("CusMensDate", ProdsuitApplication.encryptStart("23-04-2022"))
                Log.i("requestobject",requestObject1.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getAgentnote(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                        call: retrofit2.Call<String>, response:
                        Response<String>
                ) {
                    try {
                        val jObject = JSONObject(response.body())
                        Log.i("Addnote", response.body())
                        val users = ArrayList<AddNoteModel>()
                        users.add(AddNoteModel(response.body()))
                        val msg = users[0].message
                        addnoteSetterGetter.value = AddNoteModel(msg)
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
