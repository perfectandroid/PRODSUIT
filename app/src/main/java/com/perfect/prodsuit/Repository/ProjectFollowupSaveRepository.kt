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
import com.perfect.prodsuit.Model.ProjectFollowupSaveModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ProjectFollowupSaveRepository {

    private var progressDialog: ProgressDialog? = null
    val projectFollowupSaveSetterGetter = MutableLiveData<ProjectFollowupSaveModel>()
    val TAG: String = "ProjectFollowupSaveRepository"

    fun getServicesApiCall(context: Context,UserAction : String,ID_Project : String,ID_Stage : String,strFollowupdate : String,ID_CurrentStatus : String,
                           strStatudate : String,strReason : String,strRemarks : String): MutableLiveData<ProjectFollowupSaveModel> {
        saveProjectFollowup(context,UserAction,ID_Project ,ID_Stage ,strFollowupdate,ID_CurrentStatus,
            strStatudate ,strReason ,strRemarks)
        return projectFollowupSaveSetterGetter
    }

    private fun saveProjectFollowup(context: Context,UserAction : String,ID_Project : String,ID_Stage : String,strFollowupdate : String,ID_CurrentStatus : String,
                                    strStatudate : String,strReason : String,strRemarks : String) {

        try {
            projectFollowupSaveSetterGetter.value = ProjectFollowupSaveModel("")
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
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)


//                { "UserAction":"1","FK_Project":"2","FK_Stage":"111","EffectDate":"2023-10-31","CurrentStatus":"2","StatusDate":"2023-10-31","Remarks":"Test",
//                    "FK_Company":"1","FK_BranchCodeUser":"3","EntrBy":"VYSHAKH","Reason":"Test"}


                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(UserAction))
                requestObject1.put("FK_Project", ProdsuitApplication.encryptStart(ID_Project))
                requestObject1.put("FK_Stage", ProdsuitApplication.encryptStart(ID_Stage))
                requestObject1.put("EffectDate", ProdsuitApplication.encryptStart(Config.convertDate(strFollowupdate)))
                requestObject1.put("CurrentStatus", ProdsuitApplication.encryptStart(ID_CurrentStatus))
                requestObject1.put("StatusDate", ProdsuitApplication.encryptStart(Config.convertDate(strStatudate)))
                requestObject1.put("Remarks", ProdsuitApplication.encryptStart(strRemarks))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("EntrBy", null)))
                requestObject1.put("Reason", ProdsuitApplication.encryptStart(strReason))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))


                Log.e(TAG,"93333  SaveProjectFollowUp    "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.saveUpdateProjectFollowUp(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ProjectFollowupSaveModel>()
                        leads.add(ProjectFollowupSaveModel(response.body()))
                        val msg = leads[0].message
                        projectFollowupSaveSetterGetter.value = ProjectFollowupSaveModel(msg)
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