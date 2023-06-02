package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AssignedToWalkingModel
import com.perfect.prodsuit.Repository.AssignedToWalkingRepository

class AssignedToWalkingViewModel : ViewModel()  {

    var assignedToWalkingData: MutableLiveData<AssignedToWalkingModel>? = null

    fun getAssignedToWalking(context: Context, ReqMode : String) : MutableLiveData<AssignedToWalkingModel>? {
        assignedToWalkingData = AssignedToWalkingRepository.getServicesApiCall(context,ReqMode)
        return assignedToWalkingData
    }
}