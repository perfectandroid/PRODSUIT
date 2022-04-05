package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MediaTypeModel
import com.perfect.prodsuit.Repository.MediaTypeRepository

class MediaTypeViewModel: ViewModel() {

    var mediaTypeData: MutableLiveData<MediaTypeModel>? = null
    fun getMediaType(context: Context) : MutableLiveData<MediaTypeModel>? {
        mediaTypeData = MediaTypeRepository.getServicesApiCall(context)
        return mediaTypeData
    }
}