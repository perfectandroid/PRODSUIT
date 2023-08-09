package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ApprovalListModel

object ApprovalListRepository {

    private var progressDialog: ProgressDialog? = null
    val approvalListSetterGetter = MutableLiveData<ApprovalListModel>()
    val TAG: String = "ApprovalListRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ApprovalListModel> {
        getApprovalList(context)
        return approvalListSetterGetter
    }

    private fun getApprovalList(context: Context) {
        try {

            var msg = "{\n" +
                    "  \"ApprovalDetails\": {\n" +
                    "    \"ApprovalDetailList\": [\n" +
                    "      {\n" +
                    "        \"Action\": \"Lead Generate\",\n" +
                    "        \"Transaction No\": \"000248\",\n" +
                    "        \"Date\": \"02/08/2023 \",\n" +
                    "        \"Entered By\": \"SONA\",\n" +
                    "        \"Entered On\": \"02/08/2023 \"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Action\": \"Lead Generate\",\n" +
                    "        \"Transaction No\": \"000249\",\n" +
                    "        \"Date\": \"02/08/2023 \",\n" +
                    "        \"Entered By\": \"Shi\",\n" +
                    "        \"Entered On\": \"02/08/2023 \"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Action\": \"Lead Generate\",\n" +
                    "        \"Transaction No\": \"000250\",\n" +
                    "        \"Date\": \"02/08/2023 \",\n" +
                    "        \"Entered By\": \"VYSHAKH\",\n" +
                    "        \"Entered On\": \"02/08/2023 \"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Action\": \"Lead Generate\",\n" +
                    "        \"Transaction No\": \"000251\",\n" +
                    "        \"Date\": \"02/08/2023 \",\n" +
                    "        \"Entered By\": \"BHAGHYESH\",\n" +
                    "        \"Entered On\": \"02/08/2023 \"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            approvalListSetterGetter.value = ApprovalListModel(msg)
        }catch (e: Exception){

        }
    }
}