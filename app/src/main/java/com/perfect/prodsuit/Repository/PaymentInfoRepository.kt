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

import com.perfect.prodsuit.Model.PaymentInfoModel
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

object PaymentInfoRepository {

    private var progressDialog: ProgressDialog? = null

    var msg: String = ""
    val paymentInfoSetterGetter = MutableLiveData<PaymentInfoModel>()
    val TAG: String = "PaymentInfoRepository"

    fun getServicesApiCall(context: Context,ID_TransactionType:String,ID_Project:String,ID_Stage:String,ID_Employee:String,
                           ID_BillType:String,ID_PaymentMethod:String,ID_PettyCashier:String,asOnDate:String,ReqMode:String): MutableLiveData<PaymentInfoModel> {
        getPayInfo(context,ID_TransactionType,ID_Project,ID_Stage,ID_Employee,ID_BillType,ID_PaymentMethod,ID_PettyCashier,asOnDate,ReqMode)
        return paymentInfoSetterGetter
    }

    private fun getPayInfo(context: Context,ID_TransactionType:String,ID_Project:String,ID_Stage:String,ID_Employee:String,ID_BillType:String,
                           ID_PaymentMethod:String,ID_PettyCashier:String,asOnDate:String,ReqMode:String) {

        Log.e(TAG,"dsadsdsdad  ")

        try {
            paymentInfoSetterGetter.value = PaymentInfoModel("")
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
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_DepartmentSP = context.getSharedPreferences(Config.SHARED_PREF55, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_UserRoleSP = context.getSharedPreferences(Config.SHARED_PREF41, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

//                {"ReqMode":"128","FK_Project":41,"BankKey":"","FK_Company":"1","FK_Stages":"0","AsOnDate":"2024-01-03","FK_Employee":"1",
//                "FK_TransactionType"::2,"FK_PetyCashier":"5"}


                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                requestObject1.put("FK_Project", ProdsuitApplication.encryptStart(ID_Project))
                requestObject1.put("FK_Stages", ProdsuitApplication.encryptStart(ID_Stage))
                requestObject1.put("AsOnDate", ProdsuitApplication.encryptStart(Config.convertDate(asOnDate)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("FK_TransactionType", ProdsuitApplication.encryptStart(ID_TransactionType))
                requestObject1.put("FK_PetyCashier", ProdsuitApplication.encryptStart(ID_PettyCashier))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                Log.e(TAG,"9777 getPaymentInformation  "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getPaymentInformation(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<PaymentInfoModel>()
                        leads.add(PaymentInfoModel(response.body()))
                        val msg = leads[0].message
                        paymentInfoSetterGetter.value = PaymentInfoModel(msg)
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
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }



    }
}