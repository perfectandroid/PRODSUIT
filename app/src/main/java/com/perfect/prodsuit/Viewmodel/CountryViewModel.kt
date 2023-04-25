package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CountryModel
import com.perfect.prodsuit.Repository.CountryRepository

class CountryViewModel : ViewModel() {

    var countryLiveData: MutableLiveData<CountryModel>? = null

    fun getCountry(context: Context) : LiveData<CountryModel>? {
        countryLiveData = CountryRepository.getServicesApiCall(context)
        return countryLiveData
    }
}