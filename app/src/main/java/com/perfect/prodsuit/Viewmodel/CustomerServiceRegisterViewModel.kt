package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.CustomerServiceRegisterModel
import com.perfect.prodsuit.Repository.CustomerServiceRegisterRepository

class CustomerServiceRegisterViewModel: ViewModel()  {

    var cusServRegisterData: MutableLiveData<CustomerServiceRegisterModel>? = null
    fun getcusServRegister(context: Context,strUserAction : String,Customer_Type : String,ID_Customer : String,ID_Channel : String,ID_Priority : String,
                           ID_Category : String, ID_Company : String,ID_ComplaintList : String,ID_Services : String,ID_EmpMedia : String,ID_Status : String,
                           ID_AttendedBy : String, strCustomerName : String,strMobileNo : String,strAddress : String,strContactNo : String, strLandMark : String,
                           strFromDate : String, strToDate : String,strFromTime : String,strToTime : String,ID_Product : String,strDescription : String,
                           strDate : String,strTime : String,FK_Country : String,FK_States : String,FK_District : String,FK_Area : String,FK_Post : String,
                           FK_Place : String,ID_CompCategory :String,strLongitue :String,strLatitude :String,strLocationAddress :String,ID_SubCategory :String,ID_Brand :String) : LiveData<CustomerServiceRegisterModel>? {
        cusServRegisterData = CustomerServiceRegisterRepository.getServicesApiCall(context,strUserAction,Customer_Type,ID_Customer,ID_Channel,ID_Priority,ID_Category,
            ID_Company,ID_ComplaintList,ID_Services,ID_EmpMedia,ID_Status,ID_AttendedBy,strCustomerName,strMobileNo,strAddress,strContactNo,
            strLandMark,strFromDate,strToDate,strFromTime,strToTime,ID_Product,strDescription,strDate,strTime,FK_Country,FK_States,FK_District,FK_Area,
            FK_Post,FK_Place,ID_CompCategory,strLongitue,strLatitude,strLocationAddress,ID_SubCategory,ID_Brand)
        return cusServRegisterData
    }
}