package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.ImageModel
import com.perfect.prodsuit.Repository.BannersRepository
import com.perfect.prodsuit.Repository.ImageRepository

class ImageViewModel : ViewModel() {

    var imagelistLiveData: MutableLiveData<ImageModel>? = null

    fun getImage(context: Context) : LiveData<ImageModel>? {
        imagelistLiveData = ImageRepository.getServicesApiCall(context)
        return imagelistLiveData
    }

}