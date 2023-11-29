package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ExpenseAnalysisModel
import com.perfect.prodsuit.Repository.ExpenseAnalysisRespository

class ExpenseAnalysisViewModel : ViewModel() {

    var expenseanalysisLiveData: MutableLiveData<ExpenseAnalysisModel>? = null

    fun getExpenseAnalysis(context: Context) : LiveData<ExpenseAnalysisModel>? {
        expenseanalysisLiveData = ExpenseAnalysisRespository.getServicesApiCall(context)
        return expenseanalysisLiveData
    }

}