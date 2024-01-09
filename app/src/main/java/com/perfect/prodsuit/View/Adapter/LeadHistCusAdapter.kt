package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class LeadHistCusAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "LeadHistCusAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.lead_hist_cus, parent, false
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


                holder.txtsino.text        = pos.toString()
                holder.txt_lead.text        = jsonObject!!.getString("LeadNo")
                holder.txt_cusName.text        = jsonObject!!.getString("CustomerName")
                holder.txt_cusMobile.text        = "("+jsonObject!!.getString("Mobile") +")"
                holder.llProduct!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "cus_wise_lead_hist"
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txt_lead   : TextView
        internal var txtsino         : TextView
        internal var txt_cusName         : TextView
        internal var txt_cusMobile         : TextView


        internal var llProduct    : LinearLayout
        init {
            txt_lead          = v.findViewById<View>(R.id.txt_lead) as TextView
            txtsino                = v.findViewById<View>(R.id.txtsino) as TextView
            txt_cusName                = v.findViewById<View>(R.id.txt_cusName) as TextView
            txt_cusMobile                = v.findViewById<View>(R.id.txt_cusMobile) as TextView

            llProduct           = v.findViewById<View>(R.id.llProduct) as LinearLayout

        }
    }
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}