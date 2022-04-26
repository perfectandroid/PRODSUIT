package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.LeadEditDetailModel

object LeadEditDetailRepository {

    private var progressDialog: ProgressDialog? = null
    val leadEditSetterGetter = MutableLiveData<LeadEditDetailModel>()
    val TAG: String = "LeadEditRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<LeadEditDetailModel> {
        getLeadEditDetails(context)
        return leadEditSetterGetter
    }

    private fun getLeadEditDetails(context: Context) {

    }
}