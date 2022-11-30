package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.ImageModel
import com.perfect.prodsuit.Repository.BannersRepository
import com.perfect.prodsuit.Repository.ImageRepository
import com.perfect.prodsuit.View.Activity.AccountDetailsActivity

class ImageViewModel : ViewModel() {

    var imagelistLiveData: MutableLiveData<ImageModel>? = null

    fun getImage(context: Context,ID_LeadGenerateProduct :  String) : LiveData<ImageModel>? {
        imagelistLiveData = ImageRepository.getServicesApiCall(context, ID_LeadGenerateProduct)
        return imagelistLiveData
    }

}