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
import com.perfect.prodsuit.Model.ServiceAssignModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object ServiceAssignRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceAssignSetterGetter = MutableLiveData<ServiceAssignModel>()
    val TAG: String = "ServiceAssignRepository"

    fun getServicesApiCall(
        context: Context,
        ReqMode: String,
        ID_CustomerServiceRegister: String,
        strAssignees: JSONArray,
        strVisitDate: String,
        strVisitTime: String,
        ID_Priority: String,
        strRemark: String,
        FK_CustomerserviceregisterProductDetails: String?
    ): MutableLiveData<ServiceAssignModel> {
        getServiceAssign(context,ReqMode,ID_CustomerServiceRegister,strAssignees,strVisitDate,strVisitTime,ID_Priority,strRemark,FK_CustomerserviceregisterProductDetails!!)
        return serviceAssignSetterGetter
    }

    private fun getServiceAssign(context: Context,ReqMode : String,ID_CustomerServiceRegister : String,strAssignees : JSONArray,strVisitDate : String,strVisitTime : String,ID_Priority : String,strRemark : String,FK_CustomerserviceregisterProductDetails:String) {
//
//        val msg ="{\"ServiceAssignDetails\": {\"ServiceAssignDetailsList\": [{\"TicketNo\":\"TKT101\",\"TicketDate\": \"06/06/2022\",\"Branch\": \"Head Office Chalappuram\",\"Customer\": \"Yousaf\",\"Mobile\": \"9876543210\",\"Area\": \"Chalappuram\",\"Priority\": \"LOW\",\"Status\": \"Pending\",\"TimeDue\": \"1 Week Ago\"},{\"TicketNo\":\"TKT101\",\"TicketDate\": \"07/06/2022\",\"Branch\": \"Head Office Chalappuram\",\"Customer\": \"Yousaf\",\"Mobile\": \"9876543211\",\"Area\": \"Chalappuram\",\"Priority\": \"HIGH\",\"Status\": \"Completed\",\"TimeDue\": \"10 Days Ago\"},{\"TicketNo\":\"TKT101\",\"TicketDate\": \"08/06/2022\",\"Branch\": \"Head Office Chalappuram\",\"Customer\": \"Yousaf\",\"Mobile\": \"9876543212\",\"Area\": \"Chalappuram\",\"Priority\": \"MEDIUM\",\"Status\": \"Pending\",\"TimeDue\": \"1 Month Ago\"},{\"TicketNo\":\"TKT101\",\"TicketDate\": \"09/06/2022\",\"Branch\": \"Head Office Chalappuram\",\"Customer\": \"Yousaf\",\"Mobile\": \"9876543213\",\"Area\": \"Chalappuram\",\"Priority\": \"LOW\",\"Status\": \"Completed\",\"TimeDue\": \"5 Days Ago\"}],\"ResponseCode\": \"0\",\"ResponseMessage\": \"Transaction Verified\"},\"StatusCode\": 0,\"EXMessage\": \"Transaction Verified\"}"
//        serviceAssignSetterGetter.value = ServiceAssignModel(msg)
        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        val dateFrom = inputFormat.parse(strVisitDate)
        val strFromDate = outputFormat.format(dateFrom)


        Log.e(TAG,"strAssignees  4000    "+strVisitDate+"\n"+strFromDate)
        try {
            serviceAssignSetterGetter.value = ServiceAssignModel("")
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
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)


//                {"BankKey":"\/mXqmq3ZMvs=\n","Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","FK_Customerserviceregister":"1",
//                    "FK_Employee":"1","Visitdate":"2023-02-01","Visittime":"02:05","FK_Priority":"2","Remark":"Test","FK_Company":"1","FK_BranchCodeUser":"3",
//                    "EntrBy":"APP","FK_Branch":"3",Assignees{["FK_Employee":"1","EmployeeType":"3"]}




                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Customerserviceregister", ProdsuitApplication.encryptStart(ID_CustomerServiceRegister))
                requestObject1.put("FK_CustomerserviceregisterProductDetails", ProdsuitApplication.encryptStart(FK_CustomerserviceregisterProductDetails))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("Visitdate", ProdsuitApplication.encryptStart(strFromDate))
                requestObject1.put("Visittime", ProdsuitApplication.encryptStart(strVisitTime))
                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
                requestObject1.put("Remark", ProdsuitApplication.encryptStart(strRemark))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart( FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("Assignees", strAssignees)
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"requestObject1   7812   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getCustomerserviceassignUpdate(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"response  111     "+response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ServiceAssignModel>()
                        leads.add(ServiceAssignModel(response.body()))
                        val msg = leads[0].message
                        serviceAssignSetterGetter.value = ServiceAssignModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }

    }

}