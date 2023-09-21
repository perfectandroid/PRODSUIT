package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.perfect.prodsuit.Model.CalllogModel
import com.perfect.prodsuit.R
import java.util.Date
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CallLogListAdapter(internal var mContext: Context, private val catlist: ArrayList<CalllogModel>) :
	BaseAdapter() {
	internal var inflater: LayoutInflater

	init {
		inflater = LayoutInflater.from(mContext)
	}

	inner class ViewHolder {
		internal var textView1: TextView? = null
		internal var textView2: TextView? = null
		internal var textView3: TextView? = null
		internal var textView4: TextView? = null
		internal var textView5: TextView? = null
	}

	override fun getCount(): Int {
		return catlist.size
	}

	override fun getItem(position: Int): CalllogModel {
		return catlist[position]
	}

	override fun getItemId(position: Int): Long {
		return position.toLong()
	}

	override fun getView(position: Int, view: View?, parent: ViewGroup): View {
		var view = view
		val holder: ViewHolder
		if (view == null) {
			holder = ViewHolder()
			view = inflater.inflate(R.layout.calllog, null)
			holder.textView1 = view!!.findViewById<View>(R.id.textView1) as TextView
			holder.textView2 = view!!.findViewById<View>(R.id.textView2) as TextView
			holder.textView3 = view!!.findViewById<View>(R.id.textView3) as TextView
			holder.textView4 = view!!.findViewById<View>(R.id.textView4) as TextView
			holder.textView5 = view!!.findViewById<View>(R.id.textView5) as TextView
			view.tag = holder
		} else {
			holder = view.tag as ViewHolder
		}
		if(catlist[position].name.toString()!!.isNotEmpty()){
			holder.textView1!!.text = catlist[position].name
		}else{
			holder.textView1!!.text = "Unknown"
		}
		holder.textView2!!.text = catlist[position].number
		holder.textView5!!.text = catlist[position].type

		val millis = catlist[position].duration
		val duration = millis!!.toDuration(DurationUnit.MILLISECONDS)
		val timeString =
			duration.toComponents { minutes, seconds, _ ->
				String.format("%02d:%02d", minutes, seconds)
			}
		holder.textView4!!.text = timeString


		val callDate: String =  catlist[position].date
		val callDayTime = Date(java.lang.Long.valueOf(callDate))

		holder.textView3!!.text = callDayTime.toString()
		return view
	}

}