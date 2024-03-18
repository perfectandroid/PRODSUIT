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
import com.perfect.prodsuit.Model.SendMessageModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object SendMessageRepository {

    private var progressDialog: ProgressDialog? = null
    val sendmessagesetterGetter = MutableLiveData<SendMessageModel>()
    val TAG: String = "SendMessageRepository"

    fun getServicesApiCall(context: Context,messageTitle :String,messageDesc: String,Reciever_Id :String): MutableLiveData<SendMessageModel> {
        getSendMessage(context,messageTitle,messageDesc,Reciever_Id)
        return sendmessagesetterGetter
    }

    private fun getSendMessage(context: Context,messageTitle :String,messageDesc: String,Reciever_Id :String){

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


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val Id_UserSP = context.getSharedPreferences(Config.SHARED_PREF44,0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(Id_UserSP.getString("ID_User", null)))
                requestObject1.put("TransMode", ProdsuitApplication.encryptStart("ERP"))
                requestObject1.put("Reciever", ProdsuitApplication.encryptStart(Reciever_Id))
                requestObject1.put("Title", ProdsuitApplication.encryptStart(messageTitle))
                requestObject1.put("Message", ProdsuitApplication.encryptStart(messageDesc))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


//                requestObject1.put("FK_Action", ProdsuitApplication.encryptStart(ID_NextAction))
//                requestObject1.put("FK_ActionType", ProdsuitApplication.encryptStart(ID_ActionType))
//                requestObject1.put("FollowupDate", ProdsuitApplication.encryptStart(strDate))
//                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
//                requestObject1.put("FK_Departement", ProdsuitApplication.encryptStart(ID_Department))
//                requestObject1.put("FK_ToEmployee", ProdsuitApplication.encryptStart(ID_Employee))


                Log.e(TAG,"requestObject1   442442   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.notificationUpdate(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"saveAddNextAction  118   "+response.body())
                        val jObject = JSONObject(response.body())
                        val nxtAction = ArrayList<SendMessageModel>()
                        nxtAction.add(SendMessageModel(response.body()))
                        val msg = nxtAction[0].message
                        Log.e(TAG,"saveAddNextAction  1181   "+msg)
                        sendmessagesetterGetter.value = SendMessageModel(msg)
                    } catch (e: Exception) {
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
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
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