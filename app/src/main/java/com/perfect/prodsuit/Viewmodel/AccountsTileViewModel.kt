package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AccountsTileModel
import com.perfect.prodsuit.Repository.AccountsPLTileDashBoardRepository


class AccountsTileViewModel : ViewModel() {

    var AccountsLiveData: MutableLiveData<AccountsTileModel>? = null

    fun getAccountsTile(context: Context) : LiveData<AccountsTileModel>? {
        AccountsLiveData = AccountsPLTileDashBoardRepository.getServicesApiCall(context)
        return AccountsLiveData
    }
}