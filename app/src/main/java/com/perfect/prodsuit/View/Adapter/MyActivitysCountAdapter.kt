package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class MyActivitysCountAdapter (internal var context: Context, internal var jsonArray: JSONArray, internal var myActivityCountMode: Int):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "MyActivitysCountAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_activity_count, parent, false)
        vh = MainViewHolder(v)
        return vh
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")

                holder.tv_activitycount.text        = jsonObject!!.getString("Count")
                holder.tv_StatusName.text        = jsonObject!!.getString("Name")

                if (position == 0){
                    holder.tv_activitycount.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.completed_color))
                    holder.img_activitycount.setImageResource(R.drawable.sma_todays)

                }

                if (position == 1){
                    holder.tv_activitycount.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.pending_color))
                    holder.img_activitycount.setImageResource(R.drawable.sma_pending)
                }

                if (position == 2){
                    holder.tv_activitycount.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.upcoming_color))
                    holder.img_activitycount.setImageResource(R.drawable.sma_upcoming)
                }

                if (position == 3){
                    holder.tv_activitycount.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.today_color))
                    holder.img_activitycount.setImageResource(R.drawable.sma_completed)
                }

                if (myActivityCountMode == position){
                    if (position == 0){
                        holder.tv_StatusName.setTextColor(ContextCompat.getColor(context, R.color.completed_color))
                    }

                    if (position == 1){
                        holder.tv_StatusName.setTextColor(ContextCompat.getColor(context, R.color.pending_color))
                    }

                    if (position == 2){
                        holder.tv_StatusName.setTextColor(ContextCompat.getColor(context, R.color.upcoming_color))
                    }

                    if (position == 3){
                        holder.tv_StatusName.setTextColor(ContextCompat.getColor(context, R.color.today_color))
                    }
                }else{
                    holder.tv_StatusName.setTextColor(ContextCompat.getColor(context, R.color.black))
                }


                holder.rl_activitycount!!.setTag(position)
                holder.rl_activitycount!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    myActivityCountMode = position
                    notifyDataSetChanged()
                    clickListener!!.onClick(
                        position,
                        "activitycountClick"
                    )
                })


            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
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
        internal var rl_activitycount   : RelativeLayout
        internal var tv_StatusName       : TextView
        internal var tv_activitycount       : TextView
        internal var img_activitycount    : ImageView

        init {
            rl_activitycount          = v.findViewById<View>(R.id.rl_activitycount) as RelativeLayout
            tv_StatusName          = v.findViewById<View>(R.id.tv_StatusName) as TextView
            tv_activitycount          = v.findViewById<View>(R.id.tv_activitycount) as TextView
            img_activitycount           = v.findViewById<View>(R.id.img_activitycount) as ImageView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}