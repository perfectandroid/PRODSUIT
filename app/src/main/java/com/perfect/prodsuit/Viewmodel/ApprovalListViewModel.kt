package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ApprovalListModel
import com.perfect.prodsuit.Repository.ApprovalListRepository

class ApprovalListViewModel  : ViewModel() {

    var approvalListData: MutableLiveData<ApprovalListModel>? = null

    fun getApprovalList(context: Context,Module : String) : LiveData<ApprovalListModel>? {
        approvalListData = ApprovalListRepository.getServicesApiCall(context,Module)
        return approvalListData
    }
}