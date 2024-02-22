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
import com.perfect.prodsuit.Model.CommonModel
import com.perfect.prodsuit.Model.ProductPriorityModel
import com.perfect.prodsuit.Model.ServiceProductModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList


object ServiceProductRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceProductSetterGetter = MutableLiveData<ServiceProductModel>()
    val TAG: String = "ServiceProductRepository"

    fun getServicesApiCall(context: Context,ReqMode: String, SubMode: String,Customer_Type: String,ID_Customer: String,ID_Category : String): MutableLiveData<ServiceProductModel> {
        getServiceProductSetterGetter(context,ReqMode,SubMode,Customer_Type,ID_Customer,ID_Category)
        return serviceProductSetterGetter
    }

    private fun getServiceProductSetterGetter(context: Context,ReqMode: String, SubMode: String,Customer_Type: String,ID_Customer: String,ID_Category : String) {

        try {
            serviceProductSetterGetter.value = ServiceProductModel("")
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

//                {"ReqMode":"P2QbSr+YQ68=","BankKey":"\/mXqmq3ZMvs=\n","FK_Company":"vJ\/8asrP+O0=\n",
//                    "Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","FK_Customer":"aeHFs1nH9so="}

//                {"ReqMode":"P2QbSr+YQ68=","BankKey":"\/mXqmq3ZMvs=\n","FK_Company":"vJ\/8asrP+O0=\n",
//                    "Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","FK_Customer":"aeHFs1nH9so="}

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)


                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart(ReqMode))
                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("ID_Category", ProdsuitApplication.encryptStart(ID_Category))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))
               // requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                if (Customer_Type.equals("0")){
                    Log.e(LeadGenerateSaveRepository.TAG,"642121   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart(ID_Customer))
                }else if (Customer_Type.equals("1")){
                    Log.e(LeadGenerateSaveRepository.TAG,"642122   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(ID_Customer))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart("0"))
                }else{
                    Log.e(LeadGenerateSaveRepository.TAG,"642123   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart("0"))
                }

                Log.e(TAG,"requestObject1   971   "+requestObject1)
                Log.e(TAG,"requestObject1   972   ReqMode  :  "+ReqMode+"   Customer_Type  :  "+Customer_Type+"   ID_Customer  :  "+ID_Customer)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getServiceProductDetails(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {

                        Log.e(TAG," 973 "+response.body())
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ServiceProductModel>()
                        leads.add(ServiceProductModel(response.body()))
                        val msg = leads[0].message
                        serviceProductSetterGetter.value = ServiceProductModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        progressDialog!!.dismiss()
                        Toast.makeText(context,""+e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }

//        val msg  ="{\"ProductHistoryDetails\": {\"ProductHistoryDetailsList\": [{\"TicketNo\":\"TKT101\",\"RegOn\": \"06/06/2022\",\"Complaint\": \"Battery Check\",\"Status\": \"Pending\",\"AttendedBy\": \"AIswarya\",\"Employee\": \"Need to replace the product. Board is damaged \"},{\"TicketNo\":\"TKT102\",\"RegOn\": \"07/06/2022\",\"Complaint\": \"Maintenance Services\",\"Status\": \"Completed\",\"AttendedBy\": \"\tSurya\",\"Employee\": \"Need to replace the product. \"},{\"TicketNo\":\"TKT103\",\"RegOn\": \"08/06/2022\",\"Complaint\": \"Battery Check\",\"Status\": \"Ongoing\",\"AttendedBy\": \"Jerald\",\"Employee\": \"Need to replace the product\"},{\"TicketNo\":\"TKT104\",\"RegOn\": \"09/06/2022\",\"Complaint\": \"Maintenance Services\",\"Status\": \"Pending\",\"AttendedBy\": \"\tSurya\",\"Employee\": \"\"}],\"ResponseCode\": \"0\",\"ResponseMessage\": \"Transaction Verified\"},\"StatusCode\": 0,\"EXMessage\": \"Transaction Verified\"}"
//        serviceProductSetterGetter.value = ServiceProductModel(msg)
    }

}