package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PayMethodModel
import com.perfect.prodsuit.Repository.PaymentMethodRepository

class PaymentMethodViewModel : ViewModel()  {

    var payMethodData: MutableLiveData<PayMethodModel>? = null
    fun getPaymentMethod(context: Context,ReqMode : String) : LiveData<PayMethodModel>? {
        payMethodData = PaymentMethodRepository.getServicesApiCall(context,ReqMode)
        return payMethodData
    }
}