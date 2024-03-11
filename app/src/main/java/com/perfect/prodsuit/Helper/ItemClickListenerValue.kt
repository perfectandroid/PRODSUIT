package com.perfect.prodsuit.Helper

interface ItemClickListenerValue : ItemClickListener {
    fun onClick(position: Int,data: String,value: String)
}