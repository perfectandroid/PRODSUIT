package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class HistoryActMesssageAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "HistoryActMesssageAdapter"
    internal var jsonObject: JSONObject? = null
//    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_submessage_history, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.txtAbout.text         = jsonObject!!.getString("EnquiryAbout")
                holder.txtAction.text        = jsonObject!!.getString("Action")
                holder.txtActiondate.text    = jsonObject!!.getString("ActionDate")
                holder.txtStatus.text        = jsonObject!!.getString("Status")
                holder.txtAgentRemark.text   = jsonObject!!.getString("Agentremarks")
                holder.txtFollowedBy.text    = jsonObject!!.getString("FollowedBy")

//                if (position % 2 == 0){
//                    holder.llleadHistory!!.setBackgroundColor(context.getColor(R.color.greylight))
//                }
//                else{
//                    holder.llleadHistory!!.setBackgroundColor(context.getColor(R.color.white))
//                }

//                holder.llleadHistory!!.setTag(position)
//                holder.llleadHistory!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "prodcategory")
//
//                })
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

        internal var txtAbout         : TextView
        internal var txtAction        : TextView
        internal var txtActiondate    : TextView
        internal var txtStatus        : TextView
        internal var txtAgentRemark   : TextView
        internal var txtFollowedBy    : TextView
        internal var llleadHistory    : LinearLayout

        init {
            txtAbout         = v.findViewById<View>(R.id.txtAbout) as TextView
            txtAction        = v.findViewById<View>(R.id.txtAction) as TextView
            txtActiondate    = v.findViewById<View>(R.id.txtActiondate) as TextView
            txtStatus        = v.findViewById<View>(R.id.txtStatus) as TextView
            txtAgentRemark   = v.findViewById<View>(R.id.txtAgentRemark) as TextView
            txtFollowedBy    = v.findViewById<View>(R.id.txtFollowedBy) as TextView
            llleadHistory    = v.findViewById<View>(R.id.llleadHistory) as LinearLayout
        }
    }
}