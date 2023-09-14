package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.WalkExistModel
import com.perfect.prodsuit.Repository.WalkExistRepository

class WalkExistViewModel  : ViewModel()  {

    var walkExistData: MutableLiveData<WalkExistModel>? = null

    fun getWalkExist(context: Context, strPhone : String) : MutableLiveData<WalkExistModel>? {
        walkExistData = WalkExistRepository.getServicesApiCall(context,strPhone)
        return walkExistData
    }
}