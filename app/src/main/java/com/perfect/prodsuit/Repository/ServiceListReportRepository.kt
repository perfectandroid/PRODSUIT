package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ServiceListReportModel

object ServiceListReportRepository {
    var TAG = "ServiceListReportRepository"
    val serviceListSetterGetter = MutableLiveData<ServiceListReportModel>()
    private var progressDialog: ProgressDialog? = null

    fun getserviceList(context: Context, ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                       ID_CompService: String, ID_ComplaintList: String): MutableLiveData<ServiceListReportModel> {
         getServiceLists(context,ReportMode, ID_Branch, ID_Employee, strFromdate, strTodate, ID_Product, ID_CompService, ID_ComplaintList)
        return serviceListSetterGetter
    }

    private fun getServiceLists(context: Context, ReportMode: String, ID_Branch: String, ID_Employee: String, strFromdate: String, strTodate: String, ID_Product: String?,
                                ID_CompService: String, ID_ComplaintList: String) {

        try {

            var strNewlist ="{\n" +
                    "  \"NewListDetails\": {\n" +
                    "    \"NewListDetailsList\": [\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0551\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Services\": \"Demo product service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"ServiceCost\": \"1000.00\",\n" +
                    "        \"TaxAmount\": \"0.00\",\n" +
                    "        \"TotalAmount\": \"69\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0552\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Services\": \"Demo product service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"ServiceCost\": \"1000.00\",\n" +
                    "        \"TaxAmount\": \"0.00\",\n" +
                    "        \"TotalAmount\": \"69\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0553\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Services\": \"Demo product service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"ServiceCost\": \"1000.00\",\n" +
                    "        \"TaxAmount\": \"0.00\",\n" +
                    "        \"TotalAmount\": \"69\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0554\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Services\": \"Demo product service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"ServiceCost\": \"1000.00\",\n" +
                    "        \"TaxAmount\": \"0.00\",\n" +
                    "        \"TotalAmount\": \"69\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0555\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Services\": \"Demo product service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"ServiceCost\": \"1000.00\",\n" +
                    "        \"TaxAmount\": \"0.00\",\n" +
                    "        \"TotalAmount\": \"69\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0556\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Services\": \"Demo product service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"ServiceCost\": \"1000.00\",\n" +
                    "        \"TaxAmount\": \"0.00\",\n" +
                    "        \"TotalAmount\": \"69\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0557\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Services\": \"Demo product service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"ServiceCost\": \"1000.00\",\n" +
                    "        \"TaxAmount\": \"0.00\",\n" +
                    "        \"TotalAmount\": \"69\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"TicketNo\": \"TKT-0558\",\n" +
                    "        \"TicketDate\": \"04/04/2023\",\n" +
                    "        \"Customer\": \"Mallika\",\n" +
                    "        \"Services\": \"Demo product service\",\n" +
                    "        \"Employees\": \"VYSHAKH PN\",\n" +
                    "        \"ServiceCost\": \"1000.00\",\n" +
                    "        \"TaxAmount\": \"0.00\",\n" +
                    "        \"TotalAmount\": \"69\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            //  val jObject = JSONObject(strNewlist)
            val leads = ArrayList<ServiceListReportModel>()
            leads.add(ServiceListReportModel(strNewlist))
            val msg = leads[0].message
            serviceListSetterGetter.value = ServiceListReportModel(msg)

        }catch (e : Exception){

        }
    }
}