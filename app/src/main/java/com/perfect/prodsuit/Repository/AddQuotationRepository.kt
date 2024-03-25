package com.perfect.prodsuit.Repository

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.AddNoteModel
import com.perfect.prodsuit.Model.AddQuotationModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AccountDetailsActivity
import com.perfect.prodsuit.View.Activity.AddNoteActivity
import com.perfect.prodsuit.View.Activity.AddQuotationActivity
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.*

object AddQuotationRepository {

    val addquotationSetterGetter = MutableLiveData<AddQuotationModel>()
    private var progressDialog: ProgressDialog? = null
    private var fileimg: File? = null
    fun getServicesApiCall(context: Context): MutableLiveData<AddQuotationModel> {
        var s =AddQuotationActivity.imgpth
        fileimg = File(s)
        Log.i("Fileimg", fileimg.toString())
        getAddQuotation(context)
        return addquotationSetterGetter
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getAddQuotation(context: Context) {
        try {
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
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("44"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(AccountDetailsActivity.strid))
                Log.i("prodct",AccountDetailsActivity.strid)
                requestObject1.put("TrnsDate",  ProdsuitApplication.encryptStart(AddQuotationActivity.transdate))
                requestObject1.put("QuotationImge", ProdsuitApplication.encryptStart(""))
                requestObject1.put("Remark", ProdsuitApplication.encryptStart(AddQuotationActivity.remarks))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                Log.i("requestobject",requestObject1.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )

                Log.e("jsondat file", "DATA    $body")

              val requestFile = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), fileimg)

                var imageFiles: MultipartBody.Part? = null
                imageFiles = MultipartBody.Part.createFormData("QuotationImge",  ProdsuitApplication.encryptStart(fileimg!!.name),requestFile)


               Log.e("jsondat ", "BODY $imageFiles")

           // val call = apiService.getquotation(body)
            val call = apiService.getquotation(body,imageFiles)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("Addquotation", response.body())
                        val users = ArrayList<AddQuotationModel>()
                        users.add(AddQuotationModel(response.body()))
                        val msg = users[0].message
                        addquotationSetterGetter.value = AddQuotationModel(msg)
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
        }
    }

}

