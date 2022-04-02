package com.perfect.prodsuit.Reprository

import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.ProductDetailModel

object ProductDetailRepository {

    private var progressDialog: ProgressDialog? = null
    val productdetailSetterGetter = MutableLiveData<ProductDetailModel>()
    val TAG: String = "ProductDetailRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<ProductDetailModel> {
        getProductDetail(context)
        return productdetailSetterGetter
    }

    private fun getProductDetail(context: Context) {

    }
}