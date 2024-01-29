package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductReorderLevelModel
import com.perfect.prodsuit.Repository.ProductreorderLevelRepository

class ProductReorderLevelViewModel : ViewModel(){

    var productreorderLevelViewModelData: MutableLiveData<ProductReorderLevelModel>? = null

    fun getproductreorderLevel(context: Context) : LiveData<ProductReorderLevelModel>? {
        productreorderLevelViewModelData = ProductreorderLevelRepository.getServicesApiCall(context)
        return productreorderLevelViewModelData


    }
}