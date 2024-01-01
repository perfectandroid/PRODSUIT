package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PettyCashierModel
import com.perfect.prodsuit.Repository.PettyCashierRepository

class PettyCashierViewModel : ViewModel() {

    var pettyCashierData: MutableLiveData<PettyCashierModel>? = null

    fun getPettyCashierData(context: Context,ID_Project : String,ID_Employee : String) : LiveData<PettyCashierModel>? {
        pettyCashierData = PettyCashierRepository.getServicesApiCall(context,ID_Project,ID_Employee)
        return pettyCashierData
    }
}