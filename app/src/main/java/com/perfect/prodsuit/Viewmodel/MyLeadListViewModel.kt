package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.BannerModel
import com.perfect.prodsuit.Model.MyLeadListModel
import com.perfect.prodsuit.Model.UpcomingtasksModel
import com.perfect.prodsuit.Repository.MyLeadRepository
import com.perfect.prodsuit.Repository.UpcomingtasksRepository

class MyLeadListViewModel : ViewModel() {

    var myleadlistLiveData: MutableLiveData<MyLeadListModel>? = null

    fun getMyLeadlist(context: Context,submode : String, name : String, criteria : String, date : String,ID_Branch : String , ID_Employee : String, ID_Lead_Details : String,strLeadValue :String) : LiveData<MyLeadListModel>? {
        myleadlistLiveData = MyLeadRepository.getServicesApiCall(context,submode,name,criteria,date,ID_Branch,ID_Employee,ID_Lead_Details,strLeadValue)
        return myleadlistLiveData
    }

}