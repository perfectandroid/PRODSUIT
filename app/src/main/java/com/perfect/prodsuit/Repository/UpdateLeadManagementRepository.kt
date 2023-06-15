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
import com.perfect.prodsuit.Model.CustomerSearchModel
import com.perfect.prodsuit.Model.UpdateLeadManagementModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object UpdateLeadManagementRepository {
    val TAG : String = "UpdateLeadManagementRepository"
    val updateLeadManagementSetterGetter = MutableLiveData<UpdateLeadManagementModel>()
    private var progressDialog: ProgressDialog? = null
    fun getServicesApiCall(context: Context,ID_LeadGenerateProduct :String,ID_LeadGenerate :String,ID_ActionType :String,ID_Employee :String,ID_Status :String,strFollowUpDate :String,strFollowUpTime : String,
                           strCustomerRemark :String,strEmployeeRemark :String,ID_NextAction :String,ID_NextActionType :String,strNextFollowUpDate :String,ID_Priority :String,
                           ID_Department :String,ID_NextEmployee :String, strCallStatus: String?,strCallDuration: String?,strLatitude: String?,strLongitude: String?,encode1: String?,encode2: String?): MutableLiveData<UpdateLeadManagementModel> {
        getUpdateLeadManagement(context,ID_LeadGenerateProduct,ID_LeadGenerate,ID_ActionType,ID_Employee,ID_Status,strFollowUpDate,strFollowUpTime,
            strCustomerRemark,strEmployeeRemark,ID_NextAction,ID_NextActionType,strNextFollowUpDate,ID_Priority,ID_Department,ID_NextEmployee,strCallStatus,strCallDuration,strLatitude,strLongitude,encode1,encode2)
        return updateLeadManagementSetterGetter
    }

    private fun getUpdateLeadManagement(context: Context,ID_LeadGenerateProduct :String,ID_LeadGenerate :String,ID_ActionType :String,ID_Employee :String,ID_Status :String,strFollowUpDate :String,strFollowUpTime : String,
                                        strCustomerRemark :String,strEmployeeRemark :String,ID_NextAction :String,ID_NextActionType :String,strNextFollowUpDate :String,ID_Priority :String,
                                        ID_Department :String,ID_NextEmployee :String,strCallStatus: String?,strCallDuration: String?,strLatitude: String?,strLongitude: String?,encode1: String?,encode2: String?) {

        Log.e(TAG,"FOLLOWUP  25981 "
                +"\n ID_LeadGenerateProduct :  "+ID_LeadGenerateProduct
                +"\n ID_LeadGenerate        :  "+ID_LeadGenerate
                +"\n ID_ActionType          :  "+ID_ActionType
                +"\n ID_Employee            :  "+ID_Employee
                +"\n ID_Status              :  "+ID_Status
                +"\n strFollowUpDate        :  "+strFollowUpDate
           //     +"\n strFollowUpTime        :  "+strFollowUpTime
                +"\n strCallStatus          :  "+strCallStatus
                +"\n strCallDuration        :  "+strCallDuration
                +"\n strCustomerRemark      :  "+strCustomerRemark
                +"\n strEmployeeRemark      :  "+strEmployeeRemark

                +"\n NEXT ACTION                :  "
                +"\n ID_NextAction              :  "+ID_NextAction
                +"\n ID_NextActionType          :  "+ID_NextActionType
                +"\n val strNextFollowUpDate    :  "+strNextFollowUpDate
                +"\n ID_Priority                :  "+ID_Priority
                +"\n ID_Department              :  "+ID_Department
                +"\n ID_NextEmployee            :  "+ID_NextEmployee
                +"\n ID_LeadGenerate            :  "+ID_LeadGenerate
                +"\n ID_LeadGenerateProduct     :  "+ID_LeadGenerateProduct)



        try {
            updateLeadManagementSetterGetter.value = UpdateLeadManagementModel("")
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


//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "UserAction:"1"
//                "FK_LeadGenerate":1,
//                FK_LeadGenerateProduct=10205,
//                "LgActMode:"1"  -Followup details action type field save
//                "ID_FollowUpBy":"1"
//                ActStatus =1,
//                "TrnsDate":"2022-04-17",
//                "CustomerNote":"test",
//                "EmployeeNote":tes",
//
//
//                "FK_Action":1,
//                "FK_ActionType":"1",
//                "NextActionDate:"2022-04-17",
//                "FK_Priority:1,
//                "FK_Departement":1,
//                "FK_ToEmployee:1


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart("1"))  // Save 1 Update 2
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart("ERP"))  // Default

                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))  // Login
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))  // Login
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))  // Login

                requestObject1.put("ID_LeadGenerate", ProdsuitApplication.encryptStart(ID_LeadGenerate))
                requestObject1.put("ID_LeadGenerateProduct", ProdsuitApplication.encryptStart(ID_LeadGenerateProduct))

                requestObject1.put("LgActMode", ProdsuitApplication.encryptStart(ID_ActionType))
                requestObject1.put("ID_FollowUpBy", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("ActStatus", ProdsuitApplication.encryptStart(ID_Status))
                requestObject1.put("TrnsDate", ProdsuitApplication.encryptStart(strFollowUpDate))
                requestObject1.put("CustomerNote", ProdsuitApplication.encryptStart(strCustomerRemark))
                requestObject1.put("EmployeeNote", ProdsuitApplication.encryptStart(strEmployeeRemark))

                requestObject1.put("FK_Action", ProdsuitApplication.encryptStart(ID_NextAction))
                requestObject1.put("FK_ActionType", ProdsuitApplication.encryptStart(ID_NextActionType))
                requestObject1.put("NextActionDate", ProdsuitApplication.encryptStart(strNextFollowUpDate))
                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
                requestObject1.put("FK_Departement", ProdsuitApplication.encryptStart(ID_Department))
                requestObject1.put("FK_ToEmployee", ProdsuitApplication.encryptStart(ID_NextEmployee))

                requestObject1.put("LgFollowUpTime", ProdsuitApplication.encryptStart(strFollowUpTime))
                requestObject1.put("LgFollowUpStatus", ProdsuitApplication.encryptStart(strCallStatus))
                requestObject1.put("LgFollowupDuration", ProdsuitApplication.encryptStart(strCallDuration))
                requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(strLatitude))
                requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(strLongitude))
//                requestObject1.put("LocationLandMark1", ProdsuitApplication.encryptStart(encode1))
//                requestObject1.put("LocationLandMark2", ProdsuitApplication.encryptStart(encode2))
                requestObject1.put("LocationLandMark1", encode1)
                requestObject1.put("LocationLandMark2", encode2)


                Log.v(TAG,"requestObject1   1581   "+requestObject1)
                Log.v("sfgsdfdsfdsfdsdd",""+requestObject1)
                Log.e(TAG,"requestObject1   1581   "+requestObject1)
                Log.e(TAG,"UserCode   1582   "+UserCodeSP.getString("UserCode", null))

                Log.e(TAG,"encode1   11111   "+encode1)
                Log.e(TAG,"encode2   0000   "+encode2)
                Log.v("sfgsdfdsfdsfdsdd","1===="+encode1)
                Log.v("sfgsdfdsfdsfdsdd","2===="+encode2)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getUpdateLeadManagement(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.e(TAG,"response  1126     "+response.body())
                        val customer = ArrayList<UpdateLeadManagementModel>()
                        customer.add(UpdateLeadManagementModel(response.body()))
                        val msg = customer[0].message
                        updateLeadManagementSetterGetter.value = UpdateLeadManagementModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
        }

    }


}