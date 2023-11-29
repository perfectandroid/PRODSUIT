package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AccBankBalanceModel
import com.perfect.prodsuit.Repository.AccBankBalanceRepository

class AccBankBalanceViewModel  : ViewModel() {

    var accBankBalanceData: MutableLiveData<AccBankBalanceModel>? = null

    fun getAccBankBalance(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<AccBankBalanceModel>? {
        accBankBalanceData = AccBankBalanceRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return accBankBalanceData
    }
}