package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.ProductEnquiryDetailModel
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList


object ProductEnquiryDetailRepository {

    private var progressDialog: ProgressDialog? = null
    val productEnquiryDetailSetterGetter = MutableLiveData<ProductEnquiryDetailModel>()
    val TAG: String = "ProductEnquiryDetailRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ProductEnquiryDetailModel> {
        getProductEnquiryDetail(context)
        return productEnquiryDetailSetterGetter
    }

    private fun getProductEnquiryDetail(context: Context) {

        try {
            productEnquiryDetailSetterGetter.value = ProductEnquiryDetailModel("")
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
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("12"))

                Log.e(BannersRepository.TAG,"requestObject1   545   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getBannerDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        val jObject = JSONObject(response.body())
                        Log.e(BannersRepository.TAG,"banner response"+response.body())
                        val users = ArrayList<ProductEnquiryDetailModel>()
                        users.add(ProductEnquiryDetailModel(response.body()))
                        val msg = users[0].message
                        productEnquiryDetailSetterGetter.value = ProductEnquiryDetailModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Toast.makeText(context,""+e.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    // Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }
        catch (e: Exception) {
            e.printStackTrace()
            //  Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
        }
    }
}