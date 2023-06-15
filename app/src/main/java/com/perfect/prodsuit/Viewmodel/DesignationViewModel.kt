package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DesignationModel
import com.perfect.prodsuit.Repository.DesignationRepository

class DesignationViewModel: ViewModel()  {
    var designationData: MutableLiveData<DesignationModel>? = null

    fun getDesignation(context: Context) : LiveData<DesignationModel>? {
        designationData = DesignationRepository.getServicesApiCall(context)
        return designationData
    }

}