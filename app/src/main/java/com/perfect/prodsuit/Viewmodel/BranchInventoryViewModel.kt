package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BranchInventoryModel
import com.perfect.prodsuit.Repository.BranchInventoryRepository

class BranchInventoryViewModel : ViewModel() {

    var branchInventoryData: MutableLiveData<BranchInventoryModel>? = null

    fun getBranchInventory(context: Context, ID_BranchType : String ) : LiveData<BranchInventoryModel>? {
        branchInventoryData = BranchInventoryRepository.getServicesApiCall(context,ID_BranchType)
        return branchInventoryData
    }
}