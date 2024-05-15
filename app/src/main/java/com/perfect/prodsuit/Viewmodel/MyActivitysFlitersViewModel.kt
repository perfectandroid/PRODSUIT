package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MyActivitysFlitersModel
import com.perfect.prodsuit.Repository.MyActivitysFlitersRepository

class MyActivitysFlitersViewModel  : ViewModel(){

    var myActivitysFlitersData: MutableLiveData<MyActivitysFlitersModel>? = null
    fun getMyActivitysFliters(context: Context, ReqMode : String) : LiveData<MyActivitysFlitersModel>? {
        myActivitysFlitersData = MyActivitysFlitersRepository.getServicesApiCall(context,ReqMode)
        return myActivitysFlitersData
    }
}