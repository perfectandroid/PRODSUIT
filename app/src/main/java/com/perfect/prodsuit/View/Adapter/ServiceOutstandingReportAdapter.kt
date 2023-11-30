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
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceOutstandingReportAdapter (internal var context: Context, internal var jsonArray: JSONArray, internal var ReportMode: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceNewListReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_newlist_report, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                val pos = position+1

                holder.tv_TicketNo.text      = jsonObject!!.getString("TicketNo")
                holder.tv_CurrentStatus.text      = jsonObject!!.getString("CurrentStatus")
                holder.tv_Cust.text      = "Customer : "+jsonObject!!.getString("Customer")+"[Ph no. "+jsonObject!!.getString("Mobile")+"]"
                holder.tv_Product.text      = "Product : "+jsonObject!!.getString("Product")
                holder.tv_Complaint.text      = "Complaint or Service : "+jsonObject!!.getString("ComplaintorService")
                holder.tv_Ticketdate.text      = "Ticket Date : "+jsonObject!!.getString("TicketDate")


                holder.ll_newList!!.setTag(position)
                holder.ll_newList!!.setOnClickListener(View.OnClickListener {
                    Log.e(TAG,"serviceReportClick   5091")
                    clickListener!!.onClick(
                        position,
                        "serviceReportOutstandingListClick"
                    )
                })
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
        internal var ll_newList          : LinearLayout
        internal var tv_TicketNo         : TextView
        internal var tv_CurrentStatus       : TextView
        internal var tv_Cust         : TextView
        internal var tv_Product         : TextView
        internal var tv_Complaint          : TextView
        internal var tv_Ticketdate        : TextView
        init {
            tv_TicketNo        = v.findViewById<View>(R.id.tv_TicketNo) as TextView
            tv_CurrentStatus      = v.findViewById<View>(R.id.tv_CurrentStatus) as TextView
            tv_Cust        = v.findViewById<View>(R.id.tv_Cust) as TextView
            tv_Product         = v.findViewById<View>(R.id.tv_Product) as TextView
            tv_Complaint         = v.findViewById<View>(R.id.tv_Complaint) as TextView
            tv_Ticketdate       = v.findViewById<View>(R.id.tv_Ticketdate) as TextView
            ll_newList         = v.findViewById<View>(R.id.ll_newList) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}