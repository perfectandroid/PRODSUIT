package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SubProductModel
import com.perfect.prodsuit.Repository.SubProductRepository
import org.json.JSONArray

class SubProductViewModel  : ViewModel(){

    var subProductLiveData: MutableLiveData<SubProductModel>? = null
    fun getSubProduct(context: Context, FkEmployee : String, productDetails : JSONArray) : LiveData<SubProductModel>? {
        subProductLiveData = SubProductRepository.getServicesApiCall(context,FkEmployee,productDetails)
        return subProductLiveData
    }
}