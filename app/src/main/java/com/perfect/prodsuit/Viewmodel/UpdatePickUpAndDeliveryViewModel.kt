package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.UpdatePickUpAndDeliveryModel
import com.perfect.prodsuit.Model.ViewDocumentModel
import com.perfect.prodsuit.Repository.UpdatePickUpAndDeliveryRespository
import com.perfect.prodsuit.Repository.ViewDocumentRepository
import org.json.JSONArray

class UpdatePickUpAndDeliveryViewModel : ViewModel() {

    var updatePickUpAndDeliveryData: MutableLiveData<UpdatePickUpAndDeliveryModel>? = null

    fun getUpdatePickUpAndDelivery(context: Context, ID_ProductDelivery : String, PickDeliveryTime : String, PickDeliveryDate : String,remark : String,FK_BillType : String, Productdetails : JSONArray, PaymentDetail : JSONArray,StandByAmount :String,Status :String) : MutableLiveData<UpdatePickUpAndDeliveryModel>? {
        updatePickUpAndDeliveryData = UpdatePickUpAndDeliveryRespository.getServicesApiCall(context,ID_ProductDelivery,PickDeliveryTime,PickDeliveryDate,remark,FK_BillType,Productdetails,PaymentDetail,StandByAmount,Status)
        return updatePickUpAndDeliveryData
    }
}