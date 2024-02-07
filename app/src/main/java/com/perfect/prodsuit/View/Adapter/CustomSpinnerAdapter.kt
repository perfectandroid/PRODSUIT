package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.perfect.prodsuit.Model.ModelProjectCheckList
import com.perfect.prodsuit.Model.MpinUserModel
import com.perfect.prodsuit.R

class CustomSpinnerAdapter(context: Context, resource: Int, private val items: List<MpinUserModel>) :
    ArrayAdapter<MpinUserModel>(context, resource, items) {

        var TAG ="CustomSpinnerAdapter"

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_center, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        Log.e(TAG,"32222  IP_Default   "+  getItem(position)!!.IP_Default)
        textView.text = getItem(position)!!.ResellerName
        return view
    }
}