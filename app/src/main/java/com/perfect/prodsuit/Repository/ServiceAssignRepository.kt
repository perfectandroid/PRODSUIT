package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ServiceAssignModel

object ServiceAssignRepository {

    private var progressDialog: ProgressDialog? = null
    val serviceAssignSetterGetter = MutableLiveData<ServiceAssignModel>()
    val TAG: String = "ServiceAssignRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ServiceAssignModel> {
        getServiceAssign(context)
        return serviceAssignSetterGetter
    }

    private fun getServiceAssign(context: Context) {

        val msg ="{\"ServiceAssignDetails\": {\"ServiceAssignDetailsList\": [{\"TicketNo\":\"TKT101\",\"TicketDate\": \"06/06/2022\",\"Branch\": \"Head Office Chalappuram\",\"Customer\": \"Yousaf\",\"Mobile\": \"9876543210\",\"Area\": \"Chalappuram\",\"Priority\": \"LOW\",\"Status\": \"Pending\",\"TimeDue\": \"1 Week Ago\"},{\"TicketNo\":\"TKT101\",\"TicketDate\": \"07/06/2022\",\"Branch\": \"Head Office Chalappuram\",\"Customer\": \"Yousaf\",\"Mobile\": \"9876543211\",\"Area\": \"Chalappuram\",\"Priority\": \"HIGH\",\"Status\": \"Completed\",\"TimeDue\": \"10 Days Ago\"},{\"TicketNo\":\"TKT101\",\"TicketDate\": \"08/06/2022\",\"Branch\": \"Head Office Chalappuram\",\"Customer\": \"Yousaf\",\"Mobile\": \"9876543212\",\"Area\": \"Chalappuram\",\"Priority\": \"MEDIUM\",\"Status\": \"Pending\",\"TimeDue\": \"1 Month Ago\"},{\"TicketNo\":\"TKT101\",\"TicketDate\": \"09/06/2022\",\"Branch\": \"Head Office Chalappuram\",\"Customer\": \"Yousaf\",\"Mobile\": \"9876543213\",\"Area\": \"Chalappuram\",\"Priority\": \"LOW\",\"Status\": \"Completed\",\"TimeDue\": \"5 Days Ago\"}],\"ResponseCode\": \"0\",\"ResponseMessage\": \"Transaction Verified\"},\"StatusCode\": 0,\"EXMessage\": \"Transaction Verified\"}"
        serviceAssignSetterGetter.value = ServiceAssignModel(msg)
    }

}