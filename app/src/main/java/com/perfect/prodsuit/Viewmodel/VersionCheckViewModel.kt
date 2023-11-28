package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.LeadEditListModel
import com.perfect.prodsuit.Model.VersionModel
import com.perfect.prodsuit.Repository.LeadEditListRepository
import com.perfect.prodsuit.Repository.VersionRepository

class VersionCheckViewModel : ViewModel() {

    var versionData: MutableLiveData<VersionModel>? = null

    fun getVersion(context: Context) : LiveData<VersionModel>? {
        versionData = VersionRepository.getServicesApiCall(context)
        return versionData
    }
}