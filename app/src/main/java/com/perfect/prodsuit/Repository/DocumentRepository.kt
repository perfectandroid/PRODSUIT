package com.perfect.prodsuit.Repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.DocumentModel

object DocumentRepository {

    val documentSetterGetter = MutableLiveData<DocumentModel>()
    val TAG: String = "DocumentRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<DocumentModel> {
        getLeadHistory(context)
        return documentSetterGetter
    }

    private fun getLeadHistory(context: Context) {
    }

}