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
import com.perfect.prodsuit.Model.BranchModel
import com.perfect.prodsuit.Model.CustomerBalanceModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

object CustomerBalanceRepository {

    private var progressDialog: ProgressDialog? = null
    val custbalSetterGetter = MutableLiveData<CustomerBalanceModel>()
    val TAG: String = "CustomerBalanceRepository"
    var result:String?=null
    fun getServicesApiCall(context: Context,TicketDate : String,FK_Cust:String): MutableLiveData<CustomerBalanceModel> {
        getCustomerBalance(context,TicketDate,FK_Cust)
        return custbalSetterGetter
    }

    private fun getCustomerBalance(context: Context, TicketDate: String, FK_Cust: String) {

        try {


            try {

                val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                val newDate: Date = sdf.parse(TicketDate)
//            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
                val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")

                result = sdfDate2.format(newDate)
                Log.i("newdate",result.toString())

            }catch (e :  Exception){

            }


            custbalSetterGetter.value = CustomerBalanceModel("")
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
                val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)


               // requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("62"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(FK_Cust))
                requestObject1.put("TransDate", ProdsuitApplication.encryptStart(result))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))



                Log.e(TAG,"78  getcustomerbal  "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
          //  var headers = {'Content-Type': 'application/json', 'Authorization': token,};

            val call = apiService.getCustomerBalance(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e("CustbalStatus  ",response.body())
                        val leads = ArrayList<BranchModel>()
                        leads.add(BranchModel(response.body()))
                        val msg = leads[0].message
                        custbalSetterGetter.value = CustomerBalanceModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
            progressDialog!!.dismiss()
        }
    }
}