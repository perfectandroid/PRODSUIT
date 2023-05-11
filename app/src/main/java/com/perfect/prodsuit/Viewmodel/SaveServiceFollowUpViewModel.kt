package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.perfect.prodsuit.Model.SaveServiceFollowUpModel
import com.perfect.prodsuit.Repository.SaveServiceFollowUpRepository
import org.json.JSONArray

class SaveServiceFollowUpViewModel: ViewModel() {


    var saveServiceFollowUpLiveData: MutableLiveData<SaveServiceFollowUpModel>? = null

    fun saveServiceFollowUp(context: Context, customer_service_register : String, strCustomerNote : String, strEmployeeNote : String, strVisitedDate : String
                            , strTotalAmount : String, strReplacementAmount : String, ID_Action : String, strFollowUpDate : String, ID_AssignedTo : String, ID_Billtype : String,
                            saveServiceAttendedArray : JSONArray, saveReplacedeProductArray :JSONArray, saveAttendedEmployeeArray :JSONArray,
                            savePaymentDetailArray :JSONArray) : LiveData<SaveServiceFollowUpModel>? {

        Log.e("TAG","12522  ")

        saveServiceFollowUpLiveData = SaveServiceFollowUpRepository.getServicesApiCall(context,customer_service_register,strCustomerNote,strEmployeeNote,strVisitedDate,
            strTotalAmount,strReplacementAmount,ID_Action,strFollowUpDate,ID_AssignedTo,ID_Billtype,saveServiceAttendedArray,saveReplacedeProductArray,
            saveAttendedEmployeeArray,savePaymentDetailArray)
        Log.e("TAG","125222  ")
        return saveServiceFollowUpLiveData
    }

}