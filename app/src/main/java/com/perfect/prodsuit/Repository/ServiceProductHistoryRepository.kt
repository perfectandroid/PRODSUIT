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
import com.perfect.prodsuit.Model.ServiceProductHistoryModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceProductHistoryRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceProductHistSetterGetter = MutableLiveData<ServiceProductHistoryModel>()
    val TAG: String = "ServiceProductHistoryRepository"
    var fk_cust: String = ""
    var fk_custother: String = ""
    fun getServicesApiCall(
        context: Context,
        ID_Category: String,
        ID_Product: String,
        Customer_Type: String,
        ID_Customer: String
    ): MutableLiveData<ServiceProductHistoryModel> {
        getServiceProductHistory(context,ID_Category, ID_Product, Customer_Type, ID_Customer)
        return serviceProductHistSetterGetter
    }

    private fun getServiceProductHistory(context: Context,ID_Category: String, ID_Product: String, Customer_Type: String, ID_Customer: String) {

        try {
            serviceProductHistSetterGetter.value = ServiceProductHistoryModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
                R.drawable.progress))
            progressDialog!!.setCanceledOnTouchOutside(false)
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

//                {"ReqMode":"445q+g40Nfs=","BankKey":"\/mXqmq3ZMvs=\n","Token":"R1+qLb1l9CrjrvoATU5QEw8o64+20c4sms4dhOjNZYuBYl8+NOw0hQ==","SubMode":"vJ/8asrP+O0=",
//                    "FK_Product":"vJ/8asrP+O0=","FK_Customer":"07/ybAx1yS4=","FK_CustomerOther":"KZtbclVmL7w=","FK_Branch":"8Ld7pH+WkK0=",
//                    "FK_Company":"vJ\/8asrP+O0=\n","EntrBy":"a5bTsgAqQ2o=\n"}



                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("71"))
               // requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart("2"))

                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                if (Customer_Type.equals("0")){

                    fk_cust=ID_Customer
                    fk_custother="0"
                    Log.e(CustomerServiceRegisterRepository.TAG,"642121   "+ID_Customer)
                 /*   requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(ID_Customer))
                    requestObject1.put("FK_CustomerOther", ProdsuitApplication.encryptStart("0"))*/
                }else if (Customer_Type.equals("1")){
                    Log.e(CustomerServiceRegisterRepository.TAG,"642122   "+ID_Customer)
                    fk_cust="0"
                    fk_custother=ID_Customer
                   /* requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOther", ProdsuitApplication.encryptStart(ID_Customer))*/
                }else{
                    fk_cust="0"
                    fk_custother="0"
                    Log.e(CustomerServiceRegisterRepository.TAG,"642123   "+ID_Customer)
                 /*   requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOther", ProdsuitApplication.encryptStart("0"))*/
                }
                requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(fk_cust))
                requestObject1.put("FK_CustomerOther", ProdsuitApplication.encryptStart(fk_custother))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("Criteria", ProdsuitApplication.encryptStart(ID_Category))

                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))


                Log.e(TAG,"requestObject1   product   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getProductHistory(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        progressDialog!!.dismiss()
                        Log.e(TAG,"response   911  "+response.body())
                        val jObject = JSONObject(response.body())
                        val customer = ArrayList<ServiceProductHistoryModel>()
                        customer.add(ServiceProductHistoryModel(response.body()))
                        val msg = customer[0].message
                        serviceProductHistSetterGetter.value = ServiceProductHistoryModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(TAG,"response   912  "+e.toString())
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_LONG)
                            .show()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    Log.e(TAG,"response   913  "+t.message)
                    progressDialog!!.dismiss()
//                    Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
//                        .show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Log.e(TAG,"response   914  "+e.toString())
//            Toast.makeText(context, ""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_LONG)
//                .show()
            progressDialog!!.dismiss()
        }
    }
}