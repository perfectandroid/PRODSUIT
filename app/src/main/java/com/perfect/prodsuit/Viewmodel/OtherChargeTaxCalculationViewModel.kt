package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.OtherChargeTaxCalculationModel
import com.perfect.prodsuit.Repository.OtherChargeTaxCalculationRepository

class OtherChargeTaxCalculationViewModel : ViewModel()  {

    var otherChargeTaxCalculationData: MutableLiveData<OtherChargeTaxCalculationModel>? = null

    fun getOtherChargeTaxCalculation(context: Context,ReqMode : String,FK_TaxGroup : String,IncludeTaxCalc : String,AmountTaxCalc : String) : LiveData<OtherChargeTaxCalculationModel>? {
        otherChargeTaxCalculationData = OtherChargeTaxCalculationRepository.getServicesApiCall(context,ReqMode,FK_TaxGroup,IncludeTaxCalc,AmountTaxCalc)
        return otherChargeTaxCalculationData
    }
}