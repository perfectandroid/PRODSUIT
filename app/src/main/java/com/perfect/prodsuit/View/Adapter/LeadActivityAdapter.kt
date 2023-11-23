package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class LeadActivityAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    internal var colorgroup: ArrayList<Int>
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "CrmStageChartAdapter"
    internal var jsonObject: JSONObject? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_lead_activity_chart, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
//                holder.tv_colorBox.setBackgroundColor(colorgroup.get(position))
                holder.tv_label.text        = jsonObject!!.getString("ActionName")+"("+jsonObject!!.getString("Closed")+"/"+jsonObject!!.getString("Total")+")"
                holder.back.weightSum=jsonObject!!.getString("Total").toString().toFloat()
//                holder.back.weightSum=5f
                val progressBarParams = LinearLayout.LayoutParams(
                    0,  // width
                    LinearLayout.LayoutParams.MATCH_PARENT,  // height
                    jsonObject!!.getString("Closed").toString().toFloat() // weight
                )
                holder.front.setLayoutParams(progressBarParams)
                val color = Color.parseColor("#FF5733") // Example color (orange)

                holder.front.setBackgroundTintList(ColorStateList.valueOf(colorgroup.get(position)))
//                holder.front.setBackgroundTintList(ColorStateList.valueOf(color))

            }
        } catch (e: Exception) {
            e.printStackTrace()
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
        internal var tv_label         : TextView
        internal var front         : LinearLayout
        internal var back         : LinearLayout
        init {
            front        = v.findViewById<View>(R.id.front) as LinearLayout
            tv_label           = v.findViewById<View>(R.id.tv_label) as TextView
            back           = v.findViewById<View>(R.id.back) as LinearLayout

        }
    }

}