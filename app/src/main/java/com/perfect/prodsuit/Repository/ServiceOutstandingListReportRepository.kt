package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ServiceOutstandingListReportModel
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

        }catch (e : Exception){

        }
    }
}