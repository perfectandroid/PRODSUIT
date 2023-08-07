package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ApprovalDetailModel

object ApprovalDetailRepository {

    private var progressDialog: ProgressDialog? = null
    val approvalDetailSetterGetter = MutableLiveData<ApprovalDetailModel>()
    val TAG: String = "ApprovalDetailRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ApprovalDetailModel> {
        getApprovalDetail(context)
        return approvalDetailSetterGetter
    }

    private fun getApprovalDetail(context: Context) {

        try {

            var msg = "{\n" +
                    "  \"AuthRTDetails\": {\n" +
                    "    \"Key1\": [\n" +
                    "      {\n" +
                    "        \"Title\": \"Lead Details\",\n" +
                    "        \"Enquiry Date\": \"04/05/2023\",\n" +
                    "        \"LgLeadNo\": \"000248\",\n" +
                    "        \"Lead Source\": \"Direct\",\n" +
                    "        \"Name\": \"VYSHAKH PN\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"Key2\": [\n" +
                    "      {\n" +
                    "        \"Title\": \"Name & Address\",\n" +
                    "        \"Name\": \"Sona\",\n" +
                    "        \"Address\": \"HiLITE Business Park, 2 nd Floor, Poovangal\",\n" +
                    "        \"Contact No\": \"9879856465\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"Key3\": {\n" +
                    "      \"SubTitle\": \"Lead Summary\",\n" +
                    "      \"Details\": [\n" +
                    "        {\n" +
                    "          \"Title\": \"Lead Details\",\n" +
                    "          \"Enquiry Date\": \"04/05/2023\",\n" +
                    "          \"LgLeadNo\": \"000248\",\n" +
                    "          \"Lead Source\": \"Direct\",\n" +
                    "          \"Name\": \"BHAGYESH \"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"Title\": \"Lead Details\",\n" +
                    "          \"Enquiry Date\": \"04/05/2023\",\n" +
                    "          \"LgLeadNo\": \"000248\",\n" +
                    "          \"Lead Source\": \"Direct\",\n" +
                    "          \"Name\": \"VINEETH\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"Title\": \"Lead Details\",\n" +
                    "          \"Enquiry Date\": \"04/05/2023\",\n" +
                    "          \"LgLeadNo\": \"000248\",\n" +
                    "          \"Lead Source\": \"Direct\",\n" +
                    "          \"Name\": \"VYSHAKH PN\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    \"Key4\": [\n" +
                    "      {\n" +
                    "        \"Title\": \"Entry By\",\n" +
                    "        \"Name\": \"shi0021\",\n" +
                    "        \"Date\": \"02/08/2023\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"Key5\": [\n" +
                    "      {\n" +
                    "        \"Title\": \"Lead Details\",\n" +
                    "        \"Name\": \"Sona\",\n" +
                    "        \"Address\": \"HiLITE Business Park, 2 nd Floor, Poovangal\",\n" +
                    "        \"Contact No\": \"9879856465\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            approvalDetailSetterGetter.value = ApprovalDetailModel(msg)
        }catch (e: Exception){

        }

    }

}