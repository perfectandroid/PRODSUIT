package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.UpdateProjectTransactionModel
import com.perfect.prodsuit.Repository.UpdateProjectTransactionRepository
import org.json.JSONArray

class UpdateProjectTransactionViewModel : ViewModel()  {

    var updateProjectTransactionData: MutableLiveData<UpdateProjectTransactionModel>? = null
    fun SaveUpdateProjectTransaction(context: Context,UserAction : String,Date : String,FK_Project : String,FK_Stage : String,
                                 NetAmount : String,OtherCharge : String,Remark : String,
                                 pssOtherCharge : JSONArray ,pssOtherChargeTax : JSONArray,PaymentDetails : JSONArray,
                                     ID_TransactionType : String,ID_Employee : String,strRoundOff : String,ID_BillType : String,ID_PettyCashier : String) : LiveData<UpdateProjectTransactionModel>? {
        updateProjectTransactionData = UpdateProjectTransactionRepository.getServicesApiCall(context,UserAction,Date,FK_Project,FK_Stage,NetAmount,OtherCharge,
            Remark,pssOtherCharge,pssOtherChargeTax,PaymentDetails,ID_TransactionType,ID_Employee,strRoundOff,ID_BillType,ID_PettyCashier)
        return updateProjectTransactionData
    }
}