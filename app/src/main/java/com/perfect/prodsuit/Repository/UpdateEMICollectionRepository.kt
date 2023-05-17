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
import com.perfect.prodsuit.Model.UpdateEMICollectionModel
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

object UpdateEMICollectionRepository {

    val TAG: String = "UpdateEMICollectionRepository"
    val updateEMICollectionSetterGetter = MutableLiveData<UpdateEMICollectionModel>()
    private var progressDialog: ProgressDialog? = null

    fun getServicesApiCall(context: Context,strSaveTrnsDate : String, ID_CustomerWiseEMI : String, strSaveCollectDate : String, strSaveTotalAmount : String, strSaveFineAmount : String,
                           strSaveNetAmount : String, ID_CollectedBy : String, saveEmiDetailsArray : JSONArray, savePaymentDetailArray: JSONArray,
                           strLongitue : String,strLatitude : String,strLocationAddress : String): MutableLiveData<UpdateEMICollectionModel> {
        UpdateEMICollectionData(context,strSaveTrnsDate,ID_CustomerWiseEMI,strSaveCollectDate,strSaveTotalAmount,strSaveFineAmount,
            strSaveNetAmount,ID_CollectedBy,saveEmiDetailsArray,savePaymentDetailArray,strLongitue,strLatitude,strLocationAddress)
        return updateEMICollectionSetterGetter
    }

    private fun UpdateEMICollectionData(context: Context,strSaveTrnsDate : String, ID_CustomerWiseEMI : String, strSaveCollectDate : String, strSaveTotalAmount : String, strSaveFineAmount : String,
                                        strSaveNetAmount : String, ID_CollectedBy : String, saveEmiDetailsArray : JSONArray, savePaymentDetailArray:JSONArray,
                                        strLongitue : String,strLatitude : String,strLocationAddress : String) {

        Log.e(TAG,"VALUES  1411"
                +"\n  TrnsDate      "+strSaveTrnsDate
                +"\n ID_CustomerWiseEMI      "+ID_CustomerWiseEMI
                +"\n CollectDate      "+strSaveCollectDate
                +"\n TotalAmount     "+strSaveTotalAmount
                +"\n  FineAmount    "+strSaveFineAmount
                +"\n  NetAmount    "+strSaveNetAmount
                +"\n  FK_Employee    "+ID_CollectedBy
                +"\n  FK_Employee    "+ID_CollectedBy


                +"\n  saveEmiDetailsArray    "+saveEmiDetailsArray
                +"\n  savePaymentDetailArray    "+savePaymentDetailArray
        )


        try {
            updateEMICollectionSetterGetter.value = UpdateEMICollectionModel("")
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

                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val newDate1: Date = sdf.parse(strSaveTrnsDate)
                val newDate2: Date = sdf.parse(strSaveCollectDate)
                val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")


                var strSaveTrnsDateNew = sdfDate1.format(newDate1)
                var strSaveCollectDateNew = sdfDate1.format(newDate2)

                Log.e(TAG,"strSaveTrnsDateNew    536   :  "+strSaveTrnsDateNew)
                Log.e(TAG,"strSaveCollectDateNew   536   :  "+strSaveCollectDateNew)


                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val Fkcompanysp = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)


//                {"BankKey":"-500","Token":"F2E50452-6F21-44A4-B81D-733A59DEE381","TrnsDate":"2023-05-08","FK_Company":"1","FK_BranchCodeUser":"3","EntrBy":"Sree","AccountMode":"2",
//                    "ID_CustomerWiseEMI":"33","CollectDate":"2023-05-08","TotalAmount":"3960","FineAmount":"140","NetAmount":"4100","FK_Employee":"9","EMIDetails":[{"FK_CustomerWiseEMI":"33",
//                        "CusTrDetPayAmount":"3960","CusTrDetFineAmount":"140","Total":"4100","Balance":"0","FK_Closed":"1"}],"PaymentDetail":[{"PaymentMethod":"17","Refno":"5789879","PAmount":"4100"}]}
//                1-LocLatitude
//                2-LocLongitude
//                3-Address

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("TrnsDate", ProdsuitApplication.encryptStart(strSaveTrnsDateNew))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(Fkcompanysp.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserNameSP.getString("UserCode", null)))
                requestObject1.put("AccountMode", ProdsuitApplication.encryptStart("2"))
                requestObject1.put("ID_CustomerWiseEMI", ProdsuitApplication.encryptStart(ID_CustomerWiseEMI))
                requestObject1.put("CollectDate", ProdsuitApplication.encryptStart(strSaveCollectDateNew))
                requestObject1.put("TotalAmount", ProdsuitApplication.encryptStart(strSaveTotalAmount))
                requestObject1.put("FineAmount", ProdsuitApplication.encryptStart(strSaveFineAmount))
                requestObject1.put("NetAmount", ProdsuitApplication.encryptStart(strSaveNetAmount))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_CollectedBy))
                requestObject1.put("EMIDetails", saveEmiDetailsArray)
                requestObject1.put("PaymentDetail", savePaymentDetailArray)
                requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(strLatitude))
                requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(strLongitue))
                requestObject1.put("Address", ProdsuitApplication.encryptStart(strLocationAddress))


               Log.e(TAG,"114  SAVE   "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.saveUpdateEMICollection(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("save FollowUp  ",response.body())
                        val leads = ArrayList<UpdateEMICollectionModel>()
                        leads.add(UpdateEMICollectionModel(response.body()))
                        val msg = leads[0].message
                        updateEMICollectionSetterGetter.value = UpdateEMICollectionModel(msg)
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
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
            progressDialog!!.dismiss()
        }



    }
}