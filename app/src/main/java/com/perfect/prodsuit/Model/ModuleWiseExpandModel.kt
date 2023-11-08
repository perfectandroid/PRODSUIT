package com.perfect.prodsuit.Model

import com.perfect.prodsuit.Helper.ParentConstants

data class ModuleWiseExpandModel(val parentTitle:String?=null,
                                 var type:Int = ParentConstants.PARENT,
                                 var subList : MutableList<ChildDataModel> = ArrayList(),
                                 var isExpanded:Boolean = false)
