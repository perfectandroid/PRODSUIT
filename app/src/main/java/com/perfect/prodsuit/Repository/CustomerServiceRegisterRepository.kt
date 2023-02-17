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
import com.perfect.prodsuit.Model.CustomerServiceRegisterModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object CustomerServiceRegisterRepository {

    private var progressDialog: ProgressDialog? = null
    val cusServRegisterSetterGetter = MutableLiveData<CustomerServiceRegisterModel>()
    val TAG: String = "CustomerServiceRegisterRepository"

    fun getServicesApiCall(context: Context,strUserAction : String,Customer_Type : String,ID_Customer : String,ID_Channel : String,ID_Priority : String,
                           ID_Category : String, ID_Company : String,ID_ComplaintList : String,ID_Services : String,ID_EmpMedia : String,ID_Status : String,
                           ID_AttendedBy : String, strCustomerName : String,strMobileNo : String,strAddress : String,strContactNo : String, strLandMark : String,
                           strFromDate : String, strToDate : String,strFromTime : String,strToTime : String,ID_Product : String,strDescription : String,
                           strDate : String,strTime : String): MutableLiveData<CustomerServiceRegisterModel> {
        getCustomerServiceRegister(context,strUserAction,Customer_Type,ID_Customer,ID_Channel,ID_Priority,ID_Category,
            ID_Company,ID_ComplaintList,ID_Services,ID_EmpMedia,ID_Status,ID_AttendedBy,strCustomerName,strMobileNo,strAddress,strContactNo,
            strLandMark,strFromDate,strToDate,strFromTime,strToTime,ID_Product,strDescription,strDate,strTime)
        return cusServRegisterSetterGetter
    }

    private fun getCustomerServiceRegister(context: Context,strUserAction : String,Customer_Type : String,ID_Customer : String,ID_Channel : String,ID_Priority : String,
                                           ID_Category : String, ID_Company : String,ID_ComplaintList : String,ID_Services : String,ID_EmpMedia : String,ID_Status : String,
                                           ID_AttendedBy : String, strCustomerName : String,strMobileNo : String,strAddress : String,strContactNo : String, strLandMark : String,
                                           strFromDate : String, strToDate : String,strFromTime : String,strToTime : String,ID_Product : String,strDescription : String,
                                           strDate : String,strTime : String) {

//        Log.e(TAG,"Validation   93731"
//                +"\n"+"Customer Type        :  "+Customer_Type
//                +"\n"+"FK_Customer          :  "+ID_Customer
//                +"\n"+"strCustomerName      :  "+strCustomerName
//                +"\n"+"CSRChannelID         :  "+ID_Channel
//                +"\n"+"CSRPriority          :  "+ID_Priority
//                +"\n"+"CSRPCategory         :  "+ID_Category
//                +"\n"+"FK_OtherCompany      :  "+ID_Company
//                +"\n"+"FK_ComplaintList     :  "+ID_ComplaintList
//                +"\n"+"FK_ServiceList       :  "+ID_Services
//                +"\n"+"CSRChannelSubID      :  "+ID_EmpMedia
//                +"\n"+"ID_ComplaintList     :  "+ID_ComplaintList
//
//                +"\n"+"Status               :  "+ID_Status
//                +"\n"+"AttendedBy           :  "+ID_AttendedBy
//                +"\n"+"CusName              :  "+strCustomerName
//                +"\n"+"CusMobile            :  "+strMobileNo
//                +"\n"+"CusAddress           :  "+strAddress
//                +"\n"+"CSRContactNo         :  "+strContactNo
//                +"\n"+"CSRLandmark          :  "+strLandMark
//
//                +"\n"+"CSRServiceFromDate   :  "+strFromDate
//                +"\n"+"CSRServiceToDate     :  "+strToDate
//                +"\n"+"CSRServicefromtime   :  "+strFromTime
//                +"\n"+"CSRServicetotime     :  "+strToTime
//
//                +"\n"+"FK_Product           :  "+ID_Product
//                +"\n"+"CSRODescription      :  "+strDescription
//                +"\n"+"TicketDate           :  "+strDate)

        try {
            cusServRegisterSetterGetter.value = CustomerServiceRegisterModel("")
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

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))

                requestObject1.put("ID_CustomerServiceRegister", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("CSRChannelID", ProdsuitApplication.encryptStart(ID_Channel))
                requestObject1.put("CSRPriority", ProdsuitApplication.encryptStart(ID_Priority))
                requestObject1.put("CSRCurrentStatus", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart( FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("CSRPCategory", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("FK_OtherCompany", ProdsuitApplication.encryptStart(ID_Company))
                requestObject1.put("FK_ComplaintList", ProdsuitApplication.encryptStart(ID_ComplaintList))
                requestObject1.put("FK_ServiceList", ProdsuitApplication.encryptStart(ID_Services))
                requestObject1.put("CSRChannelSubID", ProdsuitApplication.encryptStart(ID_EmpMedia))
                requestObject1.put("Status", ProdsuitApplication.encryptStart(ID_Status))
                requestObject1.put("AttendedBy", ProdsuitApplication.encryptStart(ID_AttendedBy))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(strUserAction))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart("CUSV"))
                requestObject1.put("CSRTickno", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("CusName", ProdsuitApplication.encryptStart(strCustomerName))
                requestObject1.put("CusMobile", ProdsuitApplication.encryptStart(strMobileNo))
                requestObject1.put("CusAddress", ProdsuitApplication.encryptStart(strAddress))
                requestObject1.put("CSRContactNo", ProdsuitApplication.encryptStart(strContactNo))
                requestObject1.put("CSRLandmark", ProdsuitApplication.encryptStart(strLandMark))
                requestObject1.put("CSRServiceFromDate", ProdsuitApplication.encryptStart(strFromDate))
                requestObject1.put("CSRServiceToDate", ProdsuitApplication.encryptStart(strToDate))
                requestObject1.put("CSRServicefromtime", ProdsuitApplication.encryptStart(strFromTime))
                requestObject1.put("CSRServicetotime", ProdsuitApplication.encryptStart(strToTime))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("CSRODescription", ProdsuitApplication.encryptStart(strDescription))
                requestObject1.put("TicketDate", ProdsuitApplication.encryptStart(strDate))
                requestObject1.put("TicketTime", ProdsuitApplication.encryptStart(strTime))

                if (Customer_Type.equals("0")){
                    Log.e(TAG,"642121   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(ID_Customer))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart("0"))
                }else if (Customer_Type.equals("1")){
                    Log.e(TAG,"642122   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart(ID_Customer))
                }else{
                    Log.e(TAG,"642123   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart("0"))
                }


                Log.e(TAG,"requestObject1   1601   "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.UpdateCustomerServiceRegister(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("Save Service 1602  ",response.body())
                        val leads = ArrayList<CustomerServiceRegisterModel>()
                        leads.add(CustomerServiceRegisterModel(response.body()))
                        val msg = leads[0].message
                        cusServRegisterSetterGetter.value = CustomerServiceRegisterModel(msg)
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