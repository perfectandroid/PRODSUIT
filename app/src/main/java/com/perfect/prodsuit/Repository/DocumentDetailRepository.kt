package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.AgendaDetailModel
import com.perfect.prodsuit.Model.DocumentDetailModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object DocumentDetailRepository {

    private var progressDialog: ProgressDialog? = null
    val documentDetailModelSetterGetter = MutableLiveData<DocumentDetailModel>()
    val TAG: String = "DocumentDetailRepository"

    fun getServicesApiCall(context: Context, ID_LeadGenerate : String, ID_LeadGenerateProduct : String): MutableLiveData<DocumentDetailModel> {
        getDocumentDetail(context, ID_LeadGenerate, ID_LeadGenerateProduct)
        return documentDetailModelSetterGetter
    }

    private fun getDocumentDetail(context: Context, ID_LeadGenerate: String, ID_LeadGenerateProduct: String) {
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

//                "ReqMode":"47",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "ID_LeadGenerate":1
//                "ID_LeadGenerateProduct":1


//                IAMGEDETAILS
//                "ReqMode":"47",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "ID_LeadGenerateProduct":1
//                "ID_LeadGenerate:"1"
//                "ID_LeadDocumentDetails:"1"


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("47"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_LeadGenerate", ProdsuitApplication.encryptStart(ID_LeadGenerate))
                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(ID_LeadGenerateProduct))


//                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("47"))
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                requestObject1.put("ID_LeadGenerate", ProdsuitApplication.encryptStart(ID_LeadGenerate))
//                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(ID_LeadGenerateProduct))
//                requestObject1.put("ID_LeadDocumentDetails", ProdsuitApplication.encryptStart("7"))

                Log.e(TAG,"Id_Agenda   78   "+ID_LeadGenerate+"  ::::  "+ID_LeadGenerateProduct)
                Log.e(TAG,"requestObject1   78   "+requestObject1)
               // Log.e(TAG,"ID_LeadGenerateProduct   78   "+ID_LeadGenerateProduct)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getDocumentDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<DocumentDetailModel>()
                        leads.add(DocumentDetailModel(response.body()))
                        val msg = leads[0].message
                        documentDetailModelSetterGetter.value = DocumentDetailModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
        }
    }
}