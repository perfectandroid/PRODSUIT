package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Repository.FollowUpTypeRepository
import com.perfect.prodsuit.View.Adapter.AssignedTicketsAdapter
import com.perfect.prodsuit.Viewmodel.AssignedTicketListViewModel
import com.perfect.prodsuit.Viewmodel.HeaderTestViewModel
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class HeaderTestActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    val TAG: String = "HeaderTestActivity"
    lateinit var headerTestViewModel: HeaderTestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header_test)
        headerTestViewModel = ViewModelProvider(this).get(HeaderTestViewModel::class.java)

     //   getHeaderData()
        getHeaders()
    }

    private fun getHeaders() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                headerTestViewModel.getHeaderTest(this,)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {


                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   assignedtickets   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("ServiceAssignedWork")

                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(this,jObject)
                                }
                                else {
                                    val builder = AlertDialog.Builder(
                                        this@HeaderTestActivity,
                                        R.style.MyDialogTheme
                                    )
                                    builder.setMessage(jObject.getString("EXMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }


                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun getHeaderData() {
        try {

            val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(applicationContext, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(applicationContext.resources.getDrawable(
                R.drawable.progress))
            progressDialog!!.show()

          //  val TokenSP = applicationContext.getSharedPreferences(Config.SHARED_PREF5, 0)

//            val headerInterceptor = Interceptor { chain ->
//                val request: Request = chain.request()
//                    .newBuilder()
//                  //  .addHeader("Authorization", "TokenSP.getString("Token", null)")
//                  //  .addHeader("Authorization", "717A9D7E-CA07-4371-859F-B500DDEAA54A")
//                    .build()
//                chain.proceed(request)
//            }

            val client = OkHttpClient.Builder()
                .sslSocketFactory(Config.getSSLSocketFactory(this))
                .hostnameVerifier(Config.getHostnameVerifier())
               // .addInterceptor(headerInterceptor)
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


//
               // val FK_EmployeeSP = getSharedPreferences(Config.SHARED_PREF1, 0)
//                val BankKeySP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
//                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
//                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
               // val UserCodeSP = getSharedPreferences(Config.SHARED_PREF36, 0)
//                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
              //  val ID_TokenUserSP = getSharedPreferences(Config.SHARED_PREF85, 0)

//                {"ReqMode":"130","FK_Company":"1","FK_Master":"40","EntrBy":"SONAKM"}


                requestObject1.put("ReqMode", ProdsuitApplication.encryptStart("130"))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart("1"))
//                requestObject1.put("FK_Master", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
             //   requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))

                Log.e(TAG,"79991 UserDetailswithHeaderchecking  "+requestObject1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
//            val headerMap = HashMap<String, String>()
//            headerMap["Authorization"] = "Bearer $717A9D7E-CA07-4371-859F-B500DDEAA54A"
//            headerMap["Content-Type"] = "application/json"
            val call = apiService.getUserDetailswithHeaderchecking("perfect_url",body)
            call!!.enqueue(object : retrofit2.Callback<String?> {
                override fun onResponse(call: Call<String?>?, response: Response<String?>) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"79992  "+response.body())
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        Log.e(TAG,"79993  "+e.toString())
                    }
                }

                override fun onFailure(call: Call<String?>?, t: Throwable?) {
                    Log.e(TAG,"79994  "+t.toString())
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            Log.e(TAG,"79995 jObject  "+e.toString())
            Toast.makeText(applicationContext,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }
    }
}