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

class ServiceWarrantyAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "ServiceWarrantyAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_warranty, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is  MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.tv_warr_amc_type.text        = jsonObject!!.getString("WarrantyType")
                holder.tv_warr_amc_duedate.text      = jsonObject!!.getString("ReplacementWarrantyExpireDate")
                holder.tv_Status.text       = jsonObject!!.getString("ReplacementWarrantyStatus")
                holder.tv_warr_amc_renewDate.text       = jsonObject!!.getString("ServiceWarrantyExpireDate")
                holder.tv_service_status.text       = jsonObject!!.getString("ServiceWarrantyStatus")

//                if (position%2 != 0){
//                    holder.llWarranty.setBackgroundColor(context.getColor(R.color.alternate_color))
//                }


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
        internal var tv_warr_amc_type    : TextView
        internal var tv_warr_amc_duedate  : TextView
        internal var tv_Status   : TextView
        internal var tv_warr_amc_renewDate   : TextView
        internal var tv_service_status   : TextView
        internal var llWarranty       : LinearLayout
        init {
            tv_warr_amc_type      = v.findViewById<View>(R.id.tv_warr_amc_type) as TextView
            tv_warr_amc_duedate    = v.findViewById<View>(R.id.tv_warr_amc_duedate) as TextView
            tv_Status     = v.findViewById<View>(R.id.tv_Status) as TextView
            tv_warr_amc_renewDate     = v.findViewById<View>(R.id.tv_warr_amc_renewDate) as TextView
            tv_service_status     = v.findViewById<View>(R.id.tv_service_status) as TextView
            llWarranty         = v.findViewById<View>(R.id.llWarranty) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}