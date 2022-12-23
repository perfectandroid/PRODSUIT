package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.UpdateLeadManagementModel
import com.perfect.prodsuit.Repository.UpdateLeadManagementRepository

class UpdateLeadManagementViewModel  : ViewModel(){

    var updateLeadManagementLiveData: MutableLiveData<UpdateLeadManagementModel>? = null

    fun getUpdateLeadManagement(context: Context,ID_LeadGenerateProduct :String,ID_LeadGenerate :String,ID_ActionType :String,ID_Employee :String,ID_Status :String,
                                strFollowUpDate :String,strFollowUpTime : String, strCustomerRemark :String,strEmployeeRemark :String,ID_NextAction :String,ID_NextActionType :String,
                                strNextFollowUpDate :String,ID_Priority :String, ID_Department :String,ID_NextEmployee :String, strCallStatus: String?,strCallDuration: String?,
                                strLatitude: String?,strLongitude: String?,encode1: String?,encode2: String?) : LiveData<UpdateLeadManagementModel>? {
        updateLeadManagementLiveData = UpdateLeadManagementRepository.getServicesApiCall(context,ID_LeadGenerateProduct,ID_LeadGenerate,ID_ActionType,ID_Employee,ID_Status,
            strFollowUpDate, strFollowUpTime, strCustomerRemark,strEmployeeRemark,ID_NextAction,ID_NextActionType,strNextFollowUpDate,ID_Priority,ID_Department,ID_NextEmployee,
            strCallStatus,strCallDuration,strLatitude,strLongitude,encode1,encode2)
        return updateLeadManagementLiveData
    }
}