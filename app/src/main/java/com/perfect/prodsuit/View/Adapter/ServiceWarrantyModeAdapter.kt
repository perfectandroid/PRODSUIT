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

class ServiceWarrantyModeAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceWarrantyModeAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_servicewarranty_mode, parent, false
        )
        vh = MainViewHolder(v)
        return vh

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   22222   ")
                Log.e(TAG,"warrantyservice   22222   "+jsonObject!!.getString("ServiceTypeName"))

                val pos = position+1
                holder.txtsino.text        = pos.toString()
                holder.tvv_serviceWarrantyMode.text        = jsonObject!!.getString("ServiceTypeName")

                holder.ll_serviceWarrantyMode!!.setTag(position)
                holder.ll_serviceWarrantyMode!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "warrantyservice"
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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tvv_serviceWarrantyMode     : TextView
        internal var txtsino                    : TextView
        internal var ll_serviceWarrantyMode      : LinearLayout
        init {
            tvv_serviceWarrantyMode          = v.findViewById<View>(R.id.tvv_serviceWarrantyMode) as TextView
            txtsino                         = v.findViewById<View>(R.id.txtsino) as TextView
            ll_serviceWarrantyMode           = v.findViewById<View>(R.id.ll_serviceWarrantyMode) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}