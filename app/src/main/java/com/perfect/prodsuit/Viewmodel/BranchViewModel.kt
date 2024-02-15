package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BranchModel
import com.perfect.prodsuit.Repository.BranchRepository

class BranchViewModel: ViewModel() {

    var branchData: MutableLiveData<BranchModel>? = null

    fun getBranch(context: Context ,ID_BranchType : String  ,SubMode : String ) : LiveData<BranchModel>? {
        branchData = BranchRepository.getServicesApiCall(context,ID_BranchType,SubMode)
        return branchData
    }

}