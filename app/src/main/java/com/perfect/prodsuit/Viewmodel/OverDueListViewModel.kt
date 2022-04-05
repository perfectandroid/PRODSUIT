package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.ChangeMpinModel
import com.perfect.prodsuit.Model.OverDueModel
import com.perfect.prodsuit.Reprository.BannersRepository
import com.perfect.prodsuit.Reprository.ChangeMpinRepository
import com.perfect.prodsuit.Reprository.OverDueRepository

class OverDueListViewModel : ViewModel() {

    var overduelistLiveData: MutableLiveData<OverDueModel>? = null

    fun getOverduelist(context: Context) : LiveData<OverDueModel>? {
        overduelistLiveData = OverDueRepository.getServicesApiCall(context)
        return overduelistLiveData
    }

}