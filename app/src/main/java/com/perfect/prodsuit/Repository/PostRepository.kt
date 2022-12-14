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
import com.perfect.prodsuit.Model.PostModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.LeadGenerationActivity
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object PostRepository {

    private var progressDialog: ProgressDialog? = null
    val postSetterGetter = MutableLiveData<PostModel>()
    val TAG: String = "PostRepository"

    fun getServicesApiCall(context: Context,FK_Area :String): MutableLiveData<PostModel> {
        getPost(context, LeadGenerationActivity.FK_Area)
        return postSetterGetter
    }

    private fun getPost(context: Context,FK_Area :String) {

        Log.e("TAG","getPost  ")
        try {
            postSetterGetter.value = PostModel("")
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

//                "ReqMode":"34",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "SubMode:"4",
//                "FK_District":"1"

                var FK_Area_new = "0"
                if (!FK_Area.equals("")){
                    FK_Area_new = FK_Area
                }

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("34"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart("4"))
              //  requestObject1.put("FK_Area", ProdsuitApplication.encryptStart(FK_Area))
                requestObject1.put("FK_Area", ProdsuitApplication.encryptStart(FK_Area_new))

                Log.e(TAG,"FK_Area   74   "+ FK_Area+"  :  ")
                Log.e(TAG,"FK_Area_new   74   "+ FK_Area_new+"  :  ")
                Log.e(TAG,"requestObject1   74   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getPostDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val post = ArrayList<PostModel>()
                        post.add(PostModel(response.body()))
                        val msg = post[0].message
                        postSetterGetter.value = PostModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(
                            context,
                            ""+Config.SOME_TECHNICAL_ISSUES,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(
                        context,
                        ""+Config.SOME_TECHNICAL_ISSUES,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(
                context,
                ""+Config.SOME_TECHNICAL_ISSUES,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}