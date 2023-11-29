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

class SalesComparisonBarChartAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "SalesComparisonBarChartAdapter"
    internal var jsonObject: JSONObject? = null

    val color = intArrayOf(R.color.barchart_colors1,R.color.barchart_colors2
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_salescomparison_bar_chart, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            Log.e(TAG,"65457  jsonObject   "+jsonObject)
            if (holder is MainViewHolder) {

                val pos = position+1
                holder.tv_colorBox.setBackgroundResource(color[position])
                holder.tv_Label.text        = jsonObject!!.getString("Label")
                holder.tv_Value.text        = jsonObject!!.getString("Value").toFloat().toInt().toString()


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
        internal var tv_Label         : TextView
        internal var tv_Value         : TextView
        init {
            tv_colorBox        = v.findViewById<View>(R.id.tv_colorBox) as TextView
            tv_Label       = v.findViewById<View>(R.id.tv_Label) as TextView
            tv_Value       = v.findViewById<View>(R.id.tv_Value) as TextView

        }
    }
}