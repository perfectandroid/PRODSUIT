package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.ServiceFollowUpSaveModel

import com.perfect.prodsuit.Repository.ServiceFollowUpSaveRepository
import org.json.JSONArray

class ServiceFollowUpSaveViewModel : ViewModel()  {

    var ServiceFollowUpSaveLiveData: MutableLiveData<ServiceFollowUpSaveModel>? = null

    fun saveFollowUp(context: Context,
                     UserAction: String,
                     FK_Customerserviceregister: String,
                     ID_CustomerServiceRegisterProductDetails: String,
                     StartingDate: String,
                     ComponentCharge: String,
                     ServiceCharge: String,
                     OtherCharge: String,
                     TotalSecurityAmount: String,
                     TotalAmount: String,
                     DiscountAmount: String,
                     FK_Company: String,
                     FK_BranchCodeUser: String,
                     EntrBy: String,
                     FK_BillType: String,
                     FK_Machine: String,
                     TransMode: String,
                     ServiceDetails: JSONArray,
                     ProductDetails: JSONArray,
                     Actionproductdetails: JSONArray,
                     AttendedEmployeeDetails : JSONArray,ServiceIncentive : JSONArray,OtherCharges : JSONArray,PaymentDetail : JSONArray) : LiveData<ServiceFollowUpSaveModel>? {

        ServiceFollowUpSaveLiveData = ServiceFollowUpSaveRepository.getServicesApiCall(context,UserAction,
            FK_Customerserviceregister,
            ID_CustomerServiceRegisterProductDetails,
            StartingDate,
            ComponentCharge,
            ServiceCharge,
            OtherCharge,
            TotalSecurityAmount,
            TotalAmount,
            DiscountAmount,
            FK_Company,
            FK_BranchCodeUser,
            EntrBy,
            FK_BillType,
            FK_Machine,
            TransMode,
            ServiceDetails,
            ProductDetails,
            Actionproductdetails,
            AttendedEmployeeDetails,ServiceIncentive,OtherCharges,PaymentDetail)
        Log.e("LeadGenerateSaveViewModel"," 2266662    ")
        return ServiceFollowUpSaveLiveData
    }

//    "UserAction": "1",
//    "FK_Customerserviceregister": "22",
//    "ID_CustomerServiceRegisterProductDetails": "30377",
//    "StartingDate": "2023-04-26",
//    "ComponentCharge": "120",
//    "ServiceCharge": "120",
//    "OtherCharge": "120",
//    "TotalSecurityAmount": "120",
//    "TotalAmount": "120",
//    "DiscountAmount": "120",
//    "FK_Company": "1",
//    "FK_BranchCodeUser": "3",
//    "EntrBy": "App",
//    "FK_BillType": "44",
//    "FK_Machine": "10",
//    "TransMode": "'CUSF'",

}