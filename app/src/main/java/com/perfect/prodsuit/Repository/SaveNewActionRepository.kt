package com.perfect.prodsuit.Repository

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.SaveNewActionModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object SaveNewActionRepository {

    private var progressDialog: ProgressDialog? = null
    val savenewactionsetterGetter = MutableLiveData<SaveNewActionModel>()
    val TAG: String = "SaveNewActionRepository"

    fun getServicesApiCall(context: Context,ID_Category : String, ID_Product : String, ID_NextAction : String, ID_ActionType : String, strDate : String,
                           ID_Department : String, ID_Employee : String): MutableLiveData<SaveNewActionModel> {
        saveNewAction(context, ID_Category,ID_Product,ID_NextAction, ID_ActionType, strDate, ID_Department, ID_Employee)

        return savenewactionsetterGetter
    }

    private fun saveNewAction(context: Context, ID_Category : String, ID_Product : String,ID_NextAction: String, ID_ActionType: String,
                              strDate: String, ID_Department: String, ID_Employee: String) {

        Log.e(TAG,"SAVE NEW ACTION"
                +"\n"+"ID_Category     : "+ ID_Category
                +"\n"+"ID_NextAction   : "+ ID_NextAction
                +"\n"+"ID_ActionType   : "+ ID_ActionType
                +"\n"+"strDate         : "+ strDate
                +"\n"+"ID_Department   : "+ ID_Department
                +"\n"+"ID_Employee     : "+ ID_Employee)

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


//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//
//                "FK_Category":"1",
//                "FK_Product":"1",
//                "FK_Action":1,
//                "NextActionDate":"2022-04-06 ",
//                FK_Departement =1
//                "FK_ToEmployee":1


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))

                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(ID_Product))
                requestObject1.put("FK_Action", ProdsuitApplication.encryptStart(ID_NextAction))
             //   requestObject1.put("FK_ActionType", ProdsuitApplication.encryptStart(ID_ActionType))
                requestObject1.put("NextActionDate", ProdsuitApplication.encryptStart(strDate))
                requestObject1.put("FK_Departement", ProdsuitApplication.encryptStart(ID_Department))
                requestObject1.put("FK_ToEmployee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))


                Log.e(TAG,"requestObject1   102   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.saveAddNewAction(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        Log.e(TAG,"saveAddNewAction  118   "+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val nxtAction = ArrayList<SaveNewActionModel>()
                        nxtAction.add(SaveNewActionModel(response.body()))
                        val msg = nxtAction[0].message
                        savenewactionsetterGetter.value = SaveNewActionModel(msg)
                    } catch (e: Exception) {
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    progressDialog!!.dismiss()
                    val builder = AlertDialog.Builder(
                        context,
                        R.style.MyDialogTheme
                    )
                    builder.setMessage("Some Technical Issue")
                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            progressDialog!!.dismiss()
            val builder = AlertDialog.Builder(
                context,
                R.style.MyDialogTheme
            )
            builder.setMessage("Some Technical Issue")
            builder.setPositiveButton("Ok") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }
}


