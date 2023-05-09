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
import com.perfect.prodsuit.Model.SaveServiceFollowUpModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

object SaveServiceFollowUpRepository {

    var TAG = "SaveServiceFollowUpRepository"
    val saveServiceFollowUpSetterGetter = MutableLiveData<SaveServiceFollowUpModel>()
    private var progressDialog: ProgressDialog? = null
    fun getServicesApiCall(context: Context, customer_service_register : String, strCustomerNote : String, strEmployeeNote : String, strVisitedDate : String
                           , strTotalAmount : String, strReplacementAmount : String, ID_Action : String, strFollowUpDate : String, ID_AssignedTo : String, ID_Billtype : String,
                           saveServiceAttendedArray : JSONArray, saveReplacedeProductArray : JSONArray, saveAttendedEmployeeArray : JSONArray,
                           savePaymentDetailArray : JSONArray): MutableLiveData<SaveServiceFollowUpModel> {
        saveServiceFollowUpData(context,customer_service_register,strCustomerNote,strEmployeeNote,strVisitedDate,
            strTotalAmount,strReplacementAmount,ID_Action,strFollowUpDate,ID_AssignedTo,ID_Billtype,saveServiceAttendedArray,saveReplacedeProductArray,
            saveAttendedEmployeeArray,savePaymentDetailArray)
        return saveServiceFollowUpSetterGetter
    }

    private fun saveServiceFollowUpData(context: Context, customer_service_register : String, strCustomerNote : String, strEmployeeNote : String, strVisitedDate : String
                                        , strTotalAmount : String, strReplacementAmount : String, ID_Action : String, strFollowUpDate : String, ID_AssignedTo : String, ID_Billtype : String,
                                        saveServiceAttendedArray : JSONArray, saveReplacedeProductArray : JSONArray, saveAttendedEmployeeArray : JSONArray,
                                        savePaymentDetailArray : JSONArray) {


        Log.e(TAG,"3201  "
                +"\n customer_service_register  :  "+customer_service_register
                +"\n CustomerNotes  :  "+strCustomerNote
                +"\n EmployeeNote  :  "+strEmployeeNote
                +"\n StartingDate  :  "+strVisitedDate
                +"\n TotalAmount  :  "+strTotalAmount
                +"\n ReplaceAmount  :  "+strReplacementAmount
                +"\n FK_NextAction  :  "+ID_Action
                +"\n FK_NextActionLead  :  "+strFollowUpDate
                +"\n FK_NextActionLead  :  "+ID_AssignedTo
                +"\n FK_BillType  :  "+ID_Billtype)

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val newDate1: Date = sdf.parse(strVisitedDate)
        val newDate2: Date = sdf.parse(strFollowUpDate)
        val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")

        var strVisitedDateNew = sdfDate1.format(newDate1)
        var strFollowUpDateNew = sdfDate1.format(newDate2)
        Log.e(TAG,"strVisitedDate    536   :  "+strVisitedDateNew)
        Log.e(TAG,"strFollowUpDate   536   :  "+strFollowUpDateNew)



        Log.e(TAG,"saveServiceAttendedArray    3202   :  "+saveServiceAttendedArray)
        Log.e(TAG,"saveReplacedeProductArray   3203   :  "+saveReplacedeProductArray)
        Log.e(TAG,"saveAttendedEmployeeArray   3204   :  "+saveAttendedEmployeeArray)
        Log.e(TAG,"savePaymentDetailArray      3205   :  "+savePaymentDetailArray)


        try {
            saveServiceFollowUpSetterGetter.value = SaveServiceFollowUpModel("")
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
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Customerserviceregister", ProdsuitApplication.encryptStart(customer_service_register))
                requestObject1.put("CustomerNotes", ProdsuitApplication.encryptStart(strCustomerNote))
                requestObject1.put("EmployeeNote", ProdsuitApplication.encryptStart(strEmployeeNote))
                requestObject1.put("StartingDate", ProdsuitApplication.encryptStart(strVisitedDateNew))
                requestObject1.put("ServiceAmount", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("ProductAmount", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("NetAmount", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("TotalAmount", ProdsuitApplication.encryptStart(strTotalAmount))
                requestObject1.put("ReplaceAmount", ProdsuitApplication.encryptStart(strReplacementAmount))
                requestObject1.put("DiscountAmount", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserNameSP.getString("UserCode", null)))
                requestObject1.put("FK_NextAction", ProdsuitApplication.encryptStart(ID_Action))
                requestObject1.put("FK_NextActionLead", ProdsuitApplication.encryptStart(strFollowUpDateNew))
                requestObject1.put("FK_BillType", ProdsuitApplication.encryptStart(ID_Billtype))
                requestObject1.put("FK_EmployeeLead", ProdsuitApplication.encryptStart(ID_AssignedTo))

                if (saveServiceAttendedArray.length() == 0){
                    requestObject1.put("ServiceDetails",saveServiceAttendedArray)
                }else{
                    requestObject1.put("ServiceDetails","")
                }

                if (saveReplacedeProductArray.length() == 0){
                    requestObject1.put("PrdctDetails", saveReplacedeProductArray)
                }else{
                    requestObject1.put("PrdctDetails", "")
                }

                if (saveAttendedEmployeeArray.length() == 0){
                    requestObject1.put("AttendedEmployeeDetails",saveAttendedEmployeeArray)
                }else{
                    requestObject1.put("AttendedEmployeeDetails","")
                }
                if (savePaymentDetailArray.length() == 0){
                    requestObject1.put("PaymentDetail",savePaymentDetailArray)
                }else{
                    requestObject1.put("PaymentDetail","")
                }

                Log.e(TAG,"78  SAVE   "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.UpdateServiceFollowUp(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("save FollowUp  ",response.body())
                        val leads = ArrayList<SaveServiceFollowUpModel>()
                        leads.add(SaveServiceFollowUpModel(response.body()))
                        val msg = leads[0].message
                        saveServiceFollowUpSetterGetter.value = SaveServiceFollowUpModel(msg)
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