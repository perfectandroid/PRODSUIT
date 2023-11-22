//package com.perfect.prodsuit.Viewmodel
//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.perfect.prodsuit.Model.AverageLeadModel
//import com.perfect.prodsuit.Model.LeadTileModel
//import com.perfect.prodsuit.Model.ServiceCountModel
//import com.perfect.prodsuit.Repository.AverageLeadConversionRepository
//import com.perfect.prodsuit.Repository.LeadTileRepository
//import com.perfect.prodsuit.Repository.ServiceCountRepository
//
//class AverageLeadConversionViewModel : ViewModel() {
//
//    var avgleadConvrsnCountData: MutableLiveData<AverageLeadConversionViewModel>? = null
//
//    fun getAverageLeadConversionCount(context: Context) : LiveData<AverageLeadConversionViewModel>? {
//        avgleadConvrsnCountData = AverageLeadConversionRepository.getServicesApiCall(context)
//        return avgleadConvrsnCountData
//    }
//}