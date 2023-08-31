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

class ProductStockTransferAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProductStockTransferAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_product_stock_trans, parent, false
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

                holder.txtProductStock.text        = jsonObject!!.getString("Name")
                holder.txtCurrStock.text        = "Cur Stock : "+jsonObject!!.getString("CurrentStock")
                holder.txtStandQty.text        = "Stnd Stock : "+jsonObject!!.getString("StandbyQuantity")

                holder.ll_ProductStock!!.setTag(position)
                holder.ll_ProductStock!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "productstock")
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
        internal var txtProductStock   : TextView
        internal var txtsino       : TextView
        internal var txtCurrStock       : TextView
        internal var txtStandQty       : TextView
        internal var ll_ProductStock    : LinearLayout
        init {
            txtProductStock          = v.findViewById<View>(R.id.txtProductStock) as TextView
            txtsino              = v.findViewById<View>(R.id.txtsino) as TextView
            txtCurrStock              = v.findViewById<View>(R.id.txtCurrStock) as TextView
            txtStandQty              = v.findViewById<View>(R.id.txtStandQty) as TextView
            ll_ProductStock           = v.findViewById<View>(R.id.ll_ProductStock) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}