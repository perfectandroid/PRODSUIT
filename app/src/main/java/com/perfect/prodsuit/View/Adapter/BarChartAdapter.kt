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

class BarChartAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "LineChartAdapter"
    internal var jsonObject: JSONObject? = null

    val color = intArrayOf(R.color.leadstatus_color1, R.color.leadstatus_color2, R.color.leadstatus_color3,R.color.leadstatus_color4,R.color.leadstatus_color5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_bar_chart, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   "+jsonObject!!.getString("StatusName"))
                val pos = position+1

                holder.tv_BarName.text        = jsonObject!!.getString("StatusName")
                holder.tv_BarName.text        = jsonObject!!.getString("Percentage "+"%"+"("+jsonObject!!.getString("Amount")+")")
                if(!holder.tv_BarName.text.equals(""))
                {
                    holder.tv_BarBox.setBackgroundResource(color[position])
                }


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
        internal var tv_BarBox          : TextView
        internal var tv_BarName         : TextView
        internal var tv_BarValue         : TextView

        init {
            tv_BarBox        = v.findViewById<View>(R.id.tv_BarBox) as TextView
            tv_BarName       = v.findViewById<View>(R.id.tv_BarName) as TextView
            tv_BarValue       = v.findViewById<View>(R.id.tv_BarValue) as TextView

        }
    }

}