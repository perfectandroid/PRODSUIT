package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class TotalStageWiseDueAdapterNew(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "MonthlyBarChartAdapter"
    internal var jsonObject: JSONObject? = null

    val color = intArrayOf(R.color.barchart_colors1, R.color.barchart_colors2, R.color.barchart_colors3,R.color.barchart_colors4,
        R.color.barchart_colors5, R.color.barchart_colors6, R.color.barchart_colors7,R.color.barchart_colors8,
        R.color.barchart_colors9, R.color.barchart_colors10, R.color.barchart_colors11,R.color.barchart_colors12,

        R.color.barchart_colors13,
        R.color.barchart_colors14,
        R.color.barchart_colors15,
        R.color.barchart_colors16,
        R.color.barchart_colors17,
        R.color.barchart_colors18,
        R.color.barchart_colors19,
        R.color.barchart_colors20,
        R.color.barchart_colors21,
        R.color.barchart_colors22,
        R.color.barchart_colors23,
        R.color.barchart_colors24

    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_monthly_bar_chart, parent, false
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
                holder.tv_month.text        = jsonObject!!.getString("Stages")
                holder.tv_amount.text        = jsonObject!!.getString("TotalCount")


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
        internal var tv_month         : TextView
        internal var tv_amount         : TextView
        init {
            tv_colorBox        = v.findViewById<View>(R.id.tv_colorBox) as TextView
            tv_month       = v.findViewById<View>(R.id.tv_month) as TextView
            tv_amount       = v.findViewById<View>(R.id.tv_amount) as TextView

        }
    }
}