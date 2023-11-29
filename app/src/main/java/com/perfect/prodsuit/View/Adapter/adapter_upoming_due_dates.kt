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

class adapter_upoming_due_dates (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "adapter_upoming_due_dates"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_product_reorder, parent, false
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
                holder.tvv_slnmbr.text        = pos.toString()
                holder.tvv_Project.text      = jsonObject!!.getString("Project")
                holder.tvv_Stages.text  = jsonObject!!.getString("Stages")
                holder.tvv_DueDate.text  = jsonObject!!.getString("DueDate")



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
        internal var tvv_Project           : TextView
        internal var tvv_Stages             : TextView
        internal var tvv_DueDate       : TextView
        internal var tvv_slnmbr       : TextView
        init {
            tvv_Project           = v.findViewById<View>(R.id.tvv_Project)     as TextView
            tvv_Stages             = v.findViewById<View>(R.id.tvv_Stages)       as TextView
            tvv_DueDate       = v.findViewById<View>(R.id.tvv_Reorderlevel) as TextView
            tvv_slnmbr       = v.findViewById<View>(R.id.tvv_slnmbr) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}