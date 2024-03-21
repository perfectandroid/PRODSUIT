package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.VoiceModel
import com.perfect.prodsuit.Repository.VoiceRequestRepository

class VoiceViewModel : ViewModel()  {

    var voiceData: MutableLiveData<VoiceModel>? = null

    fun getVoice(context: Context,custmerAssignmentID : String) : LiveData<VoiceModel>? {
        voiceData = VoiceRequestRepository.getServicesApiCall(context,custmerAssignmentID)
        return voiceData
    }

}