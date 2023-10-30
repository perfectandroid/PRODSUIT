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
import com.perfect.prodsuit.Model.ServiceFollowUpSaveModel
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

object ServiceFollowUpSaveRepository {

    private var progressDialog: ProgressDialog? = null
    val leadGenSaveSetterGetter = MutableLiveData<ServiceFollowUpSaveModel>()
    val TAG: String = "ServiceFollowUpSaveRepository"

    fun getServicesApiCall(
        context: Context,
        UserAction: String,
        FK_Customerserviceregister: String,
        ID_CustomerServiceRegisterProductDetails: String,
        StartingDate: String,
        ComponentCharge: String,
        ServiceCharge: String,
        OtherCharge: String,
        TotalSecurityAmount: String,
        TotalAmount: String,
        DiscountAmount: String,
        FK_Company: String,
        FK_BranchCodeUser: String,
        EntrBy: String,
        FK_BillType: String,
        FK_Machine: String,
        TransMode: String,
        ServiceDetails: JSONArray,
        ProductDetails: JSONArray,
        Actionproductdetails: JSONArray,
        AttendedEmployeeDetails: JSONArray,
        ServiceIncentive: JSONArray,
        OtherCharges: JSONArray,
        PaymentDetail: JSONArray
    ): MutableLiveData<ServiceFollowUpSaveModel> {
        saveLeadGenerate(
            context, UserAction,
            FK_Customerserviceregister,
            ID_CustomerServiceRegisterProductDetails,
            StartingDate,
            ComponentCharge,
            ServiceCharge,
            OtherCharge,
            TotalSecurityAmount,
            TotalAmount,
            DiscountAmount,
            FK_Company,
            FK_BranchCodeUser,
            EntrBy,
            FK_BillType,
            FK_Machine,
            TransMode,
            ServiceDetails,
            ProductDetails,
            Actionproductdetails,
            AttendedEmployeeDetails, ServiceIncentive, OtherCharges, PaymentDetail
        )
        Log.e("LeadGenerateSaveRepository", " 226666    ")
        return leadGenSaveSetterGetter
    }

    private fun saveLeadGenerate(
        context: Context,
        UserAction: String,
        FK_Customerserviceregister: String,
        ID_CustomerServiceRegisterProductDetails: String,
        StartingDate: String,
        ComponentCharge: String,
        ServiceCharge: String,
        OtherCharge: String,
        TotalSecurityAmount: String,
        TotalAmount: String,
        DiscountAmount: String,
        FK_Company: String,
        FK_BranchCodeUser: String,
        EntrBy: String,
        FK_BillType: String,
        FK_Machine: String,
        TransMode: String,
        ServiceDetails: JSONArray,
        ProductDetails: JSONArray,
        Actionproductdetails: JSONArray,
        AttendedEmployeeDetails : JSONArray,ServiceIncentive : JSONArray,OtherCharges : JSONArray,PaymentDetail : JSONArray
    ) {


        try {
            leadGenSaveSetterGetter.value = ServiceFollowUpSaveModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(
                context.resources.getDrawable(
                    R.drawable.progress
                )
            )
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

//

//                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
//                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
//                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
//                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
//                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
//                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)

//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("UserName", ProdsuitApplication.encryptStart(UserNameSP.getString("UserName", null))) //New
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(saveUpdateMode))//




                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(UserAction))
                requestObject1.put("FK_Customerserviceregister", ProdsuitApplication.encryptStart(FK_Customerserviceregister))
                requestObject1.put("ID_CustomerServiceRegisterProductDetails", ProdsuitApplication.encryptStart(ID_CustomerServiceRegisterProductDetails))
                requestObject1.put("StartingDate", ProdsuitApplication.encryptStart(StartingDate))
                requestObject1.put("ComponentCharge", ProdsuitApplication.encryptStart(ComponentCharge))
                requestObject1.put("ServiceCharge", ProdsuitApplication.encryptStart(ServiceCharge))
                requestObject1.put("OtherCharge", ProdsuitApplication.encryptStart(OtherCharge))
                requestObject1.put("TotalSecurityAmount", ProdsuitApplication.encryptStart(TotalSecurityAmount))
                requestObject1.put("TotalAmount", ProdsuitApplication.encryptStart(TotalAmount))
                requestObject1.put("DiscountAmount", ProdsuitApplication.encryptStart(DiscountAmount))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_Company))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUser))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBy))
                requestObject1.put("FK_BillType", ProdsuitApplication.encryptStart(FK_BillType))
                requestObject1.put("FK_Machine", ProdsuitApplication.encryptStart(FK_Machine))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart(TransMode))
                requestObject1.put("ServiceDetails", (ServiceDetails))
                requestObject1.put("ProductDetails", (ProductDetails))
                requestObject1.put("Actionproductdetails", (Actionproductdetails))
                requestObject1.put("AttendedEmployeeDetails", (AttendedEmployeeDetails))
                requestObject1.put("ServiceIncentive", (ServiceIncentive))
                requestObject1.put("OtherCharges", (OtherCharges))
                requestObject1.put("PaymentDetail", (PaymentDetail))

                Log.e(TAG, "requestObject1   1363   " + requestObject1)


            } catch (e: Exception) {
                Log.v("hjhvbhk", "gghg" + e)
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.updateServiceFollowUp(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG, "response  95   " + response.body())
                        val jObject = JSONObject(response.body())
                        val leadSave = ArrayList<ServiceFollowUpSaveModel>()
                        Log.e(TAG, "pincode  95   " + leadSave)
                        leadSave.add(ServiceFollowUpSaveModel(response.body()))
                        val msg = leadSave[0].message
                        leadGenSaveSetterGetter.value = ServiceFollowUpSaveModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "" + e.toString(), Toast.LENGTH_SHORT).show()
                        progressDialog!!.dismiss()
                    }
                }

                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, "" + Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context, "" + e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

}