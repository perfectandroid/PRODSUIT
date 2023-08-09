package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ApprovalListModel
import com.perfect.prodsuit.Model.ReasonModel

object ReasonRepository {

    private var progressDialog: ProgressDialog? = null
    val reasonSetterGetter = MutableLiveData<ReasonModel>()
    val TAG: String = "ReasonRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ReasonModel> {
        getReason(context)
        return reasonSetterGetter
    }

    private fun getReason(context: Context) {
        try {

            var msg = "{\n" +
                    "  \"ReasonDetails\": {\n" +
                    "    \"ReasonDetailList\": [\n" +
                    "      {\n" +
                    "        \"ID_Reason\": \"1\",\n" +
                    "        \"Reason\": \"Duplicate\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Reason\": \"2\",\n" +
                    "        \"Reason\": \"InCorrect\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            reasonSetterGetter.value = ReasonModel(msg)
        }catch (e: Exception){

        }
    }
}