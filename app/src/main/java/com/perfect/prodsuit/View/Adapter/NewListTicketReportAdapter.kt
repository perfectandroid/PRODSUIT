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

class NewListTicketReportAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "NewListTicketReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_newlistticket_report, parent, false
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
                holder.tv_Customer.text     = jsonObject!!.getString("Customer")
                holder.tv_Product.text      = jsonObject!!.getString("ProductORProject")
                holder.tv_Assignee.text       = jsonObject!!.getString("Assignee")
                holder.tv_CompleteDate.text   = jsonObject!!.getString("CompletedDate")
                holder.tv_Status.text   = jsonObject!!.getString("Status")
                holder.tv_Remark.text      = jsonObject!!.getString("Remarks")

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
        internal var tv_Customer        : TextView
        internal var tv_Product         : TextView
        internal var tv_Assignee          : TextView
        internal var tv_CompleteDate      : TextView
        internal var tv_Status      : TextView
        internal var tv_Remark         : TextView

        //        internal var llDistrict      : LinearLayout
        init {
            tv_SiNo        = v.findViewById<View>(R.id.tv_SiNo) as TextView
            tv_LeadNo        = v.findViewById<View>(R.id.tv_LeadNo) as TextView
            tv_LeadDate      = v.findViewById<View>(R.id.tv_LeadDate) as TextView
            tv_Customer      = v.findViewById<View>(R.id.tv_Customer) as TextView
            tv_Product       = v.findViewById<View>(R.id.tv_Product) as TextView
            tv_Assignee      = v.findViewById<View>(R.id.tv_Assignee) as TextView
            tv_CompleteDate        = v.findViewById<View>(R.id.tv_CompleteDate) as TextView
            tv_Status    = v.findViewById<View>(R.id.tv_Status) as TextView
            tv_Remark    = v.findViewById<View>(R.id.tv_Remark) as TextView
//            llDistrict  = v.findViewById<View>(R.id.llDistrict) as LinearLayout
        }
    }

}