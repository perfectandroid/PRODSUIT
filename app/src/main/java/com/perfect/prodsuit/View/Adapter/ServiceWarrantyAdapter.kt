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

                holder.tv_warr_invNo.text        = jsonObject!!.getString("InvoiceNo")
                holder.tv_warr_invdate.text      = jsonObject!!.getString("InvoiceDate")
                holder.tv_warr_dealer.text       = jsonObject!!.getString("Dealer")

                if (position%2 != 0){
                    holder.llWarranty.setBackgroundColor(context.getColor(R.color.alternate_color))
                }


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
        internal var tv_warr_invNo    : TextView
        internal var tv_warr_invdate  : TextView
        internal var tv_warr_dealer   : TextView
        internal var llWarranty       : LinearLayout
        init {
            tv_warr_invNo      = v.findViewById<View>(R.id.tv_warr_invNo) as TextView
            tv_warr_invdate    = v.findViewById<View>(R.id.tv_warr_invdate) as TextView
            tv_warr_dealer     = v.findViewById<View>(R.id.tv_warr_dealer) as TextView
            llWarranty         = v.findViewById<View>(R.id.llWarranty) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}