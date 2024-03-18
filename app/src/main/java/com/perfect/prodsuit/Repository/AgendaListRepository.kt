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
import com.perfect.prodsuit.Model.AgendaListModel
import com.perfect.prodsuit.Model.TodoListModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception

object AgendaListRepository {
    //  context = this
    val agendaListSetterGetter = MutableLiveData<AgendaListModel>()
    private var progressDialog: ProgressDialog? = null
    val TAG: String = "AgendaListRepository"
    fun getAgendaApiCall(
        context: Context,
        ReqMode: String,
        SubMode: String,
        ID_FinancePlanType:String,
        AsOnDate: String,
        ID_Category: String,
        ID_Area: String,
        Demand: String,ID_Branch : String,ID_Employee : String
    ): MutableLiveData<AgendaListModel> {
        Log.e("responseww","AsOnDate repo=  "+AsOnDate)
        getAgenda(context,ReqMode,SubMode,ID_FinancePlanType,AsOnDate,ID_Category,ID_Area,Demand,ID_Branch,ID_Employee)
        return agendaListSetterGetter
    }

    private fun getAgenda(
        context: Context,
        ReqMode: String,
        SubMode: String,
        ID_FinancePlanType:String,
        AsOnDate: String,
        ID_Category: String,
        ID_Area: String,
        Demand: String,ID_Branch : String,ID_Employee : String
    ) {

        try {

            agendaListSetterGetter.value = AgendaListModel("")
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
                var formateDate = ""
//            if (!date.equals("")){
//                val parser = SimpleDateFormat("dd-MM-yyyy")
//                val formatter = SimpleDateFormat("yyyy-MM-dd")
//                formateDate = formatter.format(parser.parse(date))
//            }

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_Employee = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(SubMode))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser",null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBySP.getString("UserCode", null)))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(AsOnDate))
                requestObject1.put("ToDate", ProdsuitApplication.encryptStart(AsOnDate))
                requestObject1.put("Demand", ProdsuitApplication.encryptStart(Demand))
                requestObject1.put("FK_FinancePlanType", ProdsuitApplication.encryptStart(ID_FinancePlanType))
                requestObject1.put("FK_Area", ProdsuitApplication.encryptStart(ID_Area))
                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                Log.e(TAG,"requestObject1   agenda "+requestObject1)


            }
            catch (e:Exception)
            {
                Log.i("Exception",e.toString());
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            Log.i("response2erer","body agenda="+requestObject1.toString())
            val call = apiService.getAgendaDetailsList(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e("TodoList Respose  123   ",response.body())
                     //   Log.i("respiouhuih"," response=="+response.body())
                        val jObject = JSONObject(response.body())

                        val users = ArrayList<AgendaListModel>()
                        users.add(AgendaListModel(response.body()))
                        val msg = users[0].message

                        agendaListSetterGetter.value = AgendaListModel(msg)
                      //  Log.i("respiouhuih"," response=="+agendaListSetterGetter.value)
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



        }
        catch (e:Exception)
        {
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }









    }
}