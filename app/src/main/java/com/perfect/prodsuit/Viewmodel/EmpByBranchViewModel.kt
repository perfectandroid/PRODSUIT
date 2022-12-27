package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmpByBranchModel
import com.perfect.prodsuit.Repository.EmpByBranchRepository

class EmpByBranchViewModel : ViewModel() {

    var EmpByBranchData: MutableLiveData<EmpByBranchModel>? = null

    fun getEmpByBranch(context: Context, ID_Branch : String ) : LiveData<EmpByBranchModel>? {
        EmpByBranchData = EmpByBranchRepository.getServicesApiCall(context,ID_Branch)
        return EmpByBranchData
    }
}