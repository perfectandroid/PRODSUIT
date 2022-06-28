package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ServiceWarrantyModel


object ServiceWarrantyRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceWarrantySetterGetter = MutableLiveData<ServiceWarrantyModel>()
    val TAG: String = "ServiceWarrantyRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ServiceWarrantyModel> {
        getServiceWarrantySetterGetter(context)
        return serviceWarrantySetterGetter
    }

    private fun getServiceWarrantySetterGetter(context: Context) {

        val msg  = "{\"WarrantyDetails\": {\"WarrantyDetailsList\": [{\"ID_Warranty\": \"12\",\"InvoiceNo\": \"123\",\"InvoiceDate\": \"06/06/2022\",\"Dealer\": \"\tHead Office Chalappuram 1\"},{\"ID_Warranty\": \"Product\",\"InvoiceNo\": \"124\",\"InvoiceDate\": \"07/06/2022\",\"Dealer\": \"\tHead Office Chalappuram 2\"},{\"ID_Warranty\": \"Project\",\"InvoiceNo\": \"125\",\"InvoiceDate\": \"08/06/2022\",\"Dealer\": \"\tHead Office Chalappuram 3\"},{\"ID_Warranty\": \"Product\",\"InvoiceNo\": \"126\",\"InvoiceDate\": \"09/06/2022\",\"Dealer\": \"\tHead Office Chalappuram 4\"}],\"ResponseCode\": \"0\",\"ResponseMessage\": \"Transaction Verified\"},\"StatusCode\": 0,\"EXMessage\": \"Transaction Verified\"}"

        serviceWarrantySetterGetter.value = ServiceWarrantyModel(msg)
    }
}