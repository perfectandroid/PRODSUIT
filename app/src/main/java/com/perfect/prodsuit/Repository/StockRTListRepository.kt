package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.StockRTListModel

object StockRTListRepository {

    private var progressDialog: ProgressDialog? = null
    val stockRTListSetterGetter = MutableLiveData<StockRTListModel>()
    val TAG: String = "StockRTListRepository"

    fun getServicesApiCall(context: Context, ID_Category : String, ID_Product : String): MutableLiveData<StockRTListModel> {
        getStockRTList(context, ID_Category, ID_Product)
        return stockRTListSetterGetter
    }

    private fun getStockRTList(context: Context, ID_Category: String, ID_Product: String) {

        try {

            var msg = "{\n" +
                    "  \"StockRTDetails\": {\n" +
                    "    \"StockRTList\": [\n" +
                    "      {\n" +
                    "        \"Date\": \"04/05/2023\",\n" +
                    "        \"Branch\": \"Perfect Software Solution\",\n" +
                    "        \"DepartmentFrom\": \"Service\",\n" +
                    "        \"DepartmentTo\": \"Head Office Chalappuram\",\n" +
                    "        \"EmployeesFrom\": \"VYSHAKH PN\",\n" +
                    "        \"EmployeeTo\": \"Sona\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Date\": \"04/05/2023\",\n" +
                    "        \"Branch\": \"Perfect Software Solution\",\n" +
                    "        \"DepartmentFrom\": \"Sales\",\n" +
                    "        \"DepartmentTo\": \"Head Office Chalappuram\",\n" +
                    "        \"EmployeesFrom\": \"Sona\",\n" +
                    "        \"EmployeeTo\": \"Shan\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Date\": \"04/05/2023\",\n" +
                    "        \"Branch\": \"Perfect Software Solution\",\n" +
                    "        \"DepartmentFrom\": \"Support\",\n" +
                    "        \"DepartmentTo\": \"Head Office Chalappuram\",\n" +
                    "        \"EmployeesFrom\": \"Anvin\",\n" +
                    "        \"EmployeeTo\": \"Shan\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"Date\": \"04/05/2023\",\n" +
                    "        \"Branch\": \"Perfect Software Solution\",\n" +
                    "        \"DepartmentFrom\": \"Sales\",\n" +
                    "        \"DepartmentTo\": \"Head Office Chalappuram\",\n" +
                    "        \"EmployeesFrom\": \"Shi\",\n" +
                    "        \"EmployeeTo\": \"Shan\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"ResponseCode\": \"0\",\n" +
                    "    \"ResponseMessage\": \"Transaction Verified\"\n" +
                    "  },\n" +
                    "  \"StatusCode\": 0,\n" +
                    "  \"EXMessage\": \"Transaction Verified\"\n" +
                    "}"

            stockRTListSetterGetter.value = StockRTListModel(msg)
        }catch (e: Exception){

        }
    }
}