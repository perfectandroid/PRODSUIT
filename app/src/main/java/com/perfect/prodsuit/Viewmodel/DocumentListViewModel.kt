package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DocumentListModel
import com.perfect.prodsuit.Repository.DocumentListRepository

class DocumentListViewModel : ViewModel() {

    var documentlistLiveData: MutableLiveData<DocumentListModel>? = null

    fun getDocumentlist(context: Context,ID_LeadGenerateProduct : String) : LiveData<DocumentListModel>? {
        documentlistLiveData = DocumentListRepository.getServicesApiCall(context,ID_LeadGenerateProduct)
        return documentlistLiveData
    }

}