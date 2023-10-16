package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SearchModuleModel
import com.perfect.prodsuit.Repository.SearchModuleRepository

class SearchModuleViewModel : ViewModel() {

    var searchModuleData: MutableLiveData<SearchModuleModel>? = null

    fun getSearchModule(context: Context) : LiveData<SearchModuleModel>? {
        searchModuleData = SearchModuleRepository.getServicesApiCall(context)
        return searchModuleData
    }
}