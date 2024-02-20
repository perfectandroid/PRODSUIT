package com.perfect.prodsuit.Repository

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.SaveDocumentModel
import com.perfect.prodsuit.Model.SaveNewActionModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object SaveDocumentRepository {

    private var progressDialog: ProgressDialog? = null
    val saveDocssetterGetter = MutableLiveData<SaveDocumentModel>()
    val TAG: String = "SaveDocumentRepository"

    fun getServicesApiCall(context: Context, ID_LeadGenerateProduct : String, strDate : String, strSubject : String, strDescription : String,
                           encodeDoc : String,extension: String): MutableLiveData<SaveDocumentModel> {
        saveDocuments(context, ID_LeadGenerateProduct, strDate,strSubject, strDescription, encodeDoc,extension)
        return saveDocssetterGetter
    }

    private fun saveDocuments(context: Context, ID_LeadGenerateProduct: String, strDate: String, strSubject: String, strDescription: String, encodeDoc: String,extension: String) {


        Log.e(TAG,"Validations  382"
                +"\n"+"idLeadgenerateproduct     : "+ ID_LeadGenerateProduct
                +"\n"+"strDate                   : "+ strDate
                +"\n"+"strSubject                : "+ strSubject
                +"\n"+"strDescription            : "+ strDescription
                +"\n"+"encodeDoc                 : "+ encodeDoc
                +"\n"+"extension                 : "+ extension)

        try {
            saveDocssetterGetter.value = SaveDocumentModel("")
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


//                "ReqMode":"40",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "ID_LeadGenerateProduct":1,
//                "Doc_Date":"02-04-2022",
//                "Doc_Subject":"Subject",
//                "Doc_Description":"1",
//                "DocumentImage":"dfsfreete"


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("40"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))

                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(ID_LeadGenerateProduct))
                requestObject1.put("Doc_Date", ProdsuitApplication.encryptStart(strDate))
                requestObject1.put("Doc_Subject", ProdsuitApplication.encryptStart(strSubject))
                requestObject1.put("Doc_Description", ProdsuitApplication.encryptStart(strDescription))
//                requestObject1.put("DocumentImage", ProdsuitApplication.encryptStart(encodeDoc))
                requestObject1.put("DocumentImage", encodeDoc)
                requestObject1.put("DocImageFormat", ProdsuitApplication.encryptStart(extension))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))


                Log.e(TAG,"saveDocuments   102   "+encodeDoc)
                Log.e(TAG,"saveDocuments   102   "+requestObject1)
                Log.e(TAG,"saveDocuments   102   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.saveAddDocument(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        Log.e(TAG,"saveAddNewAction  118   "+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val nxtAction = ArrayList<SaveDocumentModel>()
                        nxtAction.add(SaveDocumentModel(response.body()))
                        val msg = nxtAction[0].message
                        saveDocssetterGetter.value = SaveDocumentModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, ""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()

                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()

        }

    }

}