package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CustomerServiceRegisterModel
import com.perfect.prodsuit.Repository.CustomerServiceRegisterRepository
import com.perfect.prodsuit.Repository.FollowUpActionRepository

class CustomerServiceRegisterViewModel: ViewModel()  {

    var cusServRegisterData: MutableLiveData<CustomerServiceRegisterModel>? = null
    fun getcusServRegister(context: Context,strUserAction : String,Customer_Type : String,ID_Customer : String,ID_Channel : String,ID_Priority : String,
                           ID_Category : String, ID_Company : String,ID_ComplaintList : String,ID_Services : String,ID_EmpMedia : String,ID_Status : String,
                           ID_AttendedBy : String, strCustomerName : String,strMobileNo : String,strAddress : String,strContactNo : String, strLandMark : String,
                           strFromDate : String, strToDate : String,strFromTime : String,strToTime : String,ID_Product : String,strDescription : String,
                           strDate : String) : LiveData<CustomerServiceRegisterModel>? {
        cusServRegisterData = CustomerServiceRegisterRepository.getServicesApiCall(context,strUserAction,Customer_Type,ID_Customer,ID_Channel,ID_Priority,ID_Category,
            ID_Company,ID_ComplaintList,ID_Services,ID_EmpMedia,ID_Status,ID_AttendedBy,strCustomerName,strMobileNo,strAddress,strContactNo,
            strLandMark,strFromDate,strToDate,strFromTime,strToTime,ID_Product,strDescription,strDate)
        return cusServRegisterData
    }
}