package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BranchTypeModel
import com.perfect.prodsuit.Reprository.BranchTypeRepository

class BranchTypeViewModel: ViewModel() {

    var branchtypeData: MutableLiveData<BranchTypeModel>? = null
    fun getBranchType(context: Context) : LiveData<BranchTypeModel>? {
        branchtypeData = BranchTypeRepository.getServicesApiCall(context)
        return branchtypeData
    }
}