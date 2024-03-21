package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.UpdatePickUpAndDeliveryModel
import com.perfect.prodsuit.Repository.UpdatePickUpAndDeliveryRespository
import org.json.JSONArray

class UpdatePickUpAndDeliveryViewModel : ViewModel() {

    var updatePickUpAndDeliveryData: MutableLiveData<UpdatePickUpAndDeliveryModel>? = null

    fun getUpdatePickUpAndDelivery(context: Context, ID_ProductDelivery : String, PickDeliveryTime : String, PickDeliveryDate : String,remark : String,FK_BillType : String, Productdetails : JSONArray, PaymentDetail : JSONArray,DeliveryComplaints : JSONArray,StandByAmount :String,Status :String,strLongitue : String, strLatitude: String, locAddress :String) : MutableLiveData<UpdatePickUpAndDeliveryModel>? {
        updatePickUpAndDeliveryData = UpdatePickUpAndDeliveryRespository.getServicesApiCall(context,ID_ProductDelivery,PickDeliveryTime,PickDeliveryDate,remark,FK_BillType,Productdetails,PaymentDetail,DeliveryComplaints,StandByAmount,Status,strLongitue,strLatitude,locAddress)
        return updatePickUpAndDeliveryData
    }
}