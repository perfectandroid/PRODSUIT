package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ActivityListModel
import com.perfect.prodsuit.Model.AddNoteModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.NoteListModel
import com.perfect.prodsuit.Repository.ActivityListRepository
import com.perfect.prodsuit.Repository.AddNoteRepository
import com.perfect.prodsuit.Repository.BannersRepository
import com.perfect.prodsuit.Repository.NoteListRepository

class NoteListViewModel : ViewModel() {

    var notelistLiveData: MutableLiveData<NoteListModel>? = null

    fun getActivitylist(context: Context) : LiveData<NoteListModel>? {
        notelistLiveData = NoteListRepository.getServicesApiCall(context)
        return notelistLiveData
    }

}