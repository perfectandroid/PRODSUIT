package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductCategoryModel
import com.perfect.prodsuit.Repository.ProductCategoryRepository

class ProductCategoryViewModel: ViewModel() {

    var productcategoryData: MutableLiveData<ProductCategoryModel>? = null

    fun getProductCategory(context: Context) : LiveData<ProductCategoryModel>? {
        productcategoryData = ProductCategoryRepository.getServicesApiCall(context)
        return productcategoryData
    }

}