package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.LeadGenerateSaveModel
import com.perfect.prodsuit.Model.PincodeSearchModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object LeadGenerateSaveRepository {

    private var progressDialog: ProgressDialog? = null
    val leadGenSaveSetterGetter = MutableLiveData<LeadGenerateSaveModel>()
    val TAG: String = "LeadGenerateSaveRepository"

    fun getServicesApiCall(context: Context, strDate :String, ID_LeadFrom :String, ID_LeadThrough :String, ID_CollectedBy :String,
                           ID_Customer :String, Customer_Name :String, Customer_Address :String, Customer_Mobile :String, Customer_Email :String,
                           CompanyNme :String,CusPhone :String, ID_MediaMaster :String, FK_Country :String, FK_States :String,
                           FK_District :String, FK_Post :String, ID_Category :String, ID_Product :String,strProdName :String, strQty :String, ID_Priority :String,
                           strFeedback :String, ID_Status :String, ID_NextAction :String, ID_ActionType :String, strFollowupdate :String, ID_Branch :String,
                           ID_BranchType :String, ID_Department :String, ID_Employee :String, strLatitude :String, strLongitue :String, locAddress :String,
                           encode1 :String, encode2 :String , saveUpdateMode : String): MutableLiveData<LeadGenerateSaveModel> {
        saveLeadGenerate(context, strDate, ID_LeadFrom!!,
            ID_LeadThrough!!, ID_CollectedBy!!, ID_Customer!!, Customer_Name!!, Customer_Address!!, Customer_Mobile!!, Customer_Email!!,CompanyNme,CusPhone,
            ID_MediaMaster!!, FK_Country, FK_States, FK_District, FK_Post, ID_Category!!, ID_Product!!, strProdName, strQty, ID_Priority!!,
            strFeedback, ID_Status!!, ID_NextAction, ID_ActionType, strFollowupdate, ID_Branch, ID_BranchType, ID_Department,
            ID_Employee, strLatitude!!,strLongitue!!, locAddress!!, encode1, encode2 , saveUpdateMode)
        Log.e("LeadGenerateSaveRepository"," 226666    ")
        return leadGenSaveSetterGetter
    }

    private fun saveLeadGenerate(context: Context, strDate :String, ID_LeadFrom :String, ID_LeadThrough :String, ID_CollectedBy :String,
                                 ID_Customer :String, Customer_Name :String, Customer_Address :String, Customer_Mobile :String, Customer_Email :String,
                                 CompanyNme :String,CusPhone :String, ID_MediaMaster :String, FK_Country :String, FK_States :String,
                                 FK_District :String, FK_Post :String, ID_Category :String, ID_Product :String,strProdName :String, strQty :String, ID_Priority :String,
                                 strFeedback :String, ID_Status :String, ID_NextAction :String, ID_ActionType :String, strFollowupdate :String, ID_Branch :String,
                                 ID_BranchType :String, ID_Department :String, ID_Employee :String, strLatitude :String, strLongitue :String, locAddress :String,
                                 encode1 :String, encode2 :String ,saveUpdateMode : String) {

        Log.e("TAG","saveLeadGenerate  ")
        try {
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

//                "ReqMode":"33",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "Pincode":"641231"

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(saveUpdateMode))

                requestObject1.put("LgLeadDate", ProdsuitApplication.encryptStart(strDate))
                requestObject1.put("LgCollectedBy", ProdsuitApplication.encryptStart(ID_CollectedBy))
                requestObject1.put("FK_LeadFrom", ProdsuitApplication.encryptStart(ID_LeadFrom))
                requestObject1.put("FK_LeadBy", ProdsuitApplication.encryptStart(ID_LeadThrough))
                requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(ID_Customer))
                requestObject1.put("LgCusName", ProdsuitApplication.encryptStart(Customer_Name))
                requestObject1.put("LgCusAddress", ProdsuitApplication.encryptStart(Customer_Address))
                requestObject1.put("LgCusMobile", ProdsuitApplication.encryptStart(Customer_Mobile))
                requestObject1.put("LgCusEmail", ProdsuitApplication.encryptStart(Customer_Email))
                requestObject1.put("CusCompany", ProdsuitApplication.encryptStart(CompanyNme))
                requestObject1.put("CusPhone", ProdsuitApplication.encryptStart(CusPhone))
                requestObject1.put("FK_MediaMaster", ProdsuitApplication.encryptStart(ID_MediaMaster))

                requestObject1.put("FK_Country", ProdsuitApplication.encryptStart(FK_Country))
                requestObject1.put("FK_State", ProdsuitApplication.encryptStart(FK_States))
                requestObject1.put("FK_District", ProdsuitApplication.encryptStart(FK_District))
                requestObject1.put("FK_Post", ProdsuitApplication.encryptStart(FK_Post))

                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("ID_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("ProdName", ProdsuitApplication.encryptStart(strProdName))
                requestObject1.put("ProjectName", ProdsuitApplication.encryptStart(strProdName))
                requestObject1.put("LgpPQuantity", ProdsuitApplication.encryptStart(strQty))
                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
                requestObject1.put("LgpDescription", ProdsuitApplication.encryptStart(strFeedback))
                requestObject1.put("ActStatus", ProdsuitApplication.encryptStart(ID_Status))

                requestObject1.put("FK_NetAction", ProdsuitApplication.encryptStart(ID_NextAction))
                requestObject1.put("FK_ActionType", ProdsuitApplication.encryptStart(ID_ActionType))
                requestObject1.put("NextActionDate", ProdsuitApplication.encryptStart(strFollowupdate))
                requestObject1.put("BranchID", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("BranchTypeID", ProdsuitApplication.encryptStart(ID_BranchType))
                requestObject1.put("FK_Departement", ProdsuitApplication.encryptStart(ID_Department))
                requestObject1.put("AssignEmp", ProdsuitApplication.encryptStart(ID_Employee))

                requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(strLatitude))
                requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(strLongitue))
                requestObject1.put("LocationAddress", ProdsuitApplication.encryptStart(locAddress))
//                requestObject1.put("LocationLandMark1", ProdsuitApplication.encryptStart(encode1))
//                requestObject1.put("LocationLandMark2", ProdsuitApplication.encryptStart(encode2))
                requestObject1.put("LocationLandMark1", encode1)
                requestObject1.put("LocationLandMark2", encode2)

                Log.e(TAG,"requestObject1   1361   "+encode1)
                Log.e(TAG,"requestObject1   1362   "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.saveUpdateLeadGeneration(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"response  95   "+response.body())
                        val jObject = JSONObject(response.body())
                        val leadSave = ArrayList<LeadGenerateSaveModel>()
                        Log.e(TAG,"pincode  95   "+leadSave)
                        leadSave.add(LeadGenerateSaveModel(response.body()))
                        val msg = leadSave[0].message
                        leadGenSaveSetterGetter.value = LeadGenerateSaveModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
        }

    }

}