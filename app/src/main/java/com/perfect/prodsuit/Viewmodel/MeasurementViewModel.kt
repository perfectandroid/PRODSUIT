package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MeasurementTypeModel
import com.perfect.prodsuit.Repository.MeasurementTypeRepository

class MeasurementViewModel : ViewModel()  {

    var measurementData: MutableLiveData<MeasurementTypeModel>? = null

    fun getMeasurement(context: Context) : LiveData<MeasurementTypeModel>? {
        measurementData = MeasurementTypeRepository.getServicesApiCall(context)
        return measurementData
    }

}