package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.PostModel
import com.perfect.prodsuit.Repository.PostRepository
import com.perfect.prodsuit.View.Activity.LeadGenerationActivity

class PostViewModel : ViewModel() {

    var postLiveData: MutableLiveData<PostModel>? = null

    fun getPost(context: Context,FK_Area : String) : LiveData<PostModel>? {
        postLiveData = PostRepository.getServicesApiCall(context, FK_Area)
        return postLiveData
    }
}