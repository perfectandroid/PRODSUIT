package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ExpenseModel
import com.perfect.prodsuit.Repository.ExpenseRepository

class ExpenseViewModel : ViewModel() {

    var expenseLiveData: MutableLiveData<ExpenseModel>? = null

    fun getExpenselist(context: Context) : LiveData<ExpenseModel>? {
        expenseLiveData = ExpenseRepository.getServicesApiCall(context)
        return expenseLiveData
    }

}