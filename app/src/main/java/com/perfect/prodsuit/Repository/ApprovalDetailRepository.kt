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
import com.perfect.prodsuit.Model.ApprovalDetailModel
import com.perfect.prodsuit.Model.ApprovalModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ApprovalDetailRepository {

    private var progressDialog: ProgressDialog? = null
    val approvalDetailSetterGetter = MutableLiveData<ApprovalDetailModel>()
    val TAG: String = "ApprovalDetailRepository"

    fun getServicesApiCall(context: Context,Module : String,AuthID : String): MutableLiveData<ApprovalDetailModel> {
        getApprovalDetail(context,Module,AuthID)
        return approvalDetailSetterGetter
    }

    private fun getApprovalDetail(context: Context,Module : String,AuthID : String) {

        try {
            approvalDetailSetterGetter.value = ApprovalDetailModel("")
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
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_UserRoleSP = context.getSharedPreferences(Config.SHARED_PREF41, 0)
                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

//                {"BankKey":"-500","Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","FK_Company":"1","FK_UserGroup":"13","FK_User":"67","Module":"LF","AuthID":"17"}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))

                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_UserGroup", ProdsuitApplication.encryptStart(FK_UserRoleSP.getString("FK_UserRole", null)))
                requestObject1.put("FK_User", ProdsuitApplication.encryptStart(ID_UserSP.getString("ID_User", null)))
                requestObject1.put("Module", ProdsuitApplication.encryptStart(Module))
                requestObject1.put("AuthID", ProdsuitApplication.encryptStart(AuthID))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))





                Log.e(TAG,"855 getAuthorizationAction  "+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getAuthorizationAction(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ApprovalDetailModel>()
                        leads.add(ApprovalDetailModel(response.body()))
                        val msg = leads[0].message
                        approvalDetailSetterGetter.value = ApprovalDetailModel(msg)
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
            progressDialog!!.dismiss()
            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
        }


//        try {
//
//            var msg = "{\n" +
//                    "  \"AuthRTDetails\": {\n" +
//                    "    \"Key1\": [\n" +
//                    "      {\n" +
//                    "        \"Title\": \"Lead Details\",\n" +
//                    "        \"Enquiry Date\": \"04/05/2023\",\n" +
//                    "        \"LgLeadNo\": \"000248\",\n" +
//                    "        \"Lead Source\": \"Direct\",\n" +
//                    "        \"Name\": \"VYSHAKH PN\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"Key2\": [\n" +
//                    "      {\n" +
//                    "        \"Title\": \"Name & Address\",\n" +
//                    "        \"Name\": \"Sona\",\n" +
//                    "        \"Address\": \"HiLITE Business Park, 2 nd Floor, Poovangal\",\n" +
//                    "        \"Contact No\": \"9879856465\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"Key3\": {\n" +
//                    "      \"SubTitle\": \"Lead Summary\",\n" +
//                    "      \"Details\": [\n" +
//                    "        {\n" +
//                    "          \"Title\": \"Lead Details\",\n" +
//                    "          \"Enquiry Date\": \"04/05/2023\",\n" +
//                    "          \"LgLeadNo\": \"000248\",\n" +
//                    "          \"Lead Source\": \"Direct\",\n" +
//                    "          \"Name\": \"BHAGYESH \"\n" +
//                    "        },\n" +
//                    "        {\n" +
//                    "          \"Title\": \"Lead Details\",\n" +
//                    "          \"Enquiry Date\": \"04/05/2023\",\n" +
//                    "          \"LgLeadNo\": \"000248\",\n" +
//                    "          \"Lead Source\": \"Direct\",\n" +
//                    "          \"Name\": \"VINEETH\"\n" +
//                    "        },\n" +
//                    "        {\n" +
//                    "          \"Title\": \"Lead Details\",\n" +
//                    "          \"Enquiry Date\": \"04/05/2023\",\n" +
//                    "          \"LgLeadNo\": \"000248\",\n" +
//                    "          \"Lead Source\": \"Direct\",\n" +
//                    "          \"Name\": \"VYSHAKH PN\"\n" +
//                    "        }\n" +
//                    "      ]\n" +
//                    "    },\n" +
//                    "    \"Key4\": [\n" +
//                    "      {\n" +
//                    "        \"Title\": \"Entry By\",\n" +
//                    "        \"Name\": \"shi0021\",\n" +
//                    "        \"Date\": \"02/08/2023\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"Key5\": [\n" +
//                    "      {\n" +
//                    "        \"Title\": \"Lead Details\",\n" +
//                    "        \"Name\": \"Sona\",\n" +
//                    "        \"Address\": \"HiLITE Business Park, 2 nd Floor, Poovangal\",\n" +
//                    "        \"Contact No\": \"9879856465\"\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"
//
//            approvalDetailSetterGetter.value = ApprovalDetailModel(msg)
//        }catch (e: Exception){
//
//        }

    }

}