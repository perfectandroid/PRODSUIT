package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialRequestSaveModel
import com.perfect.prodsuit.Repository.MaterialRequestSaveRepository
import org.json.JSONArray

class MaterialRequestSaveViewModel  : ViewModel() {

    var materialRequestSaveData: MutableLiveData<MaterialRequestSaveModel>? = null

    fun saveMaterialRequest(context: Context, UserAction : String, TransMode : String, ID_ProjectMaterialRequest : String, ID_Stage : String, ID_Project : String,
                          strUsagedate : String, ID_Employee : String, ID_Team : String, saveDetailArray : JSONArray
    ) : LiveData<MaterialRequestSaveModel>? {
        materialRequestSaveData = MaterialRequestSaveRepository.getServicesApiCall(context,UserAction,TransMode, ID_ProjectMaterialRequest, ID_Stage, ID_Project, strUsagedate, ID_Employee, ID_Team, saveDetailArray)
        return materialRequestSaveData
    }
}