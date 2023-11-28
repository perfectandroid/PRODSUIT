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

class EmployeewiseAmountAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "EmployeewiseAmountAdapter"
    internal var jsonObject: JSONObject? = null

    val color = intArrayOf(
        R.color.leadstages_color1, R.color.leadstages_color2, R.color.leadstages_color3, R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7, R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11, R.color.leadstages_color12,R.color.leadstages_color10,
        R.color.mylead_Color, R.color.barchart_colors24,R.color.barchart_colors10,R.color.barchart_colors21,R.color.barchart_colors23,R.color.barchart_colors3,R.color.barchart_colors12,R.color.barchart_colors24,R.color.mylead_light_Color,R.color.mylead_light_Color1,R.color.barchart_colors28,R.color.barchart_colors18,
        R.color.common_google_signin_btn_text_dark_focused,R.color.defaultColor,R.color.colorPrimary,R.color.color_approve,
        R.color.fui_bgEmail,R.color.service_sf_main,R.color.zxing_viewfinder_mask,R.color.hand_color,
        R.color.report_head2,R.color.fui_linkColor,R.color.emi_overdue_gardient1,R.color.emi_todo_gardient2


    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_empwiseamt, parent, false
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
                holder.tv_label.text        = jsonObject!!.getString("EmpFName")
                holder.tv_value.text        = jsonObject!!.getString("TargetAmount").toFloat().toInt().toString()

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