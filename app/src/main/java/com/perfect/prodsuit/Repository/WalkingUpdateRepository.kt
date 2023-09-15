package com.perfect.prodsuit.Repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.perfect.prodsuit.Model.WalkingUpdateModel

object WalkingUpdateRepository {

    val walkingUpdateSetterGetter = MutableLiveData<WalkingUpdateModel>()
    val TAG: String = "WalkingUpdateRepository"

    fun getServicesApiCall(context: Context): MutableLiveData<WalkingUpdateModel> {
        putWalkingUpdate(context)
        return walkingUpdateSetterGetter
    }

    private fun putWalkingUpdate(context: Context) {

    }
}