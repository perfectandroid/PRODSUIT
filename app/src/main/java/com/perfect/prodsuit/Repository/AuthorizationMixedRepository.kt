package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.AuthorizationMixedModel

object AuthorizationMixedRepository {

    private var progressDialog: ProgressDialog? = null
    val authorizationMixedSetterGetter = MutableLiveData<AuthorizationMixedModel>()
    val TAG: String = "AuthorizationMixed"

    fun getServicesApiCall(context: Context): MutableLiveData<AuthorizationMixedModel> {
        getAuthorizationMixed(context)
        return authorizationMixedSetterGetter
    }

    private fun getAuthorizationMixed(context: Context) {

//        try {
//            authorizationMixedSetterGetter.value = AuthorizationMixedModel("")
//            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
//            progressDialog = ProgressDialog(context, R.style.Progress)
//            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//            progressDialog!!.setCancelable(false)
//            progressDialog!!.setIndeterminate(true)
//            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
//                R.drawable.progress))
//            progressDialog!!.show()
//            val client = OkHttpClient.Builder()
//                .sslSocketFactory(Config.getSSLSocketFactory(context))
//                .hostnameVerifier(Config.getHostnameVerifier())
//                .build()
//            val gson = GsonBuilder()
//                .setLenient()
//                .create()
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URLSP.getString("BASE_URL", null))
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build()
//            val apiService = retrofit.create(ApiInterface::class.java!!)
//            val requestObject1 = JSONObject()
//
//            try {
//                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
//                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
//                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
//                val FK_UserRoleSP = context.getSharedPreferences(Config.SHARED_PREF41, 0)
//                val ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
//
////                {"BankKey":"-500","Token":"F5517387-B815-4DCC-B2CC-E0A2F3160E22","FK_Company":"1","FK_UserGroup":"14","FK_User":"1"}
//
//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
//                requestObject1.put("FK_UserGroup", ProdsuitApplication.encryptStart(FK_UserRoleSP.getString("FK_UserRole", null)))
//                requestObject1.put("FK_User", ProdsuitApplication.encryptStart(ID_UserSP.getString("ID_User", null)))
//
//                Log.e(TAG,"78 getAuthorizationModuleList  "+requestObject1)
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            val body = RequestBody.create(
//                okhttp3.MediaType.parse("application/json; charset=utf-8"),
//                requestObject1.toString()
//            )
//            val call = apiService.getAuthorizationModuleList(body)
//            call.enqueue(object : retrofit2.Callback<String> {
//                override fun onResponse(
//                    call: retrofit2.Call<String>, response:
//                    Response<String>
//                ) {
//                    try {
//                        progressDialog!!.dismiss()
//                        Log.e(TAG,"78 jObject  "+response.body())
//
//                        val jObject = JSONObject(response.body())
//                        Log.e(TAG,"78 jObject  "+jObject)
//                        val leads = ArrayList<AuthorizationMixedModel>()
//                        leads.add(AuthorizationMixedModel(response.body()))
//                        val msg = leads[0].message
//                        authorizationMixedSetterGetter.value = AuthorizationMixedModel(msg)
//                    } catch (e: Exception) {
//                        progressDialog!!.dismiss()
//                        Log.e(TAG,"78 jObject  "+e.toString())
//                        Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES+"1", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
//                    progressDialog!!.dismiss()
//                    Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES+"12", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }catch (e : Exception){
//            e.printStackTrace()
//            progressDialog!!.dismiss()
//            Toast.makeText(context,""+ Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
//        }

        try {

//            var msg = "{\n" +

//                    "  \"AuthorizationList\": {\n" +
//                    "    \"ListData\": [\n" +
//                    "      {\n" +
//                    "        \"SlNo\": 1,\n" +
//                    "        \"ID_FIELD\": 99,\n" +
//                    "        \"Action\": \"Lead Generate\",\n" +
//                    "        \"TransactionNo\": \"LD-000185\",\n" +
//                    "        \"Date\": \"12/10/2023\",\n" +
//                    "        \"drank\": 1,\n" +
//                    "        \"EnteredBy\": \"VYSHAKH PN \",\n" +
//                    "        \"EnteredOn\": \"12/10/2023\",\n" +
//                    "        \"TotalCount\": 1\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"SlNo\": 1,\n" +
//                    "        \"ID_FIELD\": 99,\n" +
//                    "        \"Action\": \"Lead Generate\",\n" +
//                    "        \"TransactionNo\": \"LD-000185\",\n" +
//                    "        \"Date\": \"12/10/2023\",\n" +
//                    "        \"drank\": 1,\n" +
//                    "        \"EnteredBy\": \"VYSHAKH PN \",\n" +
//                    "        \"EnteredOn\": \"12/10/2023\",\n" +
//                    "        \"TotalCount\": 1\n" +
//                    "      }\n" +
//                    "    ],\n" +
//                    "    \"ResponseCode\": \"0\",\n" +
//                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
//                    "  },\n" +
//                    "  \"StatusCode\": 0,\n" +
//                    "  \"EXMessage\": \"Transaction Verified\"\n" +
//                    "}"

            authorizationMixedSetterGetter.value = AuthorizationMixedModel()

            var msg = "{\n" +
                    "    \"AuthorizationDataList\": {\n" +
                    "        \"Lead\": [\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 1,\n" +
                    "                \"TransNumber\": \"LD-000001\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Anson\",\n" +
                    "                \"CusMobile\": \"9526218069\",\n" +
                    "                \"CusAddress\": \"tesr\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 2,\n" +
                    "                \"TransNumber\": \"LD-000002\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Ajith Kumar\",\n" +
                    "                \"CusMobile\": \"9874563215\",\n" +
                    "                \"CusAddress\": \"Thrissur\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"riyaske\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 3,\n" +
                    "                \"TransNumber\": \"LD-000003\",\n" +
                    "                \"TransDate\": \"08-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Apoorva\",\n" +
                    "                \"CusMobile\": \"09090901233\",\n" +
                    "                \"CusAddress\": \"\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"Service\": [\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 1,\n" +
                    "                \"TransNumber\": \"LD-000001\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Anson\",\n" +
                    "                \"CusMobile\": \"9526218069\",\n" +
                    "                \"CusAddress\": \"tesr\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 2,\n" +
                    "                \"TransNumber\": \"LD-000002\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Ajith Kumar\",\n" +
                    "                \"CusMobile\": \"9874563215\",\n" +
                    "                \"CusAddress\": \"Thrissur\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"riyaske\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 3,\n" +
                    "                \"TransNumber\": \"LD-000003\",\n" +
                    "                \"TransDate\": \"08-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Apoorva\",\n" +
                    "                \"CusMobile\": \"09090901233\",\n" +
                    "                \"CusAddress\": \"\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 6,\n" +
                    "                \"TransNumber\": \"LD-000005\",\n" +
                    "                \"TransDate\": \"08-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"ANSON PAUL ANTONY\",\n" +
                    "                \"CusMobile\": \"9526218067\",\n" +
                    "                \"CusAddress\": \"PURATHOOR\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 10,\n" +
                    "                \"TransNumber\": \"LD-000009\",\n" +
                    "                \"TransDate\": \"11-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Mahin\",\n" +
                    "                \"CusMobile\": \"8075099011\",\n" +
                    "                \"CusAddress\": \"Kollam\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"Project\": [\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 1,\n" +
                    "                \"TransNumber\": \"LD-000001\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Anson\",\n" +
                    "                \"CusMobile\": \"9526218069\",\n" +
                    "                \"CusAddress\": \"tesr\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 2,\n" +
                    "                \"TransNumber\": \"LD-000002\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Ajith Kumar\",\n" +
                    "                \"CusMobile\": \"9874563215\",\n" +
                    "                \"CusAddress\": \"Thrissur\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"riyaske\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 3,\n" +
                    "                \"TransNumber\": \"LD-000003\",\n" +
                    "                \"TransDate\": \"08-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Apoorva\",\n" +
                    "                \"CusMobile\": \"09090901233\",\n" +
                    "                \"CusAddress\": \"\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"Collection\": [\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 1,\n" +
                    "                \"TransNumber\": \"LD-000001\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Anson\",\n" +
                    "                \"CusMobile\": \"9526218069\",\n" +
                    "                \"CusAddress\": \"tesr\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 2,\n" +
                    "                \"TransNumber\": \"LD-000002\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Ajith Kumar\",\n" +
                    "                \"CusMobile\": \"9874563215\",\n" +
                    "                \"CusAddress\": \"Thrissur\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"riyaske\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 3,\n" +
                    "                \"TransNumber\": \"LD-000003\",\n" +
                    "                \"TransDate\": \"08-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Apoorva\",\n" +
                    "                \"CusMobile\": \"09090901233\",\n" +
                    "                \"CusAddress\": \"\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 6,\n" +
                    "                \"TransNumber\": \"LD-000005\",\n" +
                    "                \"TransDate\": \"08-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"ANSON PAUL ANTONY\",\n" +
                    "                \"CusMobile\": \"9526218067\",\n" +
                    "                \"CusAddress\": \"PURATHOOR\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 10,\n" +
                    "                \"TransNumber\": \"LD-000009\",\n" +
                    "                \"TransDate\": \"11-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Mahin\",\n" +
                    "                \"CusMobile\": \"8075099011\",\n" +
                    "                \"CusAddress\": \"Kollam\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 10010,\n" +
                    "                \"TransNumber\": \"LD-000010\",\n" +
                    "                \"TransDate\": \"12-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Balakrishnan\",\n" +
                    "                \"CusMobile\": \"9696969696\",\n" +
                    "                \"CusAddress\": \"\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"PickupAndDelivery\": [\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 1,\n" +
                    "                \"TransNumber\": \"LD-000001\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Anson\",\n" +
                    "                \"CusMobile\": \"9526218069\",\n" +
                    "                \"CusAddress\": \"tesr\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 2,\n" +
                    "                \"TransNumber\": \"LD-000002\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Ajith Kumar\",\n" +
                    "                \"CusMobile\": \"9874563215\",\n" +
                    "                \"CusAddress\": \"Thrissur\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"riyaske\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 3,\n" +
                    "                \"TransNumber\": \"LD-000003\",\n" +
                    "                \"TransDate\": \"08-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Apoorva\",\n" +
                    "                \"CusMobile\": \"09090901233\",\n" +
                    "                \"CusAddress\": \"\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"Account\": [\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 1,\n" +
                    "                \"TransNumber\": \"LD-000001\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Anson\",\n" +
                    "                \"CusMobile\": \"9526218069\",\n" +
                    "                \"CusAddress\": \"tesr\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 2,\n" +
                    "                \"TransNumber\": \"LD-000002\",\n" +
                    "                \"TransDate\": \"07-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Ajith Kumar\",\n" +
                    "                \"CusMobile\": \"9874563215\",\n" +
                    "                \"CusAddress\": \"Thrissur\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"riyaske\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FK_Transaction\": 3,\n" +
                    "                \"TransNumber\": \"LD-000003\",\n" +
                    "                \"TransDate\": \"08-09-2023 00:00:00\",\n" +
                    "                \"CusName\": \"Apoorva\",\n" +
                    "                \"CusMobile\": \"09090901233\",\n" +
                    "                \"CusAddress\": \"\",\n" +
                    "                \"Priority\": \"Hot\",\n" +
                    "                \"EnteredBy\": \"VYSHAKH\",\n" +
                    "                \"Reason\": \"\",\n" +
                    "                \"Amount\": \"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"ResponseCode\": \"0\",\n" +
                    "        \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "    },\n" +
                    "    \"StatusCode\": 0,\n" +
                    "    \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            authorizationMixedSetterGetter.value = AuthorizationMixedModel(msg)
        }catch (e: Exception){

        }
    }
}