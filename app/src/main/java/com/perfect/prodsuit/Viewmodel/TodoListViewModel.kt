package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.TodoListModel
import com.perfect.prodsuit.Repository.TodoListRepository

class TodoListViewModel : ViewModel() {

    var todolistLiveData: MutableLiveData<TodoListModel>? = null

    fun getTodolist(context: Context) : LiveData<TodoListModel>? {
        todolistLiveData = TodoListRepository.getServicesApiCall(context)
        return todolistLiveData
    }

}