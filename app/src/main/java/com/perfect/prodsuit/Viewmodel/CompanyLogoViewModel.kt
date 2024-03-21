package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CompanyLogomodel
import com.perfect.prodsuit.Repository.CompanyLogoRepository

class CompanyLogoViewModel : ViewModel() {

    var companyLogoData: MutableLiveData<CompanyLogomodel>? = null
    fun getCompanylogoType(context: Context) : MutableLiveData<CompanyLogomodel>? {
        companyLogoData = CompanyLogoRepository.getServicesApiCall(context)
        return companyLogoData
    }
}