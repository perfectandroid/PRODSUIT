package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Model.AddNoteModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.ProfileModel
import com.perfect.prodsuit.Repository.ActivityListRepository
import com.perfect.prodsuit.Repository.AddNoteRepository
import com.perfect.prodsuit.Repository.BannersRepository
import com.perfect.prodsuit.Repository.ProfileRepository

class ProfileViewModel : ViewModel() {

    var profileLiveData: MutableLiveData<ProfileModel>? = null

    fun getProfiledata(context: Context) : LiveData<ProfileModel>? {
        profileLiveData = ProfileRepository.getServicesApiCall(context)
        return profileLiveData
    }

}