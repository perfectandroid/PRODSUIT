package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.InspectionTypeModel
import com.perfect.prodsuit.Repository.InspectionTypeRepository

class InspectionTypeViewModel : ViewModel()  {

    var inspectionTypeData: MutableLiveData<InspectionTypeModel>? = null

    fun getInspectionType(context: Context, ReqMode : String) : LiveData<InspectionTypeModel>? {
        inspectionTypeData = InspectionTypeRepository.getServicesApiCall(context,ReqMode)
        return inspectionTypeData
    }

}