package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.HeaderTestModel
import com.perfect.prodsuit.Repository.HeaderTestRepository

class HeaderTestViewModel  : ViewModel() {

    var headerTestData: MutableLiveData<HeaderTestModel>? = null

    fun getHeaderTest(context: Context) : LiveData<HeaderTestModel>? {
        headerTestData = HeaderTestRepository.getServicesApiCall(context)
        return headerTestData
    }
}