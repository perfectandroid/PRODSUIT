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

class ProductReorderLevelAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProductReorderLevelAdapter"
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
                holder.tvv_product1.text      = jsonObject!!.getString("ProdName")
                holder.tvv_Reorderlevel.text  = jsonObject!!.getString("ReorderLevel")
                holder.tvv_CurrentStock.text  = jsonObject!!.getString("CurrentQuantity")


//                holder.txtProductStock.text        = jsonObject!!.getString("Name")
//                holder.txtCurrStock.text        = "Cur Stock : "+jsonObject!!.getString("CurrentStock")
//                holder.txtStandQty.text        = "Stnd Stock : "+jsonObject!!.getString("StandbyQuantity")
//
//                holder.ll_ProductStock!!.setTag(position)
//                holder.ll_ProductStock!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(
//                        position,
//                        "productstock"
//                    )
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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tvv_product1           : TextView
        internal var tvv_slnmbr             : TextView
        internal var tvv_Reorderlevel       : TextView
        internal var tvv_CurrentStock       : TextView
        init {
            tvv_product1           = v.findViewById<View>(R.id.tvv_product1)     as TextView
            tvv_slnmbr             = v.findViewById<View>(R.id.tvv_slnmbr)       as TextView
            tvv_Reorderlevel       = v.findViewById<View>(R.id.tvv_Reorderlevel) as TextView
            tvv_CurrentStock       = v.findViewById<View>(R.id.tvv_CurrentStock) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}