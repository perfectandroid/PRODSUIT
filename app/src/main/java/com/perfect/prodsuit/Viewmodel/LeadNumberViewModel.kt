package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BranchModel
import com.perfect.prodsuit.Model.LeadNumModel
import com.perfect.prodsuit.Repository.BranchRepository
import com.perfect.prodsuit.Repository.LeadNumRepository

class LeadNumberViewModel: ViewModel() {

    var leadnoData: MutableLiveData<LeadNumModel>? = null

    fun getLeadno(context: Context ,ID_BranchType : String ) : LiveData<LeadNumModel>? {
        leadnoData = LeadNumRepository.getServicesApiCall(context,ID_BranchType)
        return leadnoData
    }

}