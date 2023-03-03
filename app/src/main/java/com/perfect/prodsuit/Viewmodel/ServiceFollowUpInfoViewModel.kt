package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.Model.ServiceFollowUpInfoModel
import com.perfect.prodsuit.Model.ServiceFollowUpModel
import com.perfect.prodsuit.Repository.OverDueRepository
import com.perfect.prodsuit.Repository.ServiceFollowUpInfoRepository
import com.perfect.prodsuit.Repository.ServiceFollowUpRepository

class ServiceFollowUpInfoViewModel : ViewModel() {

    var serviceFollowUpInfoLiveData: MutableLiveData<ServiceFollowUpInfoModel>? = null

    fun getServiceFollowUpInfo(
        context: Context,
        customerServiceRegister: String,
        ID_Branch: String,
        ID_Employee: String
    ): LiveData<ServiceFollowUpInfoModel>? {
        Log.v("fsfsfds", "branch4 " + ID_Branch)
        serviceFollowUpInfoLiveData =
            ServiceFollowUpInfoRepository.getServicesApiCall(context,customerServiceRegister, ID_Branch, ID_Employee)
        return serviceFollowUpInfoLiveData
    }

}