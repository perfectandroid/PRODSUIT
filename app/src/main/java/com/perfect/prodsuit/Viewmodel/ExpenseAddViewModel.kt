package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AddExpenseModel
import com.perfect.prodsuit.Repository.AddExpenseActivityRepository

class ExpenseAddViewModel : ViewModel() {

    var expenseaddLiveData: MutableLiveData<AddExpenseModel>? = null

    fun addExpenselist(context: Context) : LiveData<AddExpenseModel>? {
        expenseaddLiveData = AddExpenseActivityRepository.getServicesApiCall(context)
        return expenseaddLiveData
    }

}