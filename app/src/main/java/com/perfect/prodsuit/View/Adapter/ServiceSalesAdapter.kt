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

class ServiceSalesAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "ServiceSalesAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_service_sales, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.tv_InvoiceNo.text        = jsonObject!!.getString("InvoiceNo")
                holder.tv_InvoiceDate.text      = jsonObject!!.getString("InvoiceDate")
                holder.tv_Product.text       = jsonObject!!.getString("Product")
                holder.tv_Quantity.text       = jsonObject!!.getString("Quantity")
                holder.tv_Dealer.text       = jsonObject!!.getString("Dealer")



                holder.llSales!!.setTag(position)
                holder.llSales!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "salesHistoryClick"
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
        internal var tv_InvoiceNo    : TextView
        internal var tv_InvoiceDate  : TextView
        internal var tv_Product   : TextView
        internal var tv_Quantity   : TextView
        internal var tv_Dealer   : TextView
        internal var llSales       : LinearLayout
        init {
            tv_InvoiceNo      = v.findViewById<View>(R.id.tv_InvoiceNo) as TextView
            tv_InvoiceDate    = v.findViewById<View>(R.id.tv_InvoiceDate) as TextView
            tv_Product     = v.findViewById<View>(R.id.tv_Product) as TextView
            tv_Quantity     = v.findViewById<View>(R.id.tv_Quantity) as TextView
            tv_Dealer     = v.findViewById<View>(R.id.tv_Dealer) as TextView
            llSales         = v.findViewById<View>(R.id.llSales) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}