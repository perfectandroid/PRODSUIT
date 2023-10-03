package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.MaterialUsageSaveModel
import com.perfect.prodsuit.Repository.MaterialUsageSaveRepository
import org.json.JSONArray

class MaterialUsageSaveViewModel  : ViewModel() {

    var materialUsageSaveData: MutableLiveData<MaterialUsageSaveModel>? = null

    fun saveMaterialUsage(context: Context,UserAction : String,TransMode : String,ID_ProjectMaterialUsage : String,ID_Stage : String,ID_Project : String,
                          strUsagedate : String,ID_Employee : String,ID_Team : String,saveDetailArray : JSONArray) : LiveData<MaterialUsageSaveModel>? {
        materialUsageSaveData = MaterialUsageSaveRepository.getServicesApiCall(context,UserAction,TransMode, ID_ProjectMaterialUsage, ID_Stage, ID_Project, strUsagedate, ID_Employee, ID_Team, saveDetailArray)
        return materialUsageSaveData
    }
}