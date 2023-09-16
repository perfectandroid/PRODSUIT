package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CreateWalkingCustomerModel
import com.perfect.prodsuit.Repository.CreateWalkingCustomerRepository
import org.json.JSONArray

class CreateWalkingCustomerViewModel : ViewModel()  {

    var createWalkingCustomerData: MutableLiveData<CreateWalkingCustomerModel>? = null

    fun CreateWalkingCustomer(context: Context,strCustomer : String,strPhone : String,ID_AssignedTo : String,strAssignedDate : String,strDescription : String,leadByMobileNo : JSONArray) : MutableLiveData<CreateWalkingCustomerModel>? {
        createWalkingCustomerData = CreateWalkingCustomerRepository.getServicesApiCall(context,strCustomer,strPhone,ID_AssignedTo,strAssignedDate,strDescription,leadByMobileNo)
        return createWalkingCustomerData
    }
}