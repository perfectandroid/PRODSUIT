package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ApprovalModel

object ApprovalRepository {

    private var progressDialog: ProgressDialog? = null
    val approvalSetterGetter = MutableLiveData<ApprovalModel>()
    val TAG: String = "ApprovalRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ApprovalModel> {
        getApproval(context)
        return approvalSetterGetter
    }

    private fun getApproval(context: Context) {

        try {

            var msg = "{\n" +
                    "  \"ApprovalDetails\": {\n" +
                    "    \"ApprovalDetailList\": [\n" +
                    "      {\n" +
                    "        \"Title\": \"Lead\",\n" +
                    "        \"Count\": \"14\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Title\": \"Service\",\n" +
                    "        \"Count\": \"35\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Title\": \"Master\",\n" +
                    "        \"Count\": \"12\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Title\": \"Inventory\",\n" +
                    "        \"Count\": \"24\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            approvalSetterGetter.value = ApprovalModel(msg)
        }catch (e: Exception){

        }

    }
}