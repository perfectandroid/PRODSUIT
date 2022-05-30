package com.perfect.prodsuit.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Repository.LeadGenerateSaveRepository
import java.lang.Exception
import com.perfect.prodsuit.Model.LeadGenerateSaveModel as LeadGenerateSaveModel

class LeadGenerateSaveViewModel : ViewModel()  {

    var LeadGenSaveLiveData: MutableLiveData<LeadGenerateSaveModel>? = null

    fun saveLeadGenerate(context: Context, strDate :String, ID_LeadFrom :String, ID_LeadThrough :String, ID_CollectedBy :String,
                         ID_Customer :String, Customer_Name :String, Customer_Address :String, Customer_Mobile :String, Customer_Email :String,
                         CompanyNme :String,CusPhone :String, ID_MediaMaster :String, FK_Country :String, FK_States :String,
                         FK_District :String, FK_Post :String, ID_Category :String, ID_Product :String,strProdName :String, strQty :String, ID_Priority :String,
                         strFeedback :String, ID_Status :String, ID_NextAction :String, ID_ActionType :String, strFollowupdate :String, ID_Branch :String,
                         ID_BranchType :String, ID_Department :String, ID_Employee :String, strLatitude :String, strLongitue :String, locAddress :String,
                            encode1 :String, encode2 :String, saveUpdateMode : String, strContactPerson :String, strContactNumber : String) : LiveData<LeadGenerateSaveModel>? {
            Log.e("LeadGenerateSaveViewModel"," 2266661    ")
            LeadGenSaveLiveData = LeadGenerateSaveRepository.getServicesApiCall(context, strDate, ID_LeadFrom!!, ID_LeadThrough!!, ID_CollectedBy!!, ID_Customer!!,
                Customer_Name!!, Customer_Address!!, Customer_Mobile!!, Customer_Email!!,CompanyNme,CusPhone, ID_MediaMaster!!, FK_Country, FK_States,
                FK_District, FK_Post, ID_Category!!, ID_Product!!, strProdName, strQty, ID_Priority!!, strFeedback, ID_Status!!, ID_NextAction, ID_ActionType,
                strFollowupdate, ID_Branch, ID_BranchType, ID_Department, ID_Employee, strLatitude!!,strLongitue!!, locAddress!!, encode1, encode2,
                saveUpdateMode,strContactPerson,strContactNumber)
            Log.e("LeadGenerateSaveViewModel"," 2266662    ")
        return LeadGenSaveLiveData
    }

}