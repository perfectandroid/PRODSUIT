package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BrandModel
import com.perfect.prodsuit.Repository.BrandRepository

class BrandViewModel : ViewModel() {

    var brandData: MutableLiveData<BrandModel>? = null

    fun getBrands(context: Context, ReqMode : String) : LiveData<BrandModel>? {
        brandData = BrandRepository.getServicesApiCall(context,ReqMode)
        return brandData
    }
}