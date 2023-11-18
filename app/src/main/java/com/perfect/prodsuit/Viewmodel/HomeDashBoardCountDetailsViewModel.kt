package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.HomeDashBoardCountDetailsModel
import com.perfect.prodsuit.Repository.HomeDashBoardCountDetailsRepository

class HomeDashBoardCountDetailsViewModel : ViewModel() {

    var homedashboardCountDetailsViewModelCountData: MutableLiveData<HomeDashBoardCountDetailsModel>? = null

    fun getHomeDashBoardCountDetails(context: Context) : MutableLiveData<HomeDashBoardCountDetailsModel>? {
        homedashboardCountDetailsViewModelCountData = HomeDashBoardCountDetailsRepository.getServicesApiCall(context)
        return homedashboardCountDetailsViewModelCountData
    }
}