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
import com.perfect.prodsuit.Model.BranchModel
import com.perfect.prodsuit.Model.SentIntimationModel
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
import java.util.concurrent.TimeUnit

object SentIntimationRepository {

    private var progressDialog: ProgressDialog? = null
    val branchSetterGetter = MutableLiveData<SentIntimationModel>()
    val TAG: String = "BranchRepository"

    fun getServicesApiCall(
        context: Context,
        dated: String,
        ID_module: String,
        ID_Branch: String,
        ID_Channel: String,
        ID_Shedule: String,
        encodeDoc: String,
        extension: String,
        message: String,
        ScheduledDate: String,
        ScheduledTime: String,
        ID_LeadSource: String,
        ID_LeadInfo: String,
        FromDate: String,
        ToDate: String,
        ID_Category: String?,
        ID_ProductType: String?,
        ID_Product: String,
        ID_Employee: String,
        ID_CollectedBy: String?,
        idArea: String,
        ID_NextAction: String,
        ID_ActionType: String,
        ID_Priority: String?,
        SearchBy: String,
        SearchBydetails: String,
        GridData: String,
        LeadCusDetails: JSONArray
    ): MutableLiveData<SentIntimationModel> {
        sentIntimation(context,dated,ID_module,ID_Branch,ID_Channel,ID_Shedule,encodeDoc,extension,message,
            ScheduledDate,
            ScheduledTime,
            ID_LeadSource,
            ID_LeadInfo,
            FromDate,
            ToDate,
            ID_Category,
            ID_ProductType,
            ID_Product,
            ID_Employee,
            ID_CollectedBy,
            idArea,
            ID_NextAction,
            ID_ActionType,
            ID_Priority,
            SearchBy,
            SearchBydetails,
            GridData,
            LeadCusDetails

            )
        return branchSetterGetter
    }

    private fun sentIntimation(
        context: Context,
        dated: String,
        ID_module: String,
        ID_Branch: String,
        ID_Channel: String,
        ID_Shedule: String,
        encodeDoc: String,
        extension: String,
        message: String,
        ScheduledDate: String,
        ScheduledTime: String,
        ID_LeadSource: String,
        ID_LeadInfo: String,
        FromDate: String,
        ToDate: String,
        ID_Category: String?,
        ID_ProductType: String?,
        ID_Product: String,
        ID_Employee: String,
        ID_CollectedBy: String?,
        ID_Area: String,
        ID_NextAction: String,
        ID_ActionType: String,
        ID_Priority: String?,
        SearchBy: String,
        SearchBydetails: String,
        GridData: String,
        LeadCusDetails: JSONArray
    ) {
        Log.e(TAG,"encodeDoc_repos   5083333  "+encodeDoc)

        try {
            branchSetterGetter.value = SentIntimationModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
                R.drawable.progress))
            progressDialog!!.show()
            val client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS).
                readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
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
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)


                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val currentDate = sdf.format(Date())

                var attachement=""
                if(encodeDoc.equals(""))
                {
                    attachement=""
                }
                else
                {
                    attachement=encodeDoc+"."+extension
                  //  attachement=encodeDoc
                }

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart("1"))
                requestObject1.put("ID_CommonIntimation", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("SheduledType", ProdsuitApplication.encryptStart(ID_Shedule))
                requestObject1.put("Message", ProdsuitApplication.encryptStart(message))
                requestObject1.put("Unicode", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("Subject", ProdsuitApplication.encryptStart(""))
                requestObject1.put("Status", ProdsuitApplication.encryptStart("0"))
                requestObject1.put("Channel", ProdsuitApplication.encryptStart(ID_Channel))
                requestObject1.put("DLId", ProdsuitApplication.encryptStart(""))
             // requestObject1.put("Attachment", ProdsuitApplication.encryptStart(attachement))
               requestObject1.put("Attachment", encodeDoc)
                requestObject1.put("Module", ProdsuitApplication.encryptStart(ID_module))
                requestObject1.put("Branch", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("Date", ProdsuitApplication.encryptStart(dated+" 00:00:00"))//
                requestObject1.put("SheduledTime", ProdsuitApplication.encryptStart(ScheduledTime))//
                requestObject1.put("SheduledDate", ProdsuitApplication.encryptStart(ScheduledDate))//

                requestObject1.put("ID_LeadFrom", ProdsuitApplication.encryptStart(ID_LeadSource))
                requestObject1.put("FK_LeadThrough", ProdsuitApplication.encryptStart(ID_LeadInfo))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(FromDate))
                requestObject1.put("ToDate", ProdsuitApplication.encryptStart(ToDate))

                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("ProdType", ProdsuitApplication.encryptStart(ID_ProductType))
                requestObject1.put("ID_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))

                requestObject1.put("Collectedby_ID", ProdsuitApplication.encryptStart(ID_CollectedBy))
                requestObject1.put("Area_ID", ProdsuitApplication.encryptStart(ID_Area))

                requestObject1.put("FK_NetAction", ProdsuitApplication.encryptStart(ID_NextAction))
                requestObject1.put("FK_ActionType", ProdsuitApplication.encryptStart(ID_ActionType))

                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))

                requestObject1.put("SearchBy", ProdsuitApplication.encryptStart(SearchBy))
                requestObject1.put("SearchBydetails", ProdsuitApplication.encryptStart(SearchBydetails))

                requestObject1.put("GridData", ProdsuitApplication.encryptStart(GridData))

                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))

                requestObject1.put("LeadCusDetails", (LeadCusDetails))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"intimation 4545454  "+requestObject1)
                Log.e(TAG,"intimation 66666  "+encodeDoc)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.sentIntimation(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("Branchresponse",response.body())
                        val leads = ArrayList<BranchModel>()
                        leads.add(BranchModel(response.body()))
                        val msg = leads[0].message
                        branchSetterGetter.value = SentIntimationModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Log.e("yhgfhgf 4654645",t.toString())
                    Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Toast.makeText(context,"DFSFSDF"+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
            progressDialog!!.dismiss()
        }
    }

}