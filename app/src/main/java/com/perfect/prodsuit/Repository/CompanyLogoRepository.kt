package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.ActionListTicketReportModel
import com.perfect.prodsuit.Model.AddQuotationModel
import com.perfect.prodsuit.Model.CompanyCodeModel
import com.perfect.prodsuit.Model.CompanyLogomodel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AccountDetailsActivity
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
import java.util.ArrayList

object CompanyLogoRepository {

    var TAG = "CompanyLogoRepository"
    val addcompanylogoSetterGetter = MutableLiveData<CompanyLogomodel>()
    private var progressDialog: ProgressDialog? = null
    private var fileimg: File? = null
    fun getServicesApiCall(context: Context): MutableLiveData<CompanyLogomodel> {
     /*   var s = AddQuotationActivity.imgpth
        fileimg = File(s)
        Log.i("Fileimg", fileimg.toString())*/
        getAddCompanyLogo(context)
        return addcompanylogoSetterGetter
    }


    private fun getAddCompanyLogo(context: Context) {
        try {
            CompanyCodeRepository.companyCodeSetterGetter.value = CompanyCodeModel("")
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
                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("72"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_User", ProdsuitApplication.encryptStart(ID_UserSP.getString("ID_User", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))


                Log.e(TAG,"requestObject companycode  er   "+requestObject1)
                Log.i("jhghgghghg","logo====   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getCompanyLogo(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        val jObject = JSONObject(response.body())
                        Log.i("companylogo response",response.body())

                        val logos = ArrayList<CompanyLogomodel>()
                        logos.add(CompanyLogomodel(response.body()))
                        val msg = logos[0].message
                        addcompanylogoSetterGetter.value = CompanyLogomodel(msg)

                     /*
                        val jsonObj: JSONObject = jObject.getJSONObject("CompanyLogDetails")
                        var logo = jsonObj.getString("CompanyLogo"); //
                        Log.i("TAG","Checking"+logo);*/
                      //  Log.i("TAG","LOGO"+logo);
                    } catch (e: Exception) {
                        Log.e(TAG,"1080   "+e)
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    Log.e(TAG,"1081   "+t.message)
                }
            })
        }
        catch (e: Exception) {
            Log.e(TAG,"1082   "+e)
            e.printStackTrace()
        }
    }

}