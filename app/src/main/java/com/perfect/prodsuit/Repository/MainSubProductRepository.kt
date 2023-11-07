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
import com.perfect.prodsuit.Model.MainSubProductModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
import java.util.ArrayList

object MainSubProductRepository {

    private var progressDialog: ProgressDialog? = null
    val mainSubProductSetterGetter = MutableLiveData<MainSubProductModel>()
    val TAG: String = "MainSubProductRepository"

    fun getServicesApiCall(context: Context,iD_Prod : String,ReqMode : String): MutableLiveData<MainSubProductModel> {
        getMainSubProduct(context,iD_Prod,ReqMode)
        return mainSubProductSetterGetter
    }

    private fun getMainSubProduct(context: Context,iD_Prod : String,ReqMode : String) {

        try {
            mainSubProductSetterGetter.value = MainSubProductModel("")
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


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)

//                {"BankKey":"-500","Token":"C3B1C164-FA1D-4FA2-BD65-3049F80C394A","ReqMode":"115","FK_Product":"10716","FK_Company":"1"}

                //.....................
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(iD_Prod))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))

                Log.e(TAG,"84444   requestObject1   "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getProductInfo(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        Log.e(TAG,"response  94   "+response.body())
                        Log.i("response1122","responseBody==="+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val service = ArrayList<MainSubProductModel>()
                        service.add(MainSubProductModel(response.body()))
                        val msg = service[0].message
                        Log.i("response1122","msg==="+msg)
                        mainSubProductSetterGetter.value = MainSubProductModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
        }

//        try {
//            mainSubProductSetterGetter.value = MainSubProductModel("")
//            var msg = "{\n" +
//                    "  \"ServiceDetails\": {\n" +
//                    "    \"ServiceAttendedList\": [\n" +
//                    "      {\n" +
//                    "        \"SNo\": 1,\n" +
//                    "        \"MasterProduct\": \"\",\n" +
//                    "        \"FK_Product\": 557,\n" +
//                    "        \"FK_Category\": 37,\n" +
//                    "        \"Product\": \"Samsung Galaxy S21 5G\",\n" +
//                    "        \"Mode\": \"0\",\n" +
//                    "        \"BindProduct\": \"0\",\n" +
//                    "        \"bFK_Category\": 0,\n" +
//                    "        \"SerchSerialNo\": \"0\",\n" +
//                    "        \"ComplaintProduct\": \"1\",\n" +
//                    "        \"Warranty\": \"1 Year Warranty\",\n" +
//                    "        \"ServiceWarrantyExpireDate\": \"27-10-2024\",\n" +
//                    "        \"ReplacementWarrantyExpireDate\": \"27-04-2024\",\n" +
//                    "        \"ID_CustomerWiseProductDetails\": 11917,\n" +
//                    "        \"ServiceWarrantyExpired\": \"0\",\n" +
//                    "        \"ReplacementWarrantyExpired\": \"0\",\n" +
//                    "        \"ServiceAttendedListDet\": [\n" +
//                    "          {\n" +
//                    "            \"SNo\": 2,\n" +
//                    "            \"FK_Category\": 37,\n" +
//                    "            \"MasterProduct\": \"Samsung Galaxy S21 5G\",\n" +
//                    "            \"FK_Product\": 578,\n" +
//                    "            \"Product\": \"Samsung Galaxy Charger\",\n" +
//                    "            \"SLNo\": \"\",\n" +
//                    "            \"BindProduct\": \"1\",\n" +
//                    "            \"SerchSerialNo\": \"0\",\n" +
//                    "            \"ComplaintProduct\": \"0\",\n" +
//                    "            \"Warranty\": \"\",\n" +
//                    "            \"ServiceWarrantyExpireDate\": \"01-01-1900\",\n" +
//                    "            \"ReplacementWarrantyExpireDate\": \"01-01-1900\",\n" +
//                    "            \"ID_CustomerWiseProductDetails\": 11918,\n" +
//                    "            \"ServiceWarrantyExpired\": \"1\",\n" +
//                    "            \"ReplacementWarrantyExpired\": \"1\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"SNo\": 3,\n" +
//                    "            \"FK_Category\": 37,\n" +
//                    "            \"MasterProduct\": \"Samsung Galaxy S21 5G\",\n" +
//                    "            \"FK_Product\": 579,\n" +
//                    "            \"Product\": \"Samsung Galaxy Power Bank\",\n" +
//                    "            \"SLNo\": \"\",\n" +
//                    "            \"BindProduct\": \"1\",\n" +
//                    "            \"SerchSerialNo\": \"0\",\n" +
//                    "            \"ComplaintProduct\": \"0\",\n" +
//                    "            \"Warranty\": \"\",\n" +
//                    "            \"ServiceWarrantyExpireDate\": \"01-01-1900\",\n" +
//                    "            \"ReplacementWarrantyExpireDate\": \"01-01-1900\",\n" +
//                    "            \"ID_CustomerWiseProductDetails\": 11919,\n" +
//                    "            \"ServiceWarrantyExpired\": \"1\",\n" +
//                    "            \"ReplacementWarrantyExpired\": \"1\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"SNo\": 4,\n" +
//                    "            \"FK_Category\": 37,\n" +
//                    "            \"MasterProduct\": \"Samsung Galaxy S21 5G\",\n" +
//                    "            \"FK_Product\": 580,\n" +
//                    "            \"Product\": \"Samsung Galaxy Headset Wireless\",\n" +
//                    "            \"SLNo\": \"\",\n" +
//                    "            \"BindProduct\": \"1\",\n" +
//                    "            \"SerchSerialNo\": \"0\",\n" +
//                    "            \"ComplaintProduct\": \"0\",\n" +
//                    "            \"Warranty\": \"\",\n" +
//                    "            \"ServiceWarrantyExpireDate\": \"01-01-1900\",\n" +
//                    "            \"ReplacementWarrantyExpireDate\": \"01-01-1900\",\n" +
//                    "            \"ID_CustomerWiseProductDetails\": 11920,\n" +
//                    "            \"ServiceWarrantyExpired\": \"1\",\n" +
//                    "            \"ReplacementWarrantyExpired\": \"1\"\n" +
//                    "          }\n" +
//                    "        ]\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//
//            mainSubProductSetterGetter.value = MainSubProductModel(msg)
//
//        }catch (e : Exception){
//
//        }
    }
}