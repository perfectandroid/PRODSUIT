package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.PickupAndDeliveryVehicleDetailsModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object PickupAndDeliveryVehicleDetailRepository {

    var TAG = "PickupAndDeliveryVehicleDetailRepository"
    private var progressDialog: ProgressDialog? = null
    val pickupAndDeliveryVehicleDetailgetSet = MutableLiveData<PickupAndDeliveryVehicleDetailsModel>()

    fun getServicesApiCall(context: Context, TransDate: String, DashMode: String, DashType: String): MutableLiveData<PickupAndDeliveryVehicleDetailsModel> {
        getPickupAndDeliveryVehicleDetail(context, TransDate, DashMode, DashType)
        return pickupAndDeliveryVehicleDetailgetSet

    }

    private fun getPickupAndDeliveryVehicleDetail(context: Context, TransDate: String, DashMode: String, DashType: String) {
        try {
            pickupAndDeliveryVehicleDetailgetSet.value = PickupAndDeliveryVehicleDetailsModel("")
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

                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val FK_DepartmentSP = context.getSharedPreferences(Config.SHARED_PREF55, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

//                {"FK_Employee":"40","EntrBy":"SONAKM","FK_Department":"1","FK_Branch":"3","FK_Company":"1","FK_BranchCodeUser":"3",
//                    "TransDate":"2024-1-08","DashMode":"37","DashType":"2"}


                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("FK_Department", ProdsuitApplication.encryptStart(FK_DepartmentSP.getString("FK_Department", null)))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(FK_BranchSP.getString("FK_Branch", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("TransDate", ProdsuitApplication.encryptStart(Config.convertDate(TransDate)))
                requestObject1.put("DashMode", ProdsuitApplication.encryptStart(DashMode))
                requestObject1.put("DashType", ProdsuitApplication.encryptStart(DashType))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))



                Log.e(TAG, "requestObject1   791   " + requestObject1.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getPickDeliveryVehicleDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                          progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        Log.i("NotifreadResponse", response.body())
                        val users = ArrayList<PickupAndDeliveryVehicleDetailsModel>()
                        users.add(PickupAndDeliveryVehicleDetailsModel(response.body()))
                        val msg = users[0].message
                        pickupAndDeliveryVehicleDetailgetSet.value = PickupAndDeliveryVehicleDetailsModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                          progressDialog!!.dismiss()
                        //  Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                      progressDialog!!.dismiss()
                    // Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
              progressDialog!!.dismiss()
            //   Toast.makeText(context,""+Config.SOME_TECHNICAL_ISSUES,Toast.LENGTH_SHORT).show()
        }
    }
}