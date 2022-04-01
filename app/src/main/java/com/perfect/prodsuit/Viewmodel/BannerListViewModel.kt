package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.ChangeMpinModel
import com.perfect.prodsuit.Reprository.BannersRepository
import com.perfect.prodsuit.Reprository.ChangeMpinRepository

class BannerListViewModel : ViewModel() {

    var bannerlistLiveData: MutableLiveData<BannerModel>? = null

    fun getBannerlist(context: Context) : LiveData<BannerModel>? {
        bannerlistLiveData = BannersRepository.getServicesApiCall(context)
        return bannerlistLiveData
    }

}