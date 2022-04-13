package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.DocumentModel
import com.perfect.prodsuit.Repository.DocumentRepository

class DocumentViewModel : ViewModel() {

    var documentData: MutableLiveData<DocumentModel>? = null
    fun getDocument(context: Context) : LiveData<DocumentModel>? {
        documentData = DocumentRepository.getServicesApiCall(context)
        return documentData
    }
}