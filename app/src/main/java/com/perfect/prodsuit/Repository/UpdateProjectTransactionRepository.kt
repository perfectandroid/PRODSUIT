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
import com.perfect.prodsuit.Model.UpdateProjectTransactionModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object UpdateProjectTransactionRepository {

    private var progressDialog: ProgressDialog? = null
    val updateProjectTransactionSetterGetter = MutableLiveData<UpdateProjectTransactionModel>()
    val TAG: String = "UpdateProjectTransactionRepository"

    fun getServicesApiCall(context: Context, UserAction : String, Date : String, FK_Project : String, FK_Stage : String,
                           NetAmount : String, OtherCharge : String, Remark : String,
                           pssOtherCharge : JSONArray, pssOtherChargeTax : JSONArray, PaymentDetails : JSONArray,
                           ID_TransactionType : String,ID_Employee : String,strRoundOff : String,ID_BillType : String,
                           ID_PettyCashier : String): MutableLiveData<UpdateProjectTransactionModel> {
        UpdateProjectTransactionRep(context,UserAction,Date,FK_Project,FK_Stage,NetAmount,OtherCharge,
            Remark,pssOtherCharge,pssOtherChargeTax,PaymentDetails,ID_TransactionType,ID_Employee,strRoundOff,ID_BillType,ID_PettyCashier)
        return updateProjectTransactionSetterGetter
    }

    private fun UpdateProjectTransactionRep(context: Context, UserAction : String, Date : String, FK_Project : String, FK_Stage : String,
                                            NetAmount : String, OtherCharge : String, Remark : String,
                                            pssOtherCharge : JSONArray, pssOtherChargeTax : JSONArray, PaymentDetails : JSONArray,
                                            ID_TransactionType : String,ID_Employee : String,strRoundOff : String,ID_BillType : String,ID_PettyCashier : String) {

        try {
            updateProjectTransactionSetterGetter.value = UpdateProjectTransactionModel("")
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
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36,0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)



                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(UserAction))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))

                requestObject1.put("FK_TransactionType", ProdsuitApplication.encryptStart(ID_TransactionType))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))

                requestObject1.put("Date", ProdsuitApplication.encryptStart(Config.convertDate(Date)))
                requestObject1.put("FK_Project", ProdsuitApplication.encryptStart(FK_Project))
                requestObject1.put("FK_Stage", ProdsuitApplication.encryptStart(FK_Stage))
                requestObject1.put("NetAmount", ProdsuitApplication.encryptStart(NetAmount))
                requestObject1.put("OtherCharge", ProdsuitApplication.encryptStart(OtherCharge))

                requestObject1.put("RoundOff", ProdsuitApplication.encryptStart(strRoundOff))
                requestObject1.put("FK_BillType", ProdsuitApplication.encryptStart(ID_BillType))
                requestObject1.put("FK_PettyCashier", ProdsuitApplication.encryptStart(ID_PettyCashier))

                requestObject1.put("Remark", ProdsuitApplication.encryptStart(Remark))

                requestObject1.put("pssOtherCharge", pssOtherCharge)
                requestObject1.put("pssOtherChargeTax", pssOtherChargeTax)
                requestObject1.put("PaymentDetail", PaymentDetails)
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                Log.e(TAG,"requestObject1   82   "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )

            val call = apiService.saveUpdateProjectTransaction(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<UpdateProjectTransactionModel>()
                        leads.add(UpdateProjectTransactionModel(response.body()))
                        val msg = leads[0].message
                        updateProjectTransactionSetterGetter.value = UpdateProjectTransactionModel(msg)
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
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }

    }
}