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

class ProductPriorityAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProductPriorityAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_product_priority, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   111224455   "+position)
                val pos = position+1
                holder.txtsino.text        = pos.toString()
                holder.txtpriority.text        = jsonObject!!.getString("PriorityName")
//                if (position % 2 == 0){
//                    holder.llprodpriority!!.setBackgroundColor(context.getColor(R.color.greylight))
//                }
//                else{
//                    holder.llprodpriority!!.setBackgroundColor(context.getColor(R.color.white))
//                }
                holder.llprodpriority!!.setTag(position)
                holder.llprodpriority!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "prodpriority")
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
        internal var txtpriority       : TextView
        internal var txtsino           : TextView
        internal var llprodpriority    : LinearLayout
        init {
            txtpriority        = v.findViewById<View>(R.id.txtpriority) as TextView
            txtsino            = v.findViewById<View>(R.id.txtsino) as TextView
            llprodpriority     = v.findViewById<View>(R.id.llprodpriority) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}