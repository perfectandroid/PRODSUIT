package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PaymentMethodModel
import com.perfect.prodsuit.Repository.PaymentMethodRepository

class PaymentMethodViewModel : ViewModel()  {

    var payMethodData: MutableLiveData<PaymentMethodModel>? = null
    fun getPaymentMethod(context: Context) : LiveData<PaymentMethodModel>? {
        payMethodData = PaymentMethodRepository.getServicesApiCall(context)
        return payMethodData
    }
}