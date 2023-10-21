//package com.perfect.prodsuit.Viewmodel
//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.perfect.prodsuit.Model.ServiceDetailsModel
//import com.perfect.prodsuit.Repository.ServiceDetailsRepository
//
//class ServiceInfoDetailsViewModel : ViewModel() {
//
//    var serviceinfodetailsData: MutableLiveData<ServiceDetailsModel>? = null
//
//    fun getServiceInfoDetails(context: Context) : LiveData<ServiceDetailsModel>? {
//        serviceinfodetailsData = ServiceDetailsRepository.getServicesApiCall(context)
//        return serviceinfodetailsData
//    }
//}