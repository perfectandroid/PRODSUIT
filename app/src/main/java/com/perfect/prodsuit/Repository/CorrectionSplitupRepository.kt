package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.CorrectionSplitupModel
object CorrectionSplitupRepository {

    val correctionSplitupSetterGetter = MutableLiveData<CorrectionSplitupModel>()
    val TAG: String = "CorrectionSplitupRepository"
    private var progressDialog: ProgressDialog? = null

    fun getServicesApiCall(context: Context, strPhone : String): MutableLiveData<CorrectionSplitupModel> {
        getCorrectionSplitup(context, strPhone)
        return correctionSplitupSetterGetter
    }

    private fun getCorrectionSplitup(context: Context, strPhone: String) {
        try {

            var strValu = "{\n" +
                    "  \"ExistCustomerDetails\": {\n" +
                    "    \"ExistCustomerDetailList\": [\n" +
                    "      {\n" +
                    "        \"LeadNo\": \"10249\",\n" +
                    "        \"Customer\": \"ChackoA\",\n" +
                    "        \"Mobile\": \"9847112345\",\n" +
                    "        \"LeadName\": \"Lead Generation\",\n" +
                    "        \"AssignedTo\": \"Chandrasekaran\",\n" +
                    "        \"FollowUpDate\": \"12/09/2023\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"LeadNo\": \"10248\",\n" +
                    "        \"Customer\": \"ChackoA\",\n" +
                    "        \"Mobile\": \"9847112345\",\n" +
                    "        \"LeadName\": \"Lead Generation\",\n" +
                    "        \"AssignedTo\": \"bemp\",\n" +
                    "        \"FollowUpDate\": \"13/09/2023\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"LeadNo\": \"10247\",\n" +
                    "        \"Customer\": \"ChackoA\",\n" +
                    "        \"Mobile\": \"9847112345\",\n" +
                    "        \"LeadName\": \"Lead Generation\",\n" +
                    "        \"AssignedTo\": \"B ELIXIH\",\n" +
                    "        \"FollowUpDate\": \"11/09/2023\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            correctionSplitupSetterGetter.value = CorrectionSplitupModel(strValu)

        }catch (e : Exception){

        }
    }

}