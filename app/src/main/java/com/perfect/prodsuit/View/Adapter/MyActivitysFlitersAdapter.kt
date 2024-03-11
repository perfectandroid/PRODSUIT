package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class MyActivitysFlitersAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "MyActivitysFlitersAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_activity_filters, parent, false)
        vh = MainViewHolder(v)
        return vh
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")

                holder.tv_daily.text        = jsonObject!!.getString("FliterType")




                holder.ll_filters!!.setTag(position)
                holder.ll_filters!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(position, "activityfiltersClick")
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
        internal var ll_filters   : LinearLayout
        internal var tv_daily       : TextView

        init {
            ll_filters          = v.findViewById<View>(R.id.ll_filters) as LinearLayout
            tv_daily          = v.findViewById<View>(R.id.tv_daily) as TextView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}