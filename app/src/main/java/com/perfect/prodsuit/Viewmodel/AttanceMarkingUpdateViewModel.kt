package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AttanceMarkingUpdateModel
import com.perfect.prodsuit.Repository.AttanceMarkingUpdateRepository

class AttanceMarkingUpdateViewModel : ViewModel() {
    var attanceMarkingUpdateData: MutableLiveData<AttanceMarkingUpdateModel>? = null
    fun setAttanceMarkingUpdate(context: Context, strLatitude : String,strLongitue : String,strLocationAddress : String,strDate : String,strTime : String,strStatus : String) : MutableLiveData<AttanceMarkingUpdateModel>? {
        attanceMarkingUpdateData = AttanceMarkingUpdateRepository.getServicesApiCall(context,strLatitude,strLongitue,strLocationAddress,strDate,strTime,strStatus)
        return attanceMarkingUpdateData
    }
}