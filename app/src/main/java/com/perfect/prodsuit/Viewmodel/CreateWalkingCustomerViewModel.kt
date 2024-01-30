package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CreateWalkingCustomerModel
import com.perfect.prodsuit.Repository.CreateWalkingCustomerRepository
import org.json.JSONArray

class CreateWalkingCustomerViewModel : ViewModel()  {

    var createWalkingCustomerData: MutableLiveData<CreateWalkingCustomerModel>? = null

    fun CreateWalkingCustomer(context: Context,strCustomer : String,strPhone : String,ID_AssignedTo : String,strAssignedDate : String,strVoiceData : String,VoiceLabel:String,strDescription : String,leadByMobileNo : JSONArray,ID_Category : String,ID_Product : String,strProjectName : String) : MutableLiveData<CreateWalkingCustomerModel>? {
        createWalkingCustomerData = CreateWalkingCustomerRepository.getServicesApiCall(context,strCustomer,strPhone,ID_AssignedTo,strAssignedDate,strVoiceData,VoiceLabel,strDescription,leadByMobileNo,ID_Category,ID_Product,strProjectName)
        return createWalkingCustomerData
    }
}