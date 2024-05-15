package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MyActivityCountModel
import com.perfect.prodsuit.Repository.MyActivityCountRepository

class MyActivityCountViewModel  : ViewModel(){

    var myActivityCountData: MutableLiveData<MyActivityCountModel>? = null
    fun getMyActivityCount(context: Context,ReqMode : String, IdFliter : String) : LiveData<MyActivityCountModel>? {
        myActivityCountData = MyActivityCountRepository.getServicesApiCall(context,ReqMode,IdFliter)
        return myActivityCountData
    }
}