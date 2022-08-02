package com.perfect.prodsuit.Viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.TodoListModel
import com.perfect.prodsuit.Repository.TodoListRepository

class TodoListViewModel : ViewModel() {

    var todolistLiveData: MutableLiveData<TodoListModel>? = null

    fun getTodolist(context: Context ,submode : String, name  : String, criteria  : String,date : String) : LiveData<TodoListModel>? {
        todolistLiveData = TodoListRepository.getServicesApiCall(context,submode,name,criteria,date)
        return todolistLiveData
    }

}