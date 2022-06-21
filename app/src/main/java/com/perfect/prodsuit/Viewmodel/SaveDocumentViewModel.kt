package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SaveDocumentModel
import com.perfect.prodsuit.Repository.SaveDocumentRepository

class SaveDocumentViewModel: ViewModel() {
    var saveDocsData: MutableLiveData<SaveDocumentModel>? = null

    fun saveDocuments(context: Context, ID_LeadGenerateProduct : String, strDate : String, strSubject : String, strDescription : String,
                      encodeDoc : String,extension: String) : LiveData<SaveDocumentModel>? {
        saveDocsData = SaveDocumentRepository.getServicesApiCall(context, ID_LeadGenerateProduct, strDate,strSubject, strDescription, encodeDoc,extension)
        return saveDocsData
    }
}