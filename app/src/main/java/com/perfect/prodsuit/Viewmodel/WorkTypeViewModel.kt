package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.WorkTypeModel
import com.perfect.prodsuit.Repository.WorkTypeRepository

class WorkTypeViewModel : ViewModel()  {

    var worktypeData: MutableLiveData<WorkTypeModel>? = null

    fun getWorkType(context: Context) : LiveData<WorkTypeModel>? {
        worktypeData = WorkTypeRepository.getServicesApiCall(context)
        return worktypeData
    }

}