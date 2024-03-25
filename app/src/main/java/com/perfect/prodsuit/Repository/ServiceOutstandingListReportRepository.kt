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
import com.perfect.prodsuit.Model.ServiceOutstandingListReportModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ServiceOutstandingListReportRepository {

    var TAG = "ServiceOutstandingListReportRepository"
    val serviceOutstandingListSetterGetter = MutableLiveData<ServiceOutstandingListReportModel>()
    private var progressDialog: ProgressDialog? = null

    fun getserviceOutstandingList(context: Context, ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                          ID_CompService: String, ID_ComplaintList: String): MutableLiveData<ServiceOutstandingListReportModel> {
        getServiceOutstandingList(context, ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)
        return serviceOutstandingListSetterGetter
    }

    private fun getServiceOutstandingList(context: Context,ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                                  ID_CompService: String, ID_ComplaintList: String) {

        try {
            serviceOutstandingListSetterGetter.value = ServiceOutstandingListReportModel("")
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

                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)

//                {"Token":"02350582-6179-4097-93F6-17FEFB46CC83","FK_Company":"1","FK_BranchCodeUser":"3","Fk_Branch":"3","EntrBy":"SONAKM",
//                    "FK_Employee":"0","FromDate":"2023-10-28","FK_Employee":"0","ToDate":"2023-11-28","FK_Area":"0","FK_Product":"0",
//                    "FK_Priority":"0","ReportMode":"3","FK_Machine":"10","Criteria":"0","TableCount":"1","Status":"5","ComplaintType":"0",
//                    "FK_Category":"0","DueCriteria":"1","FK_NextAction":"0","DueDaysFrom":"0","DueDaysTo":"0","ComplaintService":"0",
//                    "ReplacementType":"0","TicketNo":""}

                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("FK_Branch", ProdsuitApplication.encryptStart(ID_Branch))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", UserCodeSP.getString("UserCode", null))
                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("FromDate", strFromdate)
                requestObject1.put("ToDate", strTodate)
                requestObject1.put("FK_Area", "0")
                requestObject1.put("FK_Product", ID_Product)
                requestObject1.put("FK_Priority", "0")
                requestObject1.put("ReportMode", "3")
                requestObject1.put("FK_Machine", "10")
                requestObject1.put("Criteria", "0")
                requestObject1.put("TableCount", "1")
                requestObject1.put("Status", "5")
                requestObject1.put("ComplaintType", ID_CompService)
                requestObject1.put("FK_Category", "0")
                requestObject1.put("DueCriteria", "1")
                requestObject1.put("FK_NextAction", "0")
                requestObject1.put("DueDaysFrom", "0")
                requestObject1.put("DueDaysTo", "0")
                requestObject1.put("ComplaintService", "0")
                requestObject1.put("ReplacementType", "0")
                requestObject1.put("TicketNo", "")
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))



                Log.e(TAG,"78  getBranch  gjgyjghjk"+requestObject1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.getOutstanding(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()

                        val jObject = JSONObject(response.body())
                        val leads = ArrayList<ServiceOutstandingListReportModel>()
                        leads.add(ServiceOutstandingListReportModel(response.body()))
                        val msg = leads[0].message
                        serviceOutstandingListSetterGetter.value = ServiceOutstandingListReportModel(msg)



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





        /*  try {

              var strNewlist ="{\n" +
                      "  \"NewListDetails\": {\n" +
                      "    \"NewListDetailsList\": [\n" +
                      "      {\n" +
                      "        \"TicketNo\": \"TKT-0551\",\n" +
                      "        \"TicketDate\": \"04/04/2023\",\n" +
                      "        \"Customer\": \"Mallika\",\n" +
                      "        \"Product\": \"Solar Panel\",\n" +
                      "        \"Complaint\": \"Server Issue\",\n" +
                      "        \"Mobile\": \"98797987987\",\n" +
                      "        \"Area\": \"NADUVANNUR\",\n" +
                      "        \"Due\": \"69\",\n" +
                      "        \"CurrentStatus\": \"Pending\",\n" +
                      "        \"Description\": \"\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"TicketNo\": \"TKT-0552\",\n" +
                      "        \"TicketDate\": \"04/04/2023\",\n" +
                      "        \"Customer\": \"Mallika\",\n" +
                      "        \"Product\": \"Solar Panel\",\n" +
                      "        \"Complaint\": \"Server Issue\",\n" +
                      "        \"Mobile\": \"98797987987\",\n" +
                      "        \"Area\": \"NADUVANNUR\",\n" +
                      "        \"Due\": \"69\",\n" +
                      "        \"CurrentStatus\": \"Pending\",\n" +
                      "        \"Description\": \"\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"TicketNo\": \"TKT-0553\",\n" +
                      "        \"TicketDate\": \"04/04/2023\",\n" +
                      "        \"Customer\": \"Mallika\",\n" +
                      "        \"Product\": \"Solar Panel\",\n" +
                      "        \"Complaint\": \"Server Issue\",\n" +
                      "        \"Mobile\": \"98797987987\",\n" +
                      "        \"Area\": \"NADUVANNUR\",\n" +
                      "        \"Due\": \"69\",\n" +
                      "        \"CurrentStatus\": \"Pending\",\n" +
                      "        \"Description\": \"\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"TicketNo\": \"TKT-0554\",\n" +
                      "        \"TicketDate\": \"04/04/2023\",\n" +
                      "        \"Customer\": \"Mallika\",\n" +
                      "        \"Product\": \"Solar Panel\",\n" +
                      "        \"Complaint\": \"Server Issue\",\n" +
                      "        \"Mobile\": \"98797987987\",\n" +
                      "        \"Area\": \"NADUVANNUR\",\n" +
                      "        \"Due\": \"69\",\n" +
                      "        \"CurrentStatus\": \"Pending\",\n" +
                      "        \"Description\": \"\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"TicketNo\": \"TKT-0555\",\n" +
                      "        \"TicketDate\": \"04/04/2023\",\n" +
                      "        \"Customer\": \"Mallika\",\n" +
                      "        \"Product\": \"Solar Panel\",\n" +
                      "        \"Complaint\": \"Server Issue\",\n" +
                      "        \"Mobile\": \"98797987987\",\n" +
                      "        \"Area\": \"NADUVANNUR\",\n" +
                      "        \"Due\": \"69\",\n" +
                      "        \"CurrentStatus\": \"Pending\",\n" +
                      "        \"Description\": \"\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"TicketNo\": \"TKT-0556\",\n" +
                      "        \"TicketDate\": \"04/04/2023\",\n" +
                      "        \"Customer\": \"Mallika\",\n" +
                      "        \"Product\": \"Solar Panel\",\n" +
                      "        \"Complaint\": \"Server Issue\",\n" +
                      "        \"Mobile\": \"98797987987\",\n" +
                      "        \"Area\": \"NADUVANNUR\",\n" +
                      "        \"Due\": \"69\",\n" +
                      "        \"CurrentStatus\": \"Pending\",\n" +
                      "        \"Description\": \"\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"TicketNo\": \"TKT-0557\",\n" +
                      "        \"TicketDate\": \"04/04/2023\",\n" +
                      "        \"Customer\": \"Mallika\",\n" +
                      "        \"Product\": \"Solar Panel\",\n" +
                      "        \"Complaint\": \"Server Issue\",\n" +
                      "        \"Mobile\": \"98797987987\",\n" +
                      "        \"Area\": \"NADUVANNUR\",\n" +
                      "        \"Due\": \"69\",\n" +
                      "        \"CurrentStatus\": \"Pending\",\n" +
                      "        \"Description\": \"\"\n" +
                      "      },\n" +
                      "      {\n" +
                      "        \"TicketNo\": \"TKT-0558\",\n" +
                      "        \"TicketDate\": \"04/04/2023\",\n" +
                      "        \"Customer\": \"Mallika\",\n" +
                      "        \"Product\": \"Solar Panel\",\n" +
                      "        \"Complaint\": \"Server Issue\",\n" +
                      "        \"Mobile\": \"98797987987\",\n" +
                      "        \"Area\": \"NADUVANNUR\",\n" +
                      "        \"Due\": \"69\",\n" +
                      "        \"CurrentStatus\": \"Pending\",\n" +
                      "        \"Description\": \"\"\n" +
                      "      }\n" +
                      "    ],\n" +
                      "    \"ResponseCode\": \"0\",\n" +
                      "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                      "  },\n" +
                      "  \"StatusCode\": 0,\n" +
                      "  \"EXMessage\": \"Transaction Verified\"\n" +
                      "}"

              //  val jObject = JSONObject(strNewlist)
              val leads = ArrayList<ServiceOutstandingListReportModel>()
              leads.add(ServiceOutstandingListReportModel(strNewlist))
              val msg = leads[0].message
              serviceOutstandingListSetterGetter.value = ServiceOutstandingListReportModel(msg)

          }
          catch (e : Exception){

          }*/
    }
}