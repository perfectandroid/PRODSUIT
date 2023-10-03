package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.TeamEmployeeModel
import com.perfect.prodsuit.Repository.TeamEmployeeRepository

class TeamEmployeeViewModel : ViewModel()  {
    var teamEmployeeData: MutableLiveData<TeamEmployeeModel>? = null

    fun getTeamEmployee(context: Context,ID_Team : String) : LiveData<TeamEmployeeModel>? {
        teamEmployeeData = TeamEmployeeRepository.getServicesApiCall(context,ID_Team)
        return teamEmployeeData
    }
}