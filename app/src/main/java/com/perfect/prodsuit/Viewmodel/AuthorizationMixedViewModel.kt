package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AuthorizationMixedModel
import com.perfect.prodsuit.Repository.AuthorizationMixedRepository


class AuthorizationMixedViewModel  : ViewModel() {

    var authorizationMixedData: MutableLiveData<AuthorizationMixedModel>? = null

    fun getAuthorizationMixed(context: Context) : LiveData<AuthorizationMixedModel>? {
        authorizationMixedData = AuthorizationMixedRepository.getServicesApiCall(context)
        return authorizationMixedData
    }
}