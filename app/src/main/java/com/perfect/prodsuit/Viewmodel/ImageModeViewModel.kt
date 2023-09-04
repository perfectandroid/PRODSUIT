package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ImageModeModel
import com.perfect.prodsuit.Repository.ImageModeRepository

class ImageModeViewModel: ViewModel()  {

    var imagemodeData: MutableLiveData<ImageModeModel>? = null

    fun getImageMode(context: Context) : LiveData<ImageModeModel>? {
        imagemodeData = ImageModeRepository.getServicesApiCall(context)
        return imagemodeData
    }

}
