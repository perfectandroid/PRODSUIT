package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.perfect.prodsuit.R

class CustomAdapter (context: Context, resource: Int, objects: Array<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_dropdown_item, parent, false)
        val pos = position+1
        val textView = view.findViewById<TextView>(R.id.custom_textview)
        val txtsino = view.findViewById<TextView>(R.id.txtsino)
        textView.text = getItem(position)
        txtsino.setText(pos.toString())

        return view
    }
}