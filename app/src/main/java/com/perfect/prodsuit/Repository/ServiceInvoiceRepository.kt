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
import com.perfect.prodsuit.Model.ServiceAssignDetailsModel
import com.perfect.prodsuit.Model.ServiceAssignModel
import com.perfect.prodsuit.Model.ServiceInvoiceModel

import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceInvoiceRepository {

    private var progressDialog: ProgressDialog? = null
    val subProductSetterGetter = MutableLiveData<ServiceInvoiceModel>()
    val TAG: String = "SubProductRepository"

    fun getServicesApiCall(context: Context, FkEmployee : String,

                           productDetails : JSONArray): MutableLiveData<ServiceInvoiceModel> {
        getSubProduct(context,FkEmployee,productDetails)
        return subProductSetterGetter
    }

    private fun getSubProduct(context: Context,FkEmployee : String,productDetails : JSONArray)
    {
       // Log.i("resp900","array==   "+productDetails)
        try {
            subProductSetterGetter.value = ServiceInvoiceModel("")
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
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)


//                {"BankKey":"\/mXqmq3ZMvs=\n","Token":"0KjNuKHR16rDwHCS09BASBwyc4DHIeNqEVyN8kfrQtASybLeZjOwwA==\n","FK_Customerserviceregister":"1",
//                    "FK_Employee":"1","Visitdate":"2023-02-01","Visittime":"02:05","FK_Priority":"2","Remark":"Test","FK_Company":"1","FK_BranchCodeUser":"3",
//                    "EntrBy":"APP","FK_Branch":"3",Assignees{["FK_Employee":"1","EmployeeType":"3"]}


                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FkEmployee))
                requestObject1.put("ProductSubDetails", productDetails)
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))


                Log.e(TAG,"requestObject1   7812   "+requestObject1)
                Log.i("resp900","body main==   "+requestObject1)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getClosedTicketList(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"response  111     "+response.body())
                     //   Log.i("resp900","response==   "+response.body())
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ServiceInvoiceModel>()
                        leads.add(ServiceInvoiceModel(response.body()))
                        val msg = leads[0].message
                        subProductSetterGetter.value = ServiceInvoiceModel(msg)
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

    }

}