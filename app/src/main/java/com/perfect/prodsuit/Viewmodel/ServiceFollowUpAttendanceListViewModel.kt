package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.Model.ServiceFollowUpAttendanceModel
import com.perfect.prodsuit.Model.ServiceFollowUpModel
import com.perfect.prodsuit.Repository.OverDueRepository
import com.perfect.prodsuit.Repository.ServiceFollowUpAttendanceRepository
import com.perfect.prodsuit.Repository.ServiceFollowUpRepository

class ServiceFollowUpAttendanceListViewModel : ViewModel() {

    var serviceFollowUpAttendancelistLiveData: MutableLiveData<ServiceFollowUpAttendanceModel>? = null

    fun getServiceFollowUpAttendance(context: Context,customer_service_register:String,ID_Branch : String , ID_Employee : String) : LiveData<ServiceFollowUpAttendanceModel>? {
        Log.v("fsfsfds","branch4 "+ID_Branch)
        serviceFollowUpAttendancelistLiveData = ServiceFollowUpAttendanceRepository.getServicesApiCall(context,customer_service_register,ID_Branch,ID_Employee)
        return serviceFollowUpAttendancelistLiveData
    }

}