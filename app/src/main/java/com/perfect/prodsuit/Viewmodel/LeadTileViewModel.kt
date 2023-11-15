//package com.perfect.prodsuit.Viewmodel
//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.perfect.prodsuit.Model.LeadTileModel
//import com.perfect.prodsuit.Model.ServiceCountModel
//import com.perfect.prodsuit.Repository.LeadTileRepository
//import com.perfect.prodsuit.Repository.ServiceCountRepository
//
//class LeadTileViewModel : ViewModel() {
//
//    var leadTileCountData: MutableLiveData<LeadTileModel>? = null
//
//    fun getLeadTileCount(context: Context) : LiveData<LeadTileModel>? {
//        leadTileCountData = LeadTileRepository.getServicesApiCall(context)
//        return leadTileCountData
//    }
//}