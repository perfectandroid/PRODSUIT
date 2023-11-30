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

class MaterialShortageAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "MaterialShortageAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_material_shortage, parent, false
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
                holder.tvv_slnmbr.text        = pos.toString()
                holder.tvv_Product.text      = jsonObject!!.getString("Product")
                holder.tvv_ActualQuantity.text  = jsonObject!!.getString("ActualQuantity").toFloat().toString()
                holder.tvv_ShortageQuantity.text  = jsonObject!!.getString("ShortageQuantity").toFloat().toString()


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
        internal var tvv_Product           : TextView
        internal var tvv_slnmbr             : TextView
        internal var tvv_ActualQuantity       : TextView
        internal var tvv_ShortageQuantity       : TextView
        init {
            tvv_Product           = v.findViewById<View>(R.id.tvv_Product)     as TextView
            tvv_slnmbr             = v.findViewById<View>(R.id.tvv_slnmbr)       as TextView
            tvv_ActualQuantity       = v.findViewById<View>(R.id.tvv_ActualQuantity) as TextView
            tvv_ShortageQuantity       = v.findViewById<View>(R.id.tvv_ShortageQuantity) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}