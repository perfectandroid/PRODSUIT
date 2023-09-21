package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.CorrectionLeadModel
import com.perfect.prodsuit.Model.DashboardCommonModel


object DashboardCommonRepository {

    val dashboardCommonSetterGetter = MutableLiveData<DashboardCommonModel>()
    val TAG: String = "DashboardCommonRepository"
    private var progressDialog: ProgressDialog? = null

    fun getServicesApiCall(context: Context, strCustomer : String): MutableLiveData<DashboardCommonModel> {
        getdashboardCommon(context, strCustomer)
        return dashboardCommonSetterGetter
    }

    private fun getdashboardCommon(context: Context, strCustomer: String) {
        try {

            var strValu = "{\n" +
                    "  \"CorrectionDetails\": {\n" +
                    "    \"CorrectionDetailList\": [\n" +
                    "      {\n" +
                    "        \"ID_Category\": \"1\",\n" +
                    "        \"Category\": \"Medium Solar Panel\",\n" +
                    "        \"ID_Product\": \"10\",\n" +
                    "        \"Product\": \"Amaze\",\n" +
                    "        \"MRP\": \"200.00\",\n" +
                    "        \"OfferPrice\": \"150.00\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Category\": \"2\",\n" +
                    "        \"Category\": \"Mega Solar Panel\",\n" +
                    "        \"ID_Product\": \"11\",\n" +
                    "        \"Product\": \"Product 1\",\n" +
                    "        \"MRP\": \"0.00\",\n" +
                    "        \"OfferPrice\": \"210.00\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ID_Category\": \"10249\",\n" +
                    "        \"Category\": \"Medium Solar Panel\",\n" +
                    "        \"ID_Product\": \"12\",\n" +
                    "        \"Product\": \"Product 2\",\n" +
                    "        \"MRP\": \"150.22\",\n" +
                    "        \"OfferPrice\": \"99.25\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            dashboardCommonSetterGetter.value = DashboardCommonModel(strValu)

        }catch (e : Exception){

        }
    }
}