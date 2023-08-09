package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ApprovalModel
import com.perfect.prodsuit.Repository.ApprovalRepository

class ApprovalViewModel : ViewModel() {

    var approvalData: MutableLiveData<ApprovalModel>? = null

    fun getApproval(context: Context) : LiveData<ApprovalModel>? {
        approvalData = ApprovalRepository.getServicesApiCall(context)
        return approvalData
    }
}