package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.EmployeeLocationUpdateModel

object EmployeeLocationUpdateRepository {

    private var progressDialog: ProgressDialog? = null
    val employeeLocationSetterGetter = MutableLiveData<EmployeeLocationUpdateModel>()
    val TAG: String = "EmployeeLocationUpdateRepository"

    fun getServicesApiCall(context: Context, LocLatitude : String, LocLongitude : String, LocationAddress : String, ChargePercentage  :String): MutableLiveData<EmployeeLocationUpdateModel> {
        updateLocation(context,LocLatitude,LocLongitude,LocationAddress,ChargePercentage)
        return employeeLocationSetterGetter
    }

    private fun updateLocation(context: Context, LocLatitude : String, LocLongitude : String, LocationAddress : String, ChargePercentage  :String) {

        Log.e(TAG,"2222"
                +"\n LocLatitude        :   "+ LocLatitude
                +"\n LocLongitude       :   "+LocLongitude
                +"\n LocationAddress    :   "+LocationAddress
                +"\n ChargePercentage   :   "+ChargePercentage
        )
    }
}