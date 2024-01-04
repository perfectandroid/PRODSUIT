package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PaymentInfoModel
import com.perfect.prodsuit.Repository.PaymentInfoRepository


class PaymentInfoViewModel: ViewModel() {

    var paymentData: MutableLiveData<PaymentInfoModel>? = null

    fun getPaymentInfo(context: Context,ID_TransactionType:String,ID_Project:String,ID_Stage:String,ID_Employee:String,ID_BillType:String,
                       ID_PaymentMethod:String,ID_PettyCashier:String,asOnDate:String,ReqMode:String) : LiveData<PaymentInfoModel>? {
        paymentData = PaymentInfoRepository.getServicesApiCall(context,ID_TransactionType,ID_Project,ID_Stage,ID_Employee,ID_BillType,
            ID_PaymentMethod,ID_PettyCashier,asOnDate,ReqMode)
        return paymentData
    }
}