package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.WalkingUpdateModel
import com.perfect.prodsuit.Repository.WalkingUpdateRepository

class WalkingUpdateViewModel : ViewModel()  {

    var walkingUpdateData: MutableLiveData<WalkingUpdateModel>? = null

    fun putWalkingUpdate(context: Context) : MutableLiveData<WalkingUpdateModel>? {
        walkingUpdateData = WalkingUpdateRepository.getServicesApiCall(context)
        return walkingUpdateData
    }
}