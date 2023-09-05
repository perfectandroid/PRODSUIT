package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ComplaintTypeModel
import com.perfect.prodsuit.Repository.ComplaintTypeRepository

class ComplaintTypeViewModel : ViewModel()  {

    var complaintTypeData: MutableLiveData<ComplaintTypeModel>? = null

    fun getComplaintType(context: Context, FK_Category: String) : LiveData<ComplaintTypeModel>? {
        complaintTypeData = ComplaintTypeRepository.getServicesApiCall(context,FK_Category)
        return complaintTypeData
    }

}