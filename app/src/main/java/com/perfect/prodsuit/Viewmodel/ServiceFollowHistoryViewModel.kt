package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.Model.ServiceFollowUpAttendanceModel
import com.perfect.prodsuit.Model.ServiceFollowUpHistoryModel
import com.perfect.prodsuit.Model.ServiceFollowUpModel
import com.perfect.prodsuit.Repository.OverDueRepository
import com.perfect.prodsuit.Repository.ServiceFollowUpAttendanceRepository
import com.perfect.prodsuit.Repository.ServiceFollowUpHistoryRepository
import com.perfect.prodsuit.Repository.ServiceFollowUpRepository

class ServiceFollowHistoryViewModel : ViewModel() {

    var serviceFollowUpHistoryLiveData: MutableLiveData<ServiceFollowUpHistoryModel>? = null

    fun getServiceFollowUpHistory(context: Context,customer_service_register:String,ID_Branch : String , ID_Employee : String,FK_Customer:String,FK_CustomerOthers:String,FK_Product:String) : LiveData<ServiceFollowUpHistoryModel>? {
        Log.v("fsfsfds","branch4 "+ID_Branch)
        serviceFollowUpHistoryLiveData = ServiceFollowUpHistoryRepository.getServicesApiCall(context,customer_service_register,ID_Branch,ID_Employee,FK_Customer,FK_CustomerOthers,FK_Product)
        return serviceFollowUpHistoryLiveData
    }

}