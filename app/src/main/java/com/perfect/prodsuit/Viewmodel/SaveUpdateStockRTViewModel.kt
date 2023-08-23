package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SaveUpdateStockRTModel
import com.perfect.prodsuit.Repository.SaveUpdateStockRTRepository
import org.json.JSONArray

class SaveUpdateStockRTViewModel : ViewModel() {

    var saveUpdateStockRTData: MutableLiveData<SaveUpdateStockRTModel>? = null

    fun saveUpdateStockRT(context: Context,UserAction : String ,FK_BranchFrom : String,FK_DepartmentFrom : String,FK_EmployeeFrom : String,STRequest : String,strDate : String,
    FK_BranchTo : String,FK_DepartmentTo : String,FK_EmployeeTo : String,TransMode : String,FK_StockRequest : String,ID_StockTransfer : String,
                          saveDetailArray : JSONArray) : LiveData<SaveUpdateStockRTModel>? {
        saveUpdateStockRTData = SaveUpdateStockRTRepository.getServicesApiCall(context,UserAction, FK_BranchFrom!!,FK_DepartmentFrom!!,FK_EmployeeFrom!!,STRequest,strDate,
            FK_BranchTo,FK_DepartmentTo,FK_EmployeeTo,TransMode,FK_StockRequest,ID_StockTransfer,saveDetailArray)
        return saveUpdateStockRTData
    }
}