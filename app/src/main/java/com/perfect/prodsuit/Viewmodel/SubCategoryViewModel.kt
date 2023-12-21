package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.SubCategoryModel
import com.perfect.prodsuit.Repository.SubCategoryRepository

class SubCategoryViewModel : ViewModel() {

    var subcategoryData: MutableLiveData<SubCategoryModel>? = null

    fun getSubCategory(context: Context, ReqMode : String, ID_Category : String) : LiveData<SubCategoryModel>? {
        subcategoryData = SubCategoryRepository.getServicesApiCall(context,ReqMode,ID_Category)
        return subcategoryData
    }
}