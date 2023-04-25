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
import com.perfect.prodsuit.Model.ServiceSalesModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceSalesRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceSalesSetterGetter = MutableLiveData<ServiceSalesModel>()
    val TAG: String = "ServiceSalesRepository"

    fun getServicesApiCall(context: Context,ID_Product : String, Customer_Type: String, ID_Customer: String): MutableLiveData<ServiceSalesModel> {
        getServiceSalesSetterGetter(context,ID_Product,Customer_Type,ID_Customer)
        return serviceSalesSetterGetter
    }

    private fun getServiceSalesSetterGetter(context: Context,ID_Product : String, Customer_Type: String, ID_Customer: String) {

//        val msg = "{\"SalesHistoryDetails\": {\"SalesHistoryDetailsList\": [{\"InvoiceNo\":\"123\",\"InvoiceDate\": \"06/06/2022\",\"Dealer\": \"Head Office Chalappuram\",\"Product\": \"Inverter - 1050 VA\",\"Quatity\": \"10\"},{\"InvoiceNo\":\"124\",\"InvoiceDate\": \"07/06/2022\",\"Dealer\": \"Head Office Chalappuram\",\"Product\": \"Solar Panel\",\"Quatity\": \"10\"},{\"InvoiceNo\":\"125\",\"InvoiceDate\": \"08/06/2022\",\"Dealer\": \"Head Office Chalappuram\",\"Product\": \"Inverter - 1050 VA\",\"Quatity\": \"10\"},{\"InvoiceNo\":\"126\",\"InvoiceDate\": \"09/06/2022\",\"Dealer\": \"Head Office Chalappuram\",\"Product\": \"500VA+1200AH\",\"Quatity\": \"10\"}],\"ResponseCode\": \"0\",\"ResponseMessage\": \"Transaction Verified\"},\"StatusCode\": 0,\"EXMessage\": \"Transaction Verified\"}"
//        serviceSalesSetterGetter.value = ServiceSalesModel(msg)


        try {
            serviceSalesSetterGetter.value = ServiceSalesModel("")
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

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("71"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart("3"))

                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart("0"))
                if (Customer_Type.equals("0")){
                    Log.e(CustomerServiceRegisterRepository.TAG,"642121   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(ID_Customer))
                    requestObject1.put("FK_CustomerOther", ProdsuitApplication.encryptStart("0"))
                }else if (Customer_Type.equals("1")){
                    Log.e(CustomerServiceRegisterRepository.TAG,"642122   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOther", ProdsuitApplication.encryptStart(ID_Customer))
                }else{
                    Log.e(CustomerServiceRegisterRepository.TAG,"642123   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOther", ProdsuitApplication.encryptStart("0"))
                }

                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))


                Log.e(TAG,"requestObject1   1071   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getSalesHistory(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        progressDialog!!.dismiss()
                        Log.e(TAG,"response   911  "+response.body())
                        val jObject = JSONObject(response.body())
                        val customer = ArrayList<ServiceSalesModel>()
                        customer.add(ServiceSalesModel(response.body()))
                        val msg = customer[0].message
                        serviceSalesSetterGetter.value = ServiceSalesModel(msg)
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