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
import com.perfect.prodsuit.Model.TodoListModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat

object TodoListRepository {

    val todolistSetterGetter = MutableLiveData<TodoListModel>()
    private var progressDialog: ProgressDialog? = null
    fun getServicesApiCall(context: Context,submode : String, name  : String, criteria  : String,date : String,
                           ID_Branch : String , ID_Employee : String, ID_Lead_Details : String,strLeadValue :String)
    : MutableLiveData<TodoListModel> {
        getTodolist(context,submode,name,criteria,date,ID_Branch,ID_Employee,ID_Lead_Details,strLeadValue)
        return todolistSetterGetter
    }

    private fun getTodolist(context: Context,submode : String, name  : String, criteria  : String,date : String,ID_Branch : String , ID_Employee : String, ID_Lead_Details : String,strLeadValue :String) {
        try {
            todolistSetterGetter.value = TodoListModel("")
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

//                {"ReqMode":"Pwq34Rtdxss=\n","SubMode":"8Ld7pH+WkK0=\n","BankKey":"\/mXqmq3ZMvs=\n","FK_Employee":"C9jcamVv4wY=\n",
//                    "Token":"NlkUb1ACgHOTaH2Q1mri2xHtaz0iuN7HwF5Ju6q\/vuGe9XjwVXN9kw==\n","Name":"Ua9c\/VfdCVs=\n","Todate":"mrxcAaqbHMz56wTwHExKBA==\n",
//                    "criteria":"vJ\/8asrP+O0=\n"}

                var formateDate = ""
                if (!date.equals("")){
                    val parser = SimpleDateFormat("dd-MM-yyyy")
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    formateDate = formatter.format(parser.parse(date))
                }


//                {"ReqMode":"Pwq34Rtdxss=\n",
//                    "SubMode":"vJ\/8asrP+O0=\n",
//                    "BankKey":"\/mXqmq3ZMvs=\n",
//                    "FK_Employee":"07\/ybAx1yS4=\n",
//                    "FK_Company":"vJ\/8asrP+O0=\n",
//                    "Token":"dBzn9i\/p2ON5pT6gcWQMYFd87vIMuxCbUyAQhyNQ7dBroLvCSiR7Lg==\n",
//                    "Name":"j4rFcTOFBx0=\n",
//                    "Todate":"j4rFcTOFBx0=\n",
//                    "criteria":"j4rFcTOFBx0=\n",  1 Asc 2 Asc
//                    "BranchCode":2,
//                    "ID_TodoListLeadDetails":2}

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)



                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("24"))
                requestObject1.put("SubMode", ProdsuitApplication.encryptStart(submode))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                //  requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                //  requestObject1.put("Name", ProdsuitApplication.encryptStart(name))
                requestObject1.put("Name", ProdsuitApplication.encryptStart(strLeadValue))
                // requestObject1.put("Todate", ProdsuitApplication.encryptStart(date))
                requestObject1.put("Todate", ProdsuitApplication.encryptStart(formateDate))
                requestObject1.put("criteria", ProdsuitApplication.encryptStart(criteria))
                requestObject1.put("BranchCode", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("ID_TodoListLeadDetails", ProdsuitApplication.encryptStart(ID_Lead_Details))

                Log.e("TAG","requestObject1   741     "+name+"  :  "+date+"  :  "+criteria+"  :  "+submode)
                Log.e("TAG123","requestObject1   7412     "+requestObject1)
                Log.e("TAG","requestObject1   8023     "+formateDate+"   :    "+date)
                Log.e("TAG","requestObject1   8023     "+formateDate+"   :    "+date)

            } catch (e: Exception) {
                Log.i("Exception",e.toString());
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            Log.i("response2erer","body lead="+requestObject1.toString())
            val call = apiService.getLeadManagementDetailsList(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e("TodoList Respose  123   ",response.body())
                        val jObject = JSONObject(response.body())

                        val users = ArrayList<TodoListModel>()
                        users.add(TodoListModel(response.body()))
                        val msg = users[0].message
                        todolistSetterGetter.value = TodoListModel(msg)
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
        catch (e: Exception) {
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
        }
    }

}

