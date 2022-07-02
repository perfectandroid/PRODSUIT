package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ServiceProductModel


object ServiceProductRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceProductSetterGetter = MutableLiveData<ServiceProductModel>()
    val TAG: String = "ServiceProductRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ServiceProductModel> {
        getServiceProductSetterGetter(context)
        return serviceProductSetterGetter
    }

    private fun getServiceProductSetterGetter(context: Context) {

        val msg  ="{\"ProductHistoryDetails\": {\"ProductHistoryDetailsList\": [{\"TicketNo\":\"TKT101\",\"RegOn\": \"06/06/2022\",\"Complaint\": \"Battery Check\",\"Status\": \"Pending\",\"AttendedBy\": \"AIswarya\",\"Employee\": \"Need to replace the product. Board is damaged \"},{\"TicketNo\":\"TKT102\",\"RegOn\": \"07/06/2022\",\"Complaint\": \"Maintenance Services\",\"Status\": \"Completed\",\"AttendedBy\": \"\tSurya\",\"Employee\": \"Need to replace the product. \"},{\"TicketNo\":\"TKT103\",\"RegOn\": \"08/06/2022\",\"Complaint\": \"Battery Check\",\"Status\": \"Ongoing\",\"AttendedBy\": \"Jerald\",\"Employee\": \"Need to replace the product\"},{\"TicketNo\":\"TKT104\",\"RegOn\": \"09/06/2022\",\"Complaint\": \"Maintenance Services\",\"Status\": \"Pending\",\"AttendedBy\": \"\tSurya\",\"Employee\": \"\"}],\"ResponseCode\": \"0\",\"ResponseMessage\": \"Transaction Verified\"},\"StatusCode\": 0,\"EXMessage\": \"Transaction Verified\"}"
        serviceProductSetterGetter.value = ServiceProductModel(msg)
    }

}