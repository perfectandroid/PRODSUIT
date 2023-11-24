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

class SupplierWisePurchaseAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "SupplierWisePurchaseAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

//    val color = intArrayOf(R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3,R.color.leadstages_color4,
//        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7,R.color.leadstages_color8,
//        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11,R.color.leadstages_color12
//
//    )

    val color = intArrayOf(R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3,R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7,R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11,R.color.leadstages_color12,R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3,R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7,R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11,R.color.leadstages_color12,R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3,R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7,R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11,R.color.leadstages_color12,R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3,R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7,R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11,R.color.leadstages_color12,R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3,R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7,R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11,R.color.leadstages_color12,R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3,R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7,R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11,R.color.leadstages_color12,R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3,R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7,R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11,R.color.leadstages_color12,

    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_supplierwise_purchase, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {

                val pos = position+1
                holder.tv_colorBox.setBackgroundResource(color[position])
                holder.tv_SuppName.text        = jsonObject!!.getString("SuppName").take(3)
                holder.tv_amount.text        = jsonObject!!.getString("Amount").toFloat().toInt().toString()


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
        internal var tv_colorBox          : TextView
        internal var tv_SuppName         : TextView
        internal var tv_amount         : TextView
        init {
            tv_colorBox        = v.findViewById<View>(R.id.tv_colorBox) as TextView
            tv_SuppName       = v.findViewById<View>(R.id.tv_SuppName) as TextView
            tv_amount       = v.findViewById<View>(R.id.tv_amount) as TextView

        }
    }

}