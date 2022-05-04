package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ExpenseTypeModel
import com.perfect.prodsuit.Repository.ExpenseTypeRepository

class ExpenseTypeViewModel : ViewModel() {

    var expensetypeLiveData: MutableLiveData<ExpenseTypeModel>? = null

    fun getExpenseType(context: Context) : LiveData<ExpenseTypeModel>? {
        expensetypeLiveData = ExpenseTypeRepository.getServicesApiCall(context)
        return expensetypeLiveData
    }

}