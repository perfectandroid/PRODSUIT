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
import com.perfect.prodsuit.Model.UpadateSiteVisitModel
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

object UpadateSiteVisitRepository {

    private var progressDialog: ProgressDialog? = null
    val upadateSiteVisitSetterGetter = MutableLiveData<UpadateSiteVisitModel>()
    val TAG: String = "UpadateSiteVisitRepository"

    fun getServicesApiCall(context: Context, UserAction : String, strLeadno : String,ID_SiteVisitAssignment: String , strVisitdate : String, visitTime : String, strInspectionNote1 : String,
                           strInspectionNote2 : String, strCustomerNotes : String, strExpenseAmount : String, strCommonRemark : String, strInspectionCharge : String,
                           saveEmployeeDetails : JSONArray, saveMeasurementDetails : JSONArray, saveCheckedDetails : JSONArray, pssOtherCharge : JSONArray,
                           pssOtherChargeTax : JSONArray
    ): MutableLiveData<UpadateSiteVisitModel> {
        UpadateSiteVisitRep(context,UserAction,strLeadno,ID_SiteVisitAssignment,strVisitdate,visitTime,strInspectionNote1,strInspectionNote2,
            strCustomerNotes,strExpenseAmount,strCommonRemark,strInspectionCharge,saveEmployeeDetails,saveMeasurementDetails,saveCheckedDetails,
            pssOtherCharge,pssOtherChargeTax)
        return upadateSiteVisitSetterGetter
    }

    private fun UpadateSiteVisitRep(context: Context, UserAction : String, strLeadno : String,ID_SiteVisitAssignment: String , strVisitdate : String, visitTime : String, strInspectionNote1 : String,
                                    strInspectionNote2 : String, strCustomerNotes : String, strExpenseAmount : String, strCommonRemark : String, strInspectionCharge : String,
                                    saveEmployeeDetails : JSONArray, saveMeasurementDetails : JSONArray, saveCheckedDetails : JSONArray, pssOtherCharge : JSONArray,
                                    pssOtherChargeTax : JSONArray) {

        try {
            upadateSiteVisitSetterGetter.value = UpadateSiteVisitModel("")
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
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36,0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(UserAction))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("FK_LeadGeneration", ProdsuitApplication.encryptStart(strLeadno))
                requestObject1.put("VisitDate", ProdsuitApplication.encryptStart(Config.convertDate(strVisitdate)))
                requestObject1.put("VisitTime", ProdsuitApplication.encryptStart(Config.convert12HourTo24Hour(visitTime)))
                requestObject1.put("Note1", ProdsuitApplication.encryptStart(strInspectionNote1))
                requestObject1.put("Note2", ProdsuitApplication.encryptStart(strInspectionNote2))
                requestObject1.put("CusNote", ProdsuitApplication.encryptStart(strCustomerNotes))
                requestObject1.put("ExpenseAmount", ProdsuitApplication.encryptStart(strExpenseAmount))
                requestObject1.put("ExpenseAmount", ProdsuitApplication.encryptStart(strExpenseAmount))
                requestObject1.put("Inspectioncharge", ProdsuitApplication.encryptStart(strInspectionCharge))
                requestObject1.put("Remarks", ProdsuitApplication.encryptStart(strCommonRemark))
                requestObject1.put("FK_SiteVisitAssignment", ProdsuitApplication.encryptStart(ID_SiteVisitAssignment))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                requestObject1.put("EmployeeDetails", saveEmployeeDetails)
                requestObject1.put("MeasurementDetails", saveMeasurementDetails)
                requestObject1.put("pssOtherCharge", pssOtherCharge)
                requestObject1.put("pssOtherChargeTax",pssOtherChargeTax)
                requestObject1.put("CheckListSub", saveCheckedDetails)
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"  UpadateSiteVisit 10333     "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.saveUpadateSiteVisit(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<UpadateSiteVisitModel>()
                        leads.add(UpadateSiteVisitModel(response.body()))
                        val msg = leads[0].message
                        upadateSiteVisitSetterGetter.value = UpadateSiteVisitModel(msg)
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