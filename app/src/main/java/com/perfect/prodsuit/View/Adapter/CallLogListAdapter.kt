package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.perfect.prodsuit.Model.CalllogModel
import com.perfect.prodsuit.R
import java.text.SimpleDateFormat
import java.util.*

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
		internal var imgcall: ImageView? = null
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
			holder.imgcall = view!!.findViewById<View>(R.id.imgcall) as ImageView
			view.tag = holder
		} else {
			holder = view.tag as ViewHolder
		}
		if(catlist[position].name.toString()!!.isNotEmpty()){
			holder.textView1!!.text = catlist[position].name
		}else{
			holder.textView1!!.text = "Unknown"
		}

		if( catlist[position].type.equals("1")){
		holder.imgcall!!.setImageResource(R.drawable.incomingcall)
		}else if( catlist[position].type.equals("2")){
			holder.imgcall!!.setImageResource(R.drawable.outcomingcall)
		}else if( catlist[position].type.equals("3")){
			holder.imgcall!!.setImageResource(R.drawable.missedcall)
		}
		holder.textView2!!.text = catlist[position].number

		val millis = catlist[position].duration
		var second: Int = millis!!.toInt()
		val hours = second / 3600
		val minutes = second % 3600 / 60
		second = second % 60
		val formattedDuration = String.format("%02d:%02d:%02d", hours, minutes, second)
		holder.textView4!!.text = formattedDuration



		val callDate: String =  catlist[position].date
		val callDayTime = Date(java.lang.Long.valueOf(callDate))

		val seconds = callDate.toLong()
		val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm")
		val dateString: String = formatter.format(Date(seconds))
		holder.textView3!!.text = dateString
		return view
	}

}