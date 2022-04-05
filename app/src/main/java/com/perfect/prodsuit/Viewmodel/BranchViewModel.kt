package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BranchModel
import com.perfect.prodsuit.Reprository.BranchRepository

class BranchViewModel: ViewModel() {

    var branchData: MutableLiveData<BranchModel>? = null

    fun getBranch(context: Context) : LiveData<BranchModel>? {
        branchData = BranchRepository.getServicesApiCall(context)
        return branchData
    }
}