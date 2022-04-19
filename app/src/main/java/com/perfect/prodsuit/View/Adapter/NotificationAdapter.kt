package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat

class NotificationAdapter (internal var context: Context, internal var jsonArray: JSONArray):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "NotificationAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_notification, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)

            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")
                Log.i("Notfctn Date", jsonObject!!.getString("NoticeDate"))

                var date = jsonObject!!.getString("NoticeDate")

                Log.i("Notfctn Date1", date)


                holder.txtv_date1.text        = date
                holder.txtv_status.text        = jsonObject!!.getString("readStatus")
                holder.tv_msg.text        = jsonObject!!.getString("NotificationMessage")




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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var txtv_date1   : TextView
        internal var txtv_status       : TextView
        internal var tv_msg    : TextView


        init {
            txtv_date1          = v.findViewById<View>(R.id.txtv_dte1) as TextView
            txtv_status              = v.findViewById<View>(R.id.txtv_status) as TextView
            tv_msg           = v.findViewById<View>(R.id.tv_msg) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}