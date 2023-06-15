package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SendEmailModel
import com.perfect.prodsuit.Repository.SendEmailRepository

class SendEmailViewModel : ViewModel(){

    var sendemailData: MutableLiveData<SendEmailModel>? = null

    fun sendemail(context: Context,messageSubject : String,messageBody : String,mailid : String) : LiveData<SendEmailModel>? {
        sendemailData = SendEmailRepository.getServicesApiCall(context,messageSubject,messageBody,mailid)
        return sendemailData
    }
}