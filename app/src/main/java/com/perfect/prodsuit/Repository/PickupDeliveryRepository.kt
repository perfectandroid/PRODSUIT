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
import com.perfect.prodsuit.Model.PickupDeliveryModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object PickupDeliveryRepository {

    private var progressDialog: ProgressDialog? = null
    val pickupdeliveryCountSetterGetter = MutableLiveData<PickupDeliveryModel>()
    val TAG: String = "PickupDeliveryRepository"

    fun getServicesApiCall(context: Context,FK_Area: String,strFromDate: String, strToDate: String, strarea: String, strCustomer: String, strMobile: String, stProduct: String, strTicketNo: String,status_id: String): MutableLiveData<PickupDeliveryModel> {
        getPickupDeliveryCounts(context, FK_Area,strFromDate,strToDate,strarea,strCustomer,strMobile,stProduct,strTicketNo,status_id)
        return pickupdeliveryCountSetterGetter
    }

    private fun getPickupDeliveryCounts(context: Context,FK_Area: String,strFromDate: String, strToDate: String, strarea: String, strCustomer: String, strMobile: String, strProoduct: String, strTicketNo: String, status_id: String) {

        try {
            pickupdeliveryCountSetterGetter.value = PickupDeliveryModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
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
                val Entr_By  = context.getSharedPreferences(Config.SHARED_PREF36,0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)


                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("94"))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee",null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(Entr_By.getString("UserCode", null)))
                requestObject1.put("FK_BranchCodeUser",ProdsuitApplication.encryptStart("1"))
                requestObject1.put("SubMode",ProdsuitApplication.encryptStart("0"))
                requestObject1.put("Status", ProdsuitApplication.encryptStart(status_id))
                requestObject1.put("FromDate", ProdsuitApplication.encryptStart(strFromDate))
                requestObject1.put("ToDate", ProdsuitApplication.encryptStart(strToDate))
                requestObject1.put("FK_Area", ProdsuitApplication.encryptStart(FK_Area))
                requestObject1.put("FK_Product", ProdsuitApplication.encryptStart(strProoduct))
                requestObject1.put("CusName", ProdsuitApplication.encryptStart(strCustomer))
                requestObject1.put("CusMobile", ProdsuitApplication.encryptStart(strMobile))
                requestObject1.put("TicketNo", ProdsuitApplication.encryptStart(strTicketNo))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"requestObject1   8888   "+requestObject1)
                Log.e(TAG,"requestObject1   4444  "+strFromDate)
                Log.e(TAG,"requestObject1   4444  "+strToDate)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getPickupandDeliveryCount(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        Log.e(AgendaCountRepository.TAG,"requestObject1   771   "+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<PickupDeliveryModel>()
                        leads.add(PickupDeliveryModel(response.body()))
                        val msg = leads[0].message
                        pickupdeliveryCountSetterGetter.value = PickupDeliveryModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
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