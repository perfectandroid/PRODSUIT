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
import com.perfect.prodsuit.Model.AgendaCountModel
import com.perfect.prodsuit.Model.AgendaDetailModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object AgendaDetailRepository {

    private var progressDialog: ProgressDialog? = null
    val agendaDetailSetterGetter = MutableLiveData<AgendaDetailModel>()
    val TAG: String = "AgendaDetailRepository"

    fun getServicesApiCall(context: Context,ID_ActionType : String ,SubMode : String,Id_Agenda : String, name : String,date : String, criteria : String): MutableLiveData<AgendaDetailModel> {
        getAgendaDetail(context,ID_ActionType,SubMode,Id_Agenda, name,date, criteria)
        return agendaDetailSetterGetter
    }

    private fun getAgendaDetail(context: Context,ID_ActionType : String ,SubMode : String,Id_Agenda : String, name : String,date : String, criteria : String) {

        try {
            agendaDetailSetterGetter.value = AgendaDetailModel("")
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

//                "ReqMode":"43",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "ID_ActionType:"1",
//                "SubMode:"1"

//                {"ReqMode":"auUEMC+jdGg=\n","BankKey":"\/mXqmq3ZMvs=\n","FK_Employee":"C9jcamVv4wY=\n",
//                    "Token":"\/5rBx4fwNIcSGqZdWCf+L58o2XZj5E\/vnvRXz7rh91NFknPbbSK\/3w==\n",
//                    "ID_ActionType":"KZtbclVmL7w=\n","SubMode":"vJ\/8asrP+O0=\n","Id_Agenda":"vJ\/8asrP+O0=\n",
//                    "Name":"j4rFcTOFBx0=\n","Todate":"14sAcoSwi6n9b\/3WNKFIWA==\n","criteria":"j4rFcTOFBx0=\n"}


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)

                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("43"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_ActionType", ProdsuitApplication.encryptStart(ID_ActionType))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(SubMode))
                requestObject1.put("Id_Agenda", ProdsuitApplication.encryptStart(Id_Agenda))

                requestObject1.put("Name", ProdsuitApplication.encryptStart(name))
                requestObject1.put("Todate", ProdsuitApplication.encryptStart(date))
                requestObject1.put("criteria", ProdsuitApplication.encryptStart(criteria))

                Log.e(TAG,"Id_Agenda   78   "+Id_Agenda+"  ::::  "+SubMode+"  ::::  "+name+"  ::::  "+date+"  ::::  "+criteria)
                Log.e(TAG,"requestObject1   78   "+requestObject1)
                Log.e(TAG,"requestObject1   78   "+ID_ActionType+"   "+ID_ActionType)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getAgendaDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<AgendaDetailModel>()
                        leads.add(AgendaDetailModel(response.body()))
                        val msg = leads[0].message
                        agendaDetailSetterGetter.value = AgendaDetailModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
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