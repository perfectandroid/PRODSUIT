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

class ServiceProductHistoryAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "ServiceProductHistoryAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_service_product_history, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1


                holder.tv_TicketNo.text         = jsonObject!!.getString("TicketNo")
                holder.tv_RegOn.text            = jsonObject!!.getString("RegOn")
                holder.tv_AttendedBy.text       = jsonObject!!.getString("AttendedBy")
                holder.tv_Status.text           = jsonObject!!.getString("Status")
                holder.tv_Complaint.text        = jsonObject!!.getString("Complaint")
                holder.tv_EmployeeNotes.text    = jsonObject!!.getString("EmployeeNotes")

//                holder.llProduct!!.setTag(position)
//                holder.llProduct!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "serviceProduct")
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var tv_TicketNo       : TextView
        internal var tv_RegOn          : TextView
        internal var tv_AttendedBy     : TextView
        internal var tv_Status         : TextView
        internal var tv_Complaint      : TextView
        internal var tv_EmployeeNotes  : TextView
        internal var llProduct         : LinearLayout
        init {

            tv_TicketNo       = v.findViewById<View>(R.id.tv_TicketNo) as TextView
            tv_RegOn          = v.findViewById<View>(R.id.tv_RegOn) as TextView
            tv_AttendedBy     = v.findViewById<View>(R.id.tv_AttendedBy) as TextView
            tv_Status         = v.findViewById<View>(R.id.tv_Status) as TextView
            tv_Complaint      = v.findViewById<View>(R.id.tv_Complaint) as TextView
            tv_EmployeeNotes  = v.findViewById<View>(R.id.tv_EmployeeNotes) as TextView
            llProduct         = v.findViewById<View>(R.id.llProduct) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}