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

class ServiceAssignAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceAssignAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_service_assign, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.tv_TktNo.text        = jsonObject!!.getString("TicketNo")
                holder.tv_TktDate.text      = jsonObject!!.getString("TicketDate")
                holder.tv_Customer.text       = jsonObject!!.getString("Customer")
                holder.tv_Branch.text        = jsonObject!!.getString("Branch")
                holder.tv_Mobile.text        = jsonObject!!.getString("Mobile")
                holder.tv_Area.text        = jsonObject!!.getString("Area")
                holder.tv_TimeDue.text        = jsonObject!!.getString("TimeDue")
                holder.tv_Status.text        = jsonObject!!.getString("Status")

                holder.tv_TktNo!!.getBackground().setTint(context.getColor(R.color.service_adp3));


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
        internal var tv_TktNo    : TextView
        internal var tv_TktDate   : TextView
        internal var tv_Customer   : TextView
        internal var tv_Status   : TextView
        internal var tv_Branch   : TextView
        internal var tv_Mobile   : TextView
        internal var tv_Area   : TextView
        internal var tv_TimeDue   : TextView
        internal var llService       : LinearLayout
        init {
            tv_TktNo      = v.findViewById<View>(R.id.tv_TktNo) as TextView
            tv_TktDate    = v.findViewById<View>(R.id.tv_TktDate) as TextView
            tv_Customer     = v.findViewById<View>(R.id.tv_Customer) as TextView
            tv_Status     = v.findViewById<View>(R.id.tv_Status) as TextView
            tv_Branch     = v.findViewById<View>(R.id.tv_Branch) as TextView
            tv_Mobile     = v.findViewById<View>(R.id.tv_Mobile) as TextView
            tv_Area     = v.findViewById<View>(R.id.tv_Area) as TextView
            tv_TimeDue     = v.findViewById<View>(R.id.tv_TimeDue) as TextView
            llService         = v.findViewById<View>(R.id.llService) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}