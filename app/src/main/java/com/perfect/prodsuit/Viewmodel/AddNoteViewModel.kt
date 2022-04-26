package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AddNoteModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Repository.AddNoteRepository
import com.perfect.prodsuit.Repository.BannersRepository

class AddNoteViewModel : ViewModel() {

    var addnoteLiveData: MutableLiveData<AddNoteModel>? = null

    fun getAddnotelist(context: Context) : LiveData<AddNoteModel>? {
        addnoteLiveData = AddNoteRepository.getServicesApiCall(context)
        return addnoteLiveData
    }

}