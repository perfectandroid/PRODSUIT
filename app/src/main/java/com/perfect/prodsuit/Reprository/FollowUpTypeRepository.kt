package com.perfect.prodsuit.Reprository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.FollowUpTypeModel

object FollowUpTypeRepository {

    private var progressDialog: ProgressDialog? = null
    val followuptypeSetterGetter = MutableLiveData<FollowUpTypeModel>()
    val TAG: String = "FollowUpTypeRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<FollowUpTypeModel> {
        getFollowUpType(context)
        return followuptypeSetterGetter
    }

    private fun getFollowUpType(context: Context) {

    }
}