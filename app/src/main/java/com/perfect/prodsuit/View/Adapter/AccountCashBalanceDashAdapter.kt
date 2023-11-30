package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class AccountCashBalanceDashAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "AccountCashBalanceDashAdapter"
    internal var jsonObject: JSONObject? = null

    val color = intArrayOf(
        R.color.color_dash1, R.color.color_dash2, R.color.color_dash3, R.color.leadstages_color4,
        R.color.leadstages_color5, R.color.leadstages_color6, R.color.leadstages_color7, R.color.leadstages_color8,
        R.color.leadstages_color9, R.color.leadstages_color10, R.color.leadstages_color11, R.color.leadstages_color12)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_account_cashbalance_dash, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {

                val pos = position+1
//                val randomColor = Config.getRandomColor()
//                holder.ll_main.setBackgroundColor(randomColor)

                if (position % 1 == 0){
                    holder.ll_main.setBackgroundResource(color[0])
                }
                if (position % 2 == 0){
                    holder.ll_main.setBackgroundResource(color[1])
                }
                if (position % 3 == 0){
                    holder.ll_main.setBackgroundResource(color[2])
                }
                holder.tv_label.text        = jsonObject!!.getString("BranchName")
                holder.tv_value.text        = Config.changeTwoDecimel(jsonObject!!.getString("CashBalance").toString())

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

        internal var ll_main          : LinearLayout
        internal var tv_label         : TextView
        internal var tv_value         : TextView
        init {
            ll_main            = v.findViewById<View>(R.id.ll_main) as LinearLayout
            tv_label           = v.findViewById<View>(R.id.tv_label) as TextView
            tv_value           = v.findViewById<View>(R.id.tv_value) as TextView

        }
    }
}