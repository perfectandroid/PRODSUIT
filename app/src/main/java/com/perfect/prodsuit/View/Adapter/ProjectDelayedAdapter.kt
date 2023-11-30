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

class ProjectDelayedAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "CostofMaterialUsageAllocatedandUsedAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_costofmaterialusage, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")
                Log.e(TAG, "jsonObject   1051   " + jsonObject)
                val pos = position + 1
                holder.tvv_slnmbr.text = pos.toString()
                holder.tvv_Project.text = jsonObject!!.getString("Project")
                holder.tvv_ActualPeriod.text = jsonObject!!.getString("ActualPeriod")
                holder.tvv_CurrentPeriod.text = jsonObject!!.getString("CurrentPeriod")

//                holder.tvv_Allocated!!.setTag(position)
//                holder.llCountry!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "countrydetail")
//                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
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
        internal var tvv_slnmbr: TextView
        internal var tvv_Project: TextView
        internal var tvv_ActualPeriod: TextView
        internal var tvv_CurrentPeriod: TextView

        init {
            tvv_slnmbr = v.findViewById<View>(R.id.tvv_slnmbr) as TextView
            tvv_Project = v.findViewById<View>(R.id.tvv_Project) as TextView
            tvv_ActualPeriod = v.findViewById<View>(R.id.tvv_ActualPeriod) as TextView
            tvv_CurrentPeriod = v.findViewById<View>(R.id.tvv_CurrentPeriod) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}