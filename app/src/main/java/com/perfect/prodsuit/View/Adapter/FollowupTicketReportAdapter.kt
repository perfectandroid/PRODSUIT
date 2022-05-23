package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class FollowupTicketReportAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "FollowupTicketReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_followupticket_report, parent, false
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
                holder.tv_SiNo.text         = pos.toString()
                holder.tv_LeadNo.text       = jsonObject!!.getString("LeadNo")
                holder.tv_LeadDate.text     = jsonObject!!.getString("LeadDate")
                holder.tv_Action.text       = jsonObject!!.getString("Action")
                holder.tv_ActionType.text   = jsonObject!!.getString("ActionType")
                holder.tv_Customer.text     = jsonObject!!.getString("Customer")
                holder.tv_Product.text      = jsonObject!!.getString("ProductORProject")
                holder.tv_DueDays.text      = jsonObject!!.getString("DueDays")
                holder.tv_Assignee.text     = jsonObject!!.getString("Assignee")
//                holder.tv_Remarks.text      = jsonObject!!.getString("Remarks")

//                holder.llDistrict!!.setTag(position)
//                holder.llDistrict!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "districtdetail")
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
        internal var tv_SiNo            : TextView
        internal var tv_LeadNo          : TextView
        internal var tv_LeadDate        : TextView
        internal var tv_Action          : TextView
        internal var tv_ActionType      : TextView
        internal var tv_Customer        : TextView
        internal var tv_Product         : TextView
//        internal var tv_FollowupDt      : TextView
        internal var tv_DueDays         : TextView
        internal var tv_Assignee        : TextView
        internal var tv_Remarks         : TextView
        //        internal var llDistrict      : LinearLayout
        init {
            tv_SiNo        = v.findViewById<View>(R.id.tv_SiNo) as TextView
            tv_LeadNo        = v.findViewById<View>(R.id.tv_LeadNo) as TextView
            tv_LeadDate      = v.findViewById<View>(R.id.tv_LeadDate) as TextView
            tv_Customer      = v.findViewById<View>(R.id.tv_Customer) as TextView
            tv_Product       = v.findViewById<View>(R.id.tv_Product) as TextView
            tv_Action        = v.findViewById<View>(R.id.tv_Action) as TextView
            tv_ActionType    = v.findViewById<View>(R.id.tv_ActionType) as TextView
//            tv_FollowupDt    = v.findViewById<View>(R.id.tv_FollowupDt) as TextView
            tv_DueDays       = v.findViewById<View>(R.id.tv_DueDays) as TextView
            tv_Assignee      = v.findViewById<View>(R.id.tv_Assignee) as TextView
            tv_Remarks       = v.findViewById<View>(R.id.tv_Remarks) as TextView
//            llDistrict  = v.findViewById<View>(R.id.llDistrict) as LinearLayout
        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }



}