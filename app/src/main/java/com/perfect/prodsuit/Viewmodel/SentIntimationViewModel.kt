package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SentIntimationModel
import com.perfect.prodsuit.Repository.SentIntimationRepository

class SentIntimationViewModel: ViewModel() {

    var sentData: MutableLiveData<SentIntimationModel>? = null

    fun sentIntimation(
        context: Context,
        dated: String,
        ID_module: String,
        ID_Branch: String,
        ID_Channel: String,
        ID_Shedule: String,
        encodeDoc: String,
        extension: String,
        message: String
    ) : LiveData<SentIntimationModel>? {
        sentData = SentIntimationRepository.getServicesApiCall(context,dated,ID_module,ID_Branch,ID_Channel,ID_Shedule,encodeDoc,extension,message)
        return sentData
    }

}