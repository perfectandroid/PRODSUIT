package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.UpdateEMICollectionModel
import com.perfect.prodsuit.Repository.UpdateEMICollectionRepository
import org.json.JSONArray

class UpdateEMICollectionViewModel: ViewModel() {

    var updateEMICollectionLiveData: MutableLiveData<UpdateEMICollectionModel>? = null

    fun setUpdateEMICollection(context: Context,strSaveTrnsDate : String, ID_CustomerWiseEMI : String, strSaveCollectDate : String, strSaveTotalAmount : String, strSaveFineAmount : String,
                               strSaveNetAmount : String, ID_CollectedBy : String, saveEmiDetailsArray : JSONArray, savePaymentDetailArray:JSONArray) : LiveData<UpdateEMICollectionModel>? {
        updateEMICollectionLiveData = UpdateEMICollectionRepository.getServicesApiCall(context,strSaveTrnsDate,ID_CustomerWiseEMI,strSaveCollectDate,strSaveTotalAmount,strSaveFineAmount,
            strSaveNetAmount,ID_CollectedBy,saveEmiDetailsArray,savePaymentDetailArray)
        return updateEMICollectionLiveData
    }
}