package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AttendancereportModel
import com.perfect.prodsuit.Repository.AttendanceReportRepository

class AttendanceReportViewModel  : ViewModel() {

    var attendanceReprtData: MutableLiveData<AttendancereportModel>? = null

    fun getAttendanceReprt(context: Context, strToDate: String?) : LiveData<AttendancereportModel>? {
        attendanceReprtData = AttendanceReportRepository.getServicesApiCall(context,strToDate)
        return attendanceReprtData
    }
}