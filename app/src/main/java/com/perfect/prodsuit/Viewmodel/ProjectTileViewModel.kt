package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ProjectTileModel
import com.perfect.prodsuit.Repository.ProjectTileRepository





class ProjectTileViewModel : ViewModel() {

    var ProjectTileLiveData: MutableLiveData<ProjectTileModel>? = null

    fun getProjectTile(context: Context) : LiveData<ProjectTileModel>? {
        ProjectTileLiveData = ProjectTileRepository.getServicesApiCall(context)
        return ProjectTileLiveData
    }
}