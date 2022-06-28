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

class ServiceProductAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "ServiceProductAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_service_product, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.tv_prod_tktno.text        = jsonObject!!.getString("TicketNo")
                holder.tv_prod_regOn.text      = jsonObject!!.getString("RegOn")
                holder.tv_prod_Complaint.text       = jsonObject!!.getString("Complaint")
                holder.tv_prod_Status.text        = jsonObject!!.getString("Status")
                holder.tv_prod_AttendBy.text      = jsonObject!!.getString("AttendedBy")
                holder.tv_prod_EmpNote.text       = jsonObject!!.getString("Employee")

//                holder.llfollowuptype!!.setTag(position)
//                holder.llfollowuptype!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "followuptype")
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
        internal var tv_prod_tktno    : TextView
        internal var tv_prod_regOn  : TextView
        internal var tv_prod_Complaint   : TextView
        internal var tv_prod_Status   : TextView
        internal var tv_prod_AttendBy   : TextView
        internal var tv_prod_EmpNote   : TextView
        internal var llProduct       : LinearLayout
        init {
            tv_prod_tktno      = v.findViewById<View>(R.id.tv_prod_tktno) as TextView
            tv_prod_regOn    = v.findViewById<View>(R.id.tv_prod_regOn) as TextView
            tv_prod_Complaint     = v.findViewById<View>(R.id.tv_prod_Complaint) as TextView
            tv_prod_Status     = v.findViewById<View>(R.id.tv_prod_Status) as TextView
            tv_prod_AttendBy     = v.findViewById<View>(R.id.tv_prod_AttendBy) as TextView
            tv_prod_EmpNote     = v.findViewById<View>(R.id.tv_prod_EmpNote) as TextView
            llProduct         = v.findViewById<View>(R.id.llProduct) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}