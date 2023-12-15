package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.EmployeeLocationListModel
import com.perfect.prodsuit.Model.TravelListModel
import com.perfect.prodsuit.Repository.EmployeeLocationListRepository
import com.perfect.prodsuit.Repository.TravelListRepository

class LocationTravelListViewModel : ViewModel()  {
    var travelData: MutableLiveData<TravelListModel>? = null

    fun getTravelDetails(context: Context,strDate : String,ID_Department : String,ID_Designation : String,ID_Employee : String,ID_Branch : String) : LiveData<TravelListModel>? {
        travelData = TravelListRepository.getServicesApiCall(context,strDate,ID_Department,ID_Designation,ID_Employee,ID_Branch)
        return travelData
    }
}