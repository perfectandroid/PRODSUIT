package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.LeadStagesDashModel

object LeadStagesDashRepository {

    private var progressDialog: ProgressDialog? = null
    val leadstagesdashSetterGetter = MutableLiveData<LeadStagesDashModel>()
    val TAG: String = "LeadStagesDashRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<LeadStagesDashModel> {
          getLeadStagesDashboard(context)
        return leadstagesdashSetterGetter
    }

    private fun getLeadStagesDashboard(context: Context) {

    }
}