package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ServiceNewListReportModel
import java.util.ArrayList

object ServiceNewListReportRepository {

    var TAG = "ServiceNewListReportRepository"
    val serviceNewListSetterGetter = MutableLiveData<ServiceNewListReportModel>()
    private var progressDialog: ProgressDialog? = null

    fun getserviceNewList(context: Context,ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                          ID_CompService: String, ID_ComplaintList: String): MutableLiveData<ServiceNewListReportModel> {
        getServiceNewList(context,ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)
        return serviceNewListSetterGetter
    }

    private fun getServiceNewList(context: Context,ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                                  ID_CompService: String, ID_ComplaintList: String) {

        try {

            var strNewlist ="{\n" +
                    "  \"NewListDetails\": {\n" +
                    "    \"NewListDetailsList\": [\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0551\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Product\": \"Solar Panel\",\n" +
                    "        \"Complaint\": \"Server Issue\",\n" +
                    "        \"CurrentStatus\": \"Pending\",\n" +
                    "        \"Description\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0552\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Product\": \"Solar Panel\",\n" +
                    "        \"Complaint\": \"Server Issue\",\n" +
                    "        \"CurrentStatus\": \"Pending\",\n" +
                    "        \"Description\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0553\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Product\": \"Solar Panel\",\n" +
                    "        \"Complaint\": \"Server Issue\",\n" +
                    "        \"CurrentStatus\": \"Pending\",\n" +
                    "        \"Description\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0554\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Product\": \"Solar Panel\",\n" +
                    "        \"Complaint\": \"Server Issue\",\n" +
                    "        \"CurrentStatus\": \"Pending\",\n" +
                    "        \"Description\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0555\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Product\": \"Solar Panel\",\n" +
                    "        \"Complaint\": \"Server Issue\",\n" +
                    "        \"CurrentStatus\": \"Pending\",\n" +
                    "        \"Description\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0556\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Product\": \"Solar Panel\",\n" +
                    "        \"Complaint\": \"Server Issue\",\n" +
                    "        \"CurrentStatus\": \"Pending\",\n" +
                    "        \"Description\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0557\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Product\": \"Solar Panel\",\n" +
                    "        \"Complaint\": \"Server Issue\",\n" +
                    "        \"CurrentStatus\": \"Pending\",\n" +
                    "        \"Description\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0558\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Product\": \"Solar Panel\",\n" +
                    "        \"Complaint\": \"Server Issue\",\n" +
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
            val leads = ArrayList<ServiceNewListReportModel>()
            leads.add(ServiceNewListReportModel(strNewlist))
            val msg = leads[0].message
            serviceNewListSetterGetter.value = ServiceNewListReportModel(msg)

        }catch (e : Exception){

        }
    }
}