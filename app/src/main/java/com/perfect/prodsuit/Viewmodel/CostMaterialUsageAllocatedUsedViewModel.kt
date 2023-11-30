package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CorrectionSplitupModel
import com.perfect.prodsuit.Model.CostMaterialUsageAllocatedUsedModel
import com.perfect.prodsuit.Repository.CostMaterialUsageAllocatedUsedRespository

class CostMaterialUsageAllocatedUsedViewModel : ViewModel()  {

    var costmaterialusageAllocatedUsedData: MutableLiveData<CostMaterialUsageAllocatedUsedModel>? = null

    fun getCostMaterialUsageAllocatedUsed(context: Context, TransMode : String) : MutableLiveData<CostMaterialUsageAllocatedUsedModel>? {
        costmaterialusageAllocatedUsedData = CostMaterialUsageAllocatedUsedRespository.getServicesApiCall(context,TransMode)
        return costmaterialusageAllocatedUsedData
    }
}