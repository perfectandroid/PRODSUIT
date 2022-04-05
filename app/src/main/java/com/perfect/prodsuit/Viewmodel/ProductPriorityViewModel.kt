package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.perfect.prodsuit.Model.ProductPriorityModel

import com.perfect.prodsuit.Repository.ProductPriorityRepository

class ProductPriorityViewModel : ViewModel()  {

    var productpriorityData: MutableLiveData<ProductPriorityModel>? = null

    fun getProductPriority(context: Context) : LiveData<ProductPriorityModel>? {
        productpriorityData = ProductPriorityRepository.getServicesApiCall(context)
        return productpriorityData
    }
}