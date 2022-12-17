package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ImageModel
import com.perfect.prodsuit.Repository.ImageRepository

class ImageViewModel : ViewModel() {

    var imagelistLiveData: MutableLiveData<ImageModel>? = null

    fun getImage(context: Context,ID_LeadGenerateProduct :  String,ID_LeadGenerate :  String) : LiveData<ImageModel>? {
        imagelistLiveData = ImageRepository.getServicesApiCall(context, ID_LeadGenerateProduct,ID_LeadGenerate)
        return imagelistLiveData
    }

}