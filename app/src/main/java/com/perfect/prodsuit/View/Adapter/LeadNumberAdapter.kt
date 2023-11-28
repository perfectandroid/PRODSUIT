package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class LeadNumberAdapter(internal var context: Context, internal var jsonArray: JSONArray, internal var report: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "LeadnoAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    var pos = 0
//    private var mItemClickListener: ItemClickListenerData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_report_name, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                if (report.equals("Project")){
                   // holder.llReportName!!.visibility = View.GONE
                    pos++
                    holder.txtsino.text        = pos.toString()
                    holder.txtReportName.text        = jsonObject!!.getString("LeadNo")
                }

                holder.llReportName!!.setTag(position)
                holder.llReportName!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "LeadNoClick"
                    )
                })
//
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        return jsonArray.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtReportName   : TextView
        internal var txtsino         : TextView
        internal var llReportName    : LinearLayout
        init {
            txtReportName      = v.findViewById<View>(R.id.txtReportName) as TextView
            txtsino            = v.findViewById<View>(R.id.txtsino) as TextView
            llReportName       = v.findViewById<View>(R.id.llReportName) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

//    fun addItemClickListener(listener: ItemClickListenerData) {
//        mItemClickListener = listener
//    }

}