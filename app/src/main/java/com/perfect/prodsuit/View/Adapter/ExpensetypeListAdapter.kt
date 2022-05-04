package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.perfect.prodsuit.Model.ExpenseTypeModel
import com.perfect.prodsuit.Model.ExpensetypelistModel
import com.perfect.prodsuit.R
import java.util.ArrayList

class ExpensetypeListAdapter(internal var mContext: Context, private val arraylist: ArrayList<ExpensetypelistModel>) :
    BaseAdapter() {

    internal var inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    inner class ViewHolder {
        internal var tvAreaName: TextView? = null
    }

    override fun getCount(): Int {
        return arraylist.size
    }

    override fun getItem(position: Int): ExpensetypelistModel {
        return arraylist[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            holder = ViewHolder()
            view = inflater.inflate(R.layout.reporttypelist, null)
            holder.tvAreaName = view!!.findViewById<View>(R.id.tvAreaName) as TextView
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.tvAreaName!!.text = arraylist[position].SettingsName
        return view
    }

}