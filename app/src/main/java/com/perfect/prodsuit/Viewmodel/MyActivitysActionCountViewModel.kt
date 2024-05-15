package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MyActivitysActionCountModel
import com.perfect.prodsuit.Repository.MyActivityActionCountRepository
class MyActivitysActionCountViewModel : ViewModel(){

    var myActivityActionCountData: MutableLiveData<MyActivitysActionCountModel>? = null
    fun getMyActivityActionCount(context: Context, ReqMode : String, SubMode : String, IdFliter : String) : LiveData<MyActivitysActionCountModel>? {
        myActivityActionCountData = MyActivityActionCountRepository.getServicesApiCall(context,ReqMode,SubMode,IdFliter)
        return myActivityActionCountData
    }
}