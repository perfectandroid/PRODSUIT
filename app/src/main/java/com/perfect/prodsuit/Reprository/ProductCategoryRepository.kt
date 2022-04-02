package com.perfect.prodsuit.Reprository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ProductCategoryModel

object ProductCategoryRepository {

    private var progressDialog: ProgressDialog? = null
    val productcategorySetterGetter = MutableLiveData<ProductCategoryModel>()
    val TAG: String = "ProductCategoryRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ProductCategoryModel> {
        getProductCategory(context)
        return productcategorySetterGetter
    }

    private fun getProductCategory(context: Context) {

    }
}