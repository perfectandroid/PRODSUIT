package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AddNoteModel
import com.perfect.prodsuit.Repository.AddNoteRepository


class AddNoteViewModel : ViewModel() {

    var addnoteLiveData: MutableLiveData<AddNoteModel>? = null

    fun getAddnotelist(context: Context) : LiveData<AddNoteModel>? {
        addnoteLiveData = AddNoteRepository.getServicesApiCall(context)
        return addnoteLiveData
    }

}