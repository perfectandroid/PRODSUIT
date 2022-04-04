package com.perfect.prodsuit.Reprository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.FollowUpActionModel
import com.perfect.prodsuit.Model.ProductCategoryModel


object FollowUpActionRepository {

    private var progressDialog: ProgressDialog? = null
    val followupactionSetterGetter = MutableLiveData<FollowUpActionModel>()
    val TAG: String = "FollowUpActionRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<FollowUpActionModel> {
        getFollowUpAction(context)
        return followupactionSetterGetter
    }

    private fun getFollowUpAction(context: Context) {

    }
}