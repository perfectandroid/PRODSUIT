package com.perfect.prodsuit.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.StandByModel
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object StandByRepository {

    val standBySetterGetter = MutableLiveData<StandByModel>()
    var TAG = "StandByRepository"
    fun getServicesApiCall(context: Context, ID_ProductDelivery : String, PickDeliveryTime : String, PickDeliveryDate : String,remark : String,FK_BillType : String, Productdetails : JSONArray, PaymentDetail : JSONArray,DeliveryComplaints : JSONArray,StandByAmount :String,Status :String,strLongitue : String, strLatitude: String, locAddress :String): MutableLiveData<StandByModel> {
        StandBy(context,ID_ProductDelivery,PickDeliveryTime,PickDeliveryDate,remark,FK_BillType,Productdetails,PaymentDetail,DeliveryComplaints,StandByAmount,Status,strLongitue,strLatitude,locAddress)
        return standBySetterGetter
    }

    private fun StandBy(context: Context, ID_ProductDelivery : String, PickDeliveryTime : String, PickDeliveryDate : String, remark : String, FK_BillType : String, Productdetails : JSONArray, PaymentDetail : JSONArray, DeliveryComplaints : JSONArray, StandByAmount :String, Status :String, strLongitue : String, strLatitude: String, locAddress :String) {
        try {
            standBySetterGetter.value = StandByModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
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
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_Company = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP     = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val EntrBy     = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)

                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_Company.getString("FK_Company", null)))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(EntrBy.getString("UserCode", null)))
                requestObject1.put("FK_ProductDeliveryAssign", ProdsuitApplication.encryptStart(ID_ProductDelivery))
                requestObject1.put("PdfDeliveryTime", ProdsuitApplication.encryptStart(PickDeliveryTime))
                requestObject1.put("PdfDeliveryDate", ProdsuitApplication.encryptStart(PickDeliveryDate))
                requestObject1.put("Remark", ProdsuitApplication.encryptStart(remark))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("Status", (Status))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))

                requestObject1.put("LocLatitude", (strLatitude))
                requestObject1.put("LocLongitude", (strLongitue))
                requestObject1.put("Address", (locAddress))

                requestObject1.put("Productdetails", (Productdetails))
                requestObject1.put("PaymentDetail", (PaymentDetail))
                requestObject1.put("DeliveryComplaints", (DeliveryComplaints))



                Log.e(TAG,"requestObject1   52   "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Log.e(TAG,"request   "+requestObject1)
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getUpdateStandbyDelivery(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        val jObject = JSONObject(response.body())
                        Log.i("Splashresposne",response.body())
                        val users = ArrayList<StandByModel>()
                        users.add(StandByModel(response.body()))
                        val msg = users[0].message
                        standBySetterGetter.value = StandByModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                }
            })
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}