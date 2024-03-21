package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.StandByModel
import com.perfect.prodsuit.Repository.StandByRepository
import org.json.JSONArray

class StandByViewModel : ViewModel(){

    var standByData: MutableLiveData<StandByModel>? = null

    fun getStandBy(context: Context, ID_ProductDelivery : String, PickDeliveryTime : String, PickDeliveryDate : String, remark : String, FK_BillType : String, Productdetails : JSONArray, PaymentDetail : JSONArray, DeliveryComplaints : JSONArray, StandByAmount :String, Status :String, strLongitue : String, strLatitude: String, locAddress :String) : LiveData<StandByModel>? {
        standByData = StandByRepository.getServicesApiCall(context,ID_ProductDelivery,PickDeliveryTime,PickDeliveryDate,remark,FK_BillType,Productdetails,PaymentDetail,DeliveryComplaints,StandByAmount,Status,strLongitue,strLatitude,locAddress)
        return standByData
    }
}