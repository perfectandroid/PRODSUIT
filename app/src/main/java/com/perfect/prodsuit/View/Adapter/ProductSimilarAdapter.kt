package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Paint
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

class ProductSimilarAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProductSimilarAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_product_similar, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   105100   ")
                val pos = position+1

                holder.txtProdct.text        = jsonObject!!.getString("ProductName")
                holder.txtProdct_mrp.text        = "₹ "+jsonObject!!.getString("MRP")
                holder.txtProdct_sales.text        = "₹ "+jsonObject!!.getString("SalPrice")
                holder.txtProdct_mrp!!.setPaintFlags(holder.txtProdct_mrp!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


//                holder.ll_product_list!!.setTag(position)
//                holder.ll_product_list!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "productEnquiryList")
//                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105100   "+e.toString())
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
        internal var txtProdct          : TextView
        internal var txtProdct_mrp          : TextView
        internal var txtProdct_sales          : TextView
        internal var ll_product_list    : LinearLayout
        init {
            txtProdct        = v.findViewById<View>(R.id.txtProdct) as TextView
            txtProdct_mrp        = v.findViewById<View>(R.id.txtProdct_mrp) as TextView
            txtProdct_sales        = v.findViewById<View>(R.id.txtProdct_sales) as TextView
            ll_product_list        = v.findViewById<View>(R.id.ll_product_list) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}