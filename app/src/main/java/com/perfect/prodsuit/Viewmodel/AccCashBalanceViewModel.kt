package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AccCashBalanceModel
import com.perfect.prodsuit.Repository.AccCashBalanceRepository

class AccCashBalanceViewModel : ViewModel() {

    var accCashBalanceData: MutableLiveData<AccCashBalanceModel>? = null

    fun getAccCashBalance(context: Context, TransDate : String, DashMode : String, DashType : String) : LiveData<AccCashBalanceModel>? {
        accCashBalanceData = AccCashBalanceRepository.getServicesApiCall(context,TransDate,DashMode,DashType)
        return accCashBalanceData
    }
}