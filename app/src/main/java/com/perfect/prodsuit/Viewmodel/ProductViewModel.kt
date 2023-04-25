package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProductListModel
import com.perfect.prodsuit.Repository.ProductListRepository

class ProductViewModel : ViewModel(){

    var productlistdata: MutableLiveData<ProductListModel>? = null

    fun getProductListing(context: Context, ID_Employee: String) : LiveData<ProductListModel>? {
        productlistdata = ProductListRepository.getServicesApiCall(context,ID_Employee)
        return productlistdata
    }
}