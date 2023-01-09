package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CompanyCodeModel
import com.perfect.prodsuit.Repository.CompanyCodeRepository

class CompanyCodeViewModel  : ViewModel()  {

    var companyCodeLiveData: MutableLiveData<CompanyCodeModel>? = null

    fun getCompanyCode(context: Context,companyCode : String) : LiveData<CompanyCodeModel>? {
        companyCodeLiveData = CompanyCodeRepository.getServicesApiCall(context,companyCode)
        return companyCodeLiveData
    }
}