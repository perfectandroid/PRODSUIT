package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AreaListModel
import com.perfect.prodsuit.Repository.AreaListRepository


class AreaListViewModel : ViewModel(){

    var arealistdata: MutableLiveData<AreaListModel>? = null

    fun getAreaListing(context: Context, ID_Employee: String) : LiveData<AreaListModel>? {
        arealistdata = AreaListRepository.getServicesApiCall(context,ID_Employee)
        return arealistdata
    }

}