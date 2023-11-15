package com.perfect.prodsuit.Model

data class ModelProjectCheckList(var ID_CheckListType:String, var CLTyName:String, var is_checked :Boolean = false ,var SubArrary : Array<ModelProjectCheckListSub> )
