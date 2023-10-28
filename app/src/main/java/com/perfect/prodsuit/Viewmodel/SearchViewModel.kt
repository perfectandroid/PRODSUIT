package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SearchModel
import com.perfect.prodsuit.Repository.SearchRepository

class SearchViewModel : ViewModel() {

    var searchData: MutableLiveData<SearchModel>? = null
    fun getSearch(context: Context, id_search: String) : MutableLiveData<SearchModel>? {
        searchData = SearchRepository.getServicesApiCall(context,id_search)
        return searchData
    }
}