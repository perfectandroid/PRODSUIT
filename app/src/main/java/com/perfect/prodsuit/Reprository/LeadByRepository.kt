package com.perfect.prodsuit.Reprository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.LeadByModel
import com.perfect.prodsuit.Model.LeadFromModel


object LeadByRepository {

    private var progressDialog: ProgressDialog? = null
    val leadBySetterGetter = MutableLiveData<LeadByModel>()
    val TAG: String = "LeadByRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<LeadByModel> {
        getLeadBy(context)
        return leadBySetterGetter
    }

    private fun getLeadBy(context: Context) {

    }
}