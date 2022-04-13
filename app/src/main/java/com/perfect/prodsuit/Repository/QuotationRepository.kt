package com.perfect.prodsuit.Repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.QuotationModel

object QuotationRepository {

    val quotationSetterGetter = MutableLiveData<QuotationModel>()
    val TAG: String = "QuotationRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<QuotationModel> {
        getQuotation(context)
        return quotationSetterGetter
    }

    private fun getQuotation(context: Context) {

    }
}