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
import java.text.DecimalFormat

class ComparisonNewAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var tvv_countValue=0.00
    internal val TAG : String = "ProductReorderLevelAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    val color = intArrayOf(
        R.color.color_dash1, R.color.color_dash2, R.color.color_dash3, R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7, R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11, R.color.leadstages_color12)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_comparison, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                Log.e(TAG,"onBindViewHolder   10516566   "+jsonArray.length())

                tvv_countValue=jsonObject!!.getString("Value").toDouble()
                //  tvv_countValue=1649785634.18888
                val forM= String.format("%2f",tvv_countValue)
                val deciForm= DecimalFormat("##0.00")
                val forMatNo=deciForm.format(tvv_countValue)

                val pos = position+1

//                if (pos % 1 == 0){
//                    holder.ll_main.setBackgroundResource(color[0])
//                }
//                if (pos % 2 == 0){
//                    holder.ll_main.setBackgroundResource(color[1])
//                }
//                if (pos % 3 == 0){
//                    holder.ll_main.setBackgroundResource(color[2])
//                }

                holder.tvv_slnmbr.text        = pos.toString()
                holder.label_comparison.text      = jsonObject!!.getString("Label")
             //   holder.lvalue_comparison.text  = jsonObject!!.getString("Value")
                holder.lvalue_comparison.text  = forMatNo.toString()




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

        internal var tvv_slnmbr             : TextView
        internal var label_comparison       : TextView
        internal var lvalue_comparison       : TextView
        internal var ll_main          : LinearLayout

        init {
            ll_main            = v.findViewById<View>(R.id.ll_main) as LinearLayout
            tvv_slnmbr             = v.findViewById<View>(R.id.tvv_slnmbr)       as TextView
            label_comparison       = v.findViewById<View>(R.id.label_comparison) as TextView
            lvalue_comparison       = v.findViewById<View>(R.id.lvalue_comparison) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}