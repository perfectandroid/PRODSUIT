package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AttendanceAddModel
import com.perfect.prodsuit.Repository.AttendanceAddRepository

class AttendanceAddViewModel : ViewModel(){

    var customerLiveData: MutableLiveData<AttendanceAddModel>? = null

    fun AddAttendance(context: Context , IsOnline : String, strLatitude : String , strLongitue : String , address  :String, SubMode : String) : LiveData<AttendanceAddModel>? {
        customerLiveData = AttendanceAddRepository.getServicesApiCall(context,IsOnline,strLatitude,strLongitue,address,SubMode)
        return customerLiveData
    }


}