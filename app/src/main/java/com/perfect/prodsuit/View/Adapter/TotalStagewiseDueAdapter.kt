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

class TotalStagewiseDueAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "TotalStagewiseDueAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_total_stagewisedue, parent, false
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

                Log.e(TAG,"onBindViewHolder   1051   "+jsonObject)
                holder.tvv_slnmbr.text           = pos.toString()
                holder.tvv_Stages.text           = jsonObject!!.getString("Stages")
                holder.tvv_TotalCount.text       = jsonObject!!.getString("TotalCount")
                holder.tvv_TotalPercentage.text  = jsonObject!!.getString("TotalPercentage")


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
        internal var tvv_Stages                 : TextView
        internal var tvv_slnmbr                 : TextView
        internal var tvv_TotalCount             : TextView
        internal var tvv_TotalPercentage        : TextView
        init {
            tvv_Stages               = v.findViewById<View>(R.id.tvv_Stages)      as TextView
            tvv_slnmbr               = v.findViewById<View>(R.id.tvv_slnmbr)       as TextView
            tvv_TotalCount           = v.findViewById<View>(R.id.tvv_TotalCount)       as TextView
            tvv_TotalPercentage      = v.findViewById<View>(R.id.tvv_TotalPercentage)      as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}