package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Model.ProjectPayInfoModel
import com.perfect.prodsuit.Model.ProjectTransactionTypeModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object ProjectTransactionTypeRepository {

    private var progressDialog: ProgressDialog? = null
    val projectTransactionTypeSetGet = MutableLiveData<ProjectTransactionTypeModel>()
    val TAG: String = "ProjectTransactionTypeRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ProjectTransactionTypeModel> {
        getProjectTransactionType(context)
        return projectTransactionTypeSetGet
    }

    private fun getProjectTransactionType(context: Context) {
        try {
            projectTransactionTypeSetGet.value = ProjectTransactionTypeModel("")

            val msg = "{\n" +
                    "  \"TransTypeDetails\": {\n" +
                    "    \"TransTypeList\": [\n" +
                    "      {\n" +
                    "        \"ID_TransType\": \"1\",\n" +
                    "        \"TransType\": \"Fund Allocation\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_TransType\": \"1\",\n" +
                    "        \"TransType\": \"Fund Spend\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_TransType\": \"1\",\n" +
                    "        \"TransType\": \"Fund Return\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_TransType\": \"1\",\n" +
                    "        \"TransType\": \"Project Transaction\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_TransType\": \"1\",\n" +
                    "        \"TransType\": \"Petty Cash Inward\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_TransType\": \"1\",\n" +
                    "        \"TransType\": \"Petty Cash Outward\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"
            projectTransactionTypeSetGet.value = ProjectTransactionTypeModel(msg)
        }catch (e : Exception){

        }



    }

}