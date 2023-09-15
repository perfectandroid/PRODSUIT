package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.CreateWalkingCustomerModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object CreateWalkingCustomerRepository {

    val createWalkingCustomerSetterGetter = MutableLiveData<CreateWalkingCustomerModel>()
    val TAG: String = "CreateWalkingCustomerRepository"
    private var progressDialog: ProgressDialog? = null

    fun getServicesApiCall(context: Context,strCustomer : String,strPhone : String,ID_AssignedTo : String,strAssignedDate : String,strDescription : String,leadByMobileNo : String): MutableLiveData<CreateWalkingCustomerModel> {
        CreateWalkingCustomer(context, strCustomer,strPhone,ID_AssignedTo,strAssignedDate,strDescription,leadByMobileNo)
        return createWalkingCustomerSetterGetter
    }

    private fun CreateWalkingCustomer(context: Context,strCustomer : String,strPhone : String,ID_AssignedTo : String,strAssignedDate : String,strDescription : String,leadByMobileNo : String) {
        try {
            createWalkingCustomerSetterGetter.value =  CreateWalkingCustomerModel("")
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
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)

//                {"BankKey":"-500","Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","FK_Company":"1","UserAction":"1","TransMode":"",
//                    "ID_CustomerAssignment":"0","CusName":"TestData","CusMobile":"0000000000","CaAssignedDate":"02/06/2023","FK_Employee":"2",
//                    "CaDescription":"Collect information","FK_BranchCodeUser":"2","EntrBy":"pomjnhftb"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(""))
                requestObject1.put("ID_CustomerAssignment", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("CusName", ProdsuitApplication.encryptStart(strCustomer))
                requestObject1.put("CusMobile", ProdsuitApplication.encryptStart(strPhone))
                requestObject1.put("CaAssignedDate", ProdsuitApplication.encryptStart(strAssignedDate))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_AssignedTo))
                requestObject1.put("CaDescription", ProdsuitApplication.encryptStart(strDescription))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("leadByMobileNo", ProdsuitApplication.encryptStart(leadByMobileNo))

                Log.e(TAG,"requestObject1   671   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()

            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.UpdateWalkingCustomerData(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<CreateWalkingCustomerModel>()
                        leads.add(CreateWalkingCustomerModel(response.body()))
                        val msg = leads[0].message
                        createWalkingCustomerSetterGetter.value = CreateWalkingCustomerModel(msg)

                    } catch (e: Exception) {

                        // Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    // Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
//            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }
    }
}