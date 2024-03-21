package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.UpadateSiteVisitModel
import com.perfect.prodsuit.Repository.UpadateSiteVisitRepository
import org.json.JSONArray

class UpadateSiteVisitViewModel : ViewModel() {

    var upadateSiteVisitData: MutableLiveData<UpadateSiteVisitModel>? = null

    fun getUpadateSiteVisit(context: Context, UserAction : String, strLeadno : String, ID_SiteVisitAssignment: String ,strVisitdate : String, visitTime : String, strInspectionNote1 : String,
                            strInspectionNote2 : String, strCustomerNotes : String, strExpenseAmount : String, strCommonRemark : String, strInspectionCharge : String,
                            saveEmployeeDetails : JSONArray, saveMeasurementDetails : JSONArray, saveCheckedDetails : JSONArray, pssOtherCharge : JSONArray,
                            pssOtherChargeTax : JSONArray) : LiveData<UpadateSiteVisitModel>? {
        upadateSiteVisitData = UpadateSiteVisitRepository.getServicesApiCall(context,UserAction,strLeadno,ID_SiteVisitAssignment,strVisitdate,visitTime,strInspectionNote1,strInspectionNote2,
            strCustomerNotes,strExpenseAmount,strCommonRemark,strInspectionCharge,saveEmployeeDetails,saveMeasurementDetails,saveCheckedDetails,
            pssOtherCharge,pssOtherChargeTax)
        return upadateSiteVisitData
    }
}