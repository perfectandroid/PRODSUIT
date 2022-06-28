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

                holder.tv_Sale_InvNo.text        = jsonObject!!.getString("InvoiceNo")
                holder.tv_Sale_InvDate.text      = jsonObject!!.getString("InvoiceDate")
                holder.tv_Sale_dealer.text       = jsonObject!!.getString("Dealer")
                holder.tv_Sale_ProdQty.text        = jsonObject!!.getString("Product")+"\n"+" / "+jsonObject!!.getString("Quatity")

//                holder.llfollowuptype!!.setTag(position)
//                holder.llfollowuptype!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "followuptype")
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tv_Sale_InvNo    : TextView
        internal var tv_Sale_InvDate  : TextView
        internal var tv_Sale_dealer   : TextView
        internal var tv_Sale_ProdQty   : TextView
        internal var llSales       : LinearLayout
        init {
            tv_Sale_InvNo      = v.findViewById<View>(R.id.tv_Sale_InvNo) as TextView
            tv_Sale_InvDate    = v.findViewById<View>(R.id.tv_Sale_InvDate) as TextView
            tv_Sale_dealer     = v.findViewById<View>(R.id.tv_Sale_dealer) as TextView
            tv_Sale_ProdQty     = v.findViewById<View>(R.id.tv_Sale_ProdQty) as TextView
            llSales         = v.findViewById<View>(R.id.llSales) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}