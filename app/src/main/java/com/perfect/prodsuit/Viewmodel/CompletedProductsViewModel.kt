package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CompletedProductsModel
import com.perfect.prodsuit.Model.CostMaterialUsageAllocatedUsedModel
import com.perfect.prodsuit.Repository.CompletedProductsRespository
import com.perfect.prodsuit.Repository.CostMaterialUsageAllocatedUsedRespository

class CompletedProductsViewModel  : ViewModel()  {

    var completedProductsData: MutableLiveData<CompletedProductsModel>? = null

    fun getCompletedProducts(context: Context, TransMode : String) : MutableLiveData<CompletedProductsModel>? {
        completedProductsData = CompletedProductsRespository.getServicesApiCall(context,TransMode)
        return completedProductsData
    }
}