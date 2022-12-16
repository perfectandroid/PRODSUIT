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

class LeadByAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "LeadByAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_lead_by, parent, false
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
                holder.txtsino.text           = pos.toString()
                holder.txtName.text           =jsonObject!!.getString("Name")
                holder.txtDesignation.text    =jsonObject!!.getString("DesignationName")
//                if (position % 2 == 0){
//                    holder.llleadby!!.setBackgroundColor(context.getColor(R.color.greylight))
//                }
//                else{
//                    holder.llleadby!!.setBackgroundColor(context.getColor(R.color.white))
//                }
                holder.llleadby!!.setTag(position)
                holder.llleadby!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "leadby")
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
        internal var txtName          : TextView
        internal var txtDesignation          : TextView
        internal var txtsino          : TextView
        internal var llleadby    : LinearLayout
        init {
            txtName        = v.findViewById<View>(R.id.txtName) as TextView
            txtDesignation        = v.findViewById<View>(R.id.txtDesignation) as TextView
            txtsino        = v.findViewById<View>(R.id.txtsino) as TextView
            llleadby       = v.findViewById<View>(R.id.llleadby) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}