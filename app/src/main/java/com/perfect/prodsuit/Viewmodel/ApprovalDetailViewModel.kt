package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ApprovalDetailModel
import com.perfect.prodsuit.Repository.ApprovalDetailRepository

class ApprovalDetailViewModel : ViewModel() {

    var approvalDetailData: MutableLiveData<ApprovalDetailModel>? = null

    fun getApprovalDetail(context: Context) : LiveData<ApprovalDetailModel>? {
        approvalDetailData = ApprovalDetailRepository.getServicesApiCall(context)
        return approvalDetailData
    }
}