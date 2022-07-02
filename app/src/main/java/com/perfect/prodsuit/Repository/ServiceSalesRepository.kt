package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ServiceSalesModel

object ServiceSalesRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceSalesSetterGetter = MutableLiveData<ServiceSalesModel>()
    val TAG: String = "ServiceSalesRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ServiceSalesModel> {
        getServiceSalesSetterGetter(context)
        return serviceSalesSetterGetter
    }

    private fun getServiceSalesSetterGetter(context: Context) {

        val msg = "{\"SalesHistoryDetails\": {\"SalesHistoryDetailsList\": [{\"InvoiceNo\":\"123\",\"InvoiceDate\": \"06/06/2022\",\"Dealer\": \"Head Office Chalappuram\",\"Product\": \"Inverter - 1050 VA\",\"Quatity\": \"10\"},{\"InvoiceNo\":\"124\",\"InvoiceDate\": \"07/06/2022\",\"Dealer\": \"Head Office Chalappuram\",\"Product\": \"Solar Panel\",\"Quatity\": \"10\"},{\"InvoiceNo\":\"125\",\"InvoiceDate\": \"08/06/2022\",\"Dealer\": \"Head Office Chalappuram\",\"Product\": \"Inverter - 1050 VA\",\"Quatity\": \"10\"},{\"InvoiceNo\":\"126\",\"InvoiceDate\": \"09/06/2022\",\"Dealer\": \"Head Office Chalappuram\",\"Product\": \"500VA+1200AH\",\"Quatity\": \"10\"}],\"ResponseCode\": \"0\",\"ResponseMessage\": \"Transaction Verified\"},\"StatusCode\": 0,\"EXMessage\": \"Transaction Verified\"}"
        serviceSalesSetterGetter.value = ServiceSalesModel(msg)
    }
}