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
import java.util.ArrayList

class LeadSourseChartAdapter(
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
            R.layout.adapter_lead_sourse_chart, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {

                Log.v("sdfsdfdsdfsdfdd","colo  "+colorgroup.get(position))
                holder.tv_colorBox.setBackgroundColor(colorgroup.get(position))
                holder.tv_label.text        = jsonObject!!.getString("LeadFrom")
                holder.tv_value.text        = jsonObject!!.getString("TotalCount").toFloat().toInt().toString()+" ("+jsonObject!!.getString("TotalPercentage")+"%)"

//                if (position == 0){
//                    holder.tv_label.text        = "Service Ticket Action-, Date Criteria"
//                }

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
        internal var tv_colorBox      : TextView
        internal var tv_label         : TextView
        internal var tv_value         : TextView
        init {
            tv_colorBox        = v.findViewById<View>(R.id.tv_colorBox) as TextView
            tv_label           = v.findViewById<View>(R.id.tv_label) as TextView
            tv_value           = v.findViewById<View>(R.id.tv_value) as TextView

        }
    }

}