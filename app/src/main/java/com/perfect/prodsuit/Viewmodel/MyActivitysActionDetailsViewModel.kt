package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MyActivitysActionDetailsModel
import com.perfect.prodsuit.Repository.MyActivitysActionDetailsRepository

class MyActivitysActionDetailsViewModel : ViewModel(){

    var myActivitysActionDetailsData: MutableLiveData<MyActivitysActionDetailsModel>? = null
    fun getMyActivitysActionDetails(context: Context, ReqMode : String, SubMode : String, ID_ActionType : String, IdFliter : String) : LiveData<MyActivitysActionDetailsModel>? {
        myActivitysActionDetailsData = MyActivitysActionDetailsRepository.getServicesApiCall(context,ReqMode,SubMode,ID_ActionType,IdFliter)
        return myActivitysActionDetailsData
    }
}