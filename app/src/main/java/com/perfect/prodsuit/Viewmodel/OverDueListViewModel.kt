package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.Repository.OverDueRepository

class OverDueListViewModel : ViewModel() {

    var overduelistLiveData: MutableLiveData<OverDueModel>? = null

    fun getOverduelist(context: Context,submode : String, name: String, criteria: String,date: String,ID_Branch : String , ID_Employee : String, ID_Lead_Details : String,strLeadValue :String) : LiveData<OverDueModel>? {
        overduelistLiveData = OverDueRepository.getServicesApiCall(context,submode, name, criteria,date,ID_Branch,ID_Employee,ID_Lead_Details,strLeadValue)
        return overduelistLiveData
    }

}