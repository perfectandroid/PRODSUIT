package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DocumentDetailModel
import com.perfect.prodsuit.Repository.DocumentDetailRepository

class DocumentDetailViewModel : ViewModel()  {

    var documentDetailData: MutableLiveData<DocumentDetailModel>? = null
    fun getDocumentDetail(context: Context, ID_LeadGenerate : String, ID_LeadGenerateProduct : String) : MutableLiveData<DocumentDetailModel>? {
        documentDetailData = DocumentDetailRepository.getServicesApiCall(context,ID_LeadGenerate,ID_LeadGenerateProduct)
        return documentDetailData
    }
}