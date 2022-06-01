package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.NotificationReadStatusViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class NotificationAdapter (internal var context: Context, internal var jsonArray: JSONArray ):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext: Context? = null
    internal val TAG : String = "NotificationAdapter"
    internal var jsonObject: JSONObject? = null
    internal var jsonObject1: JSONObject? = null
    internal var id= ""

    private var clickListener: ItemClickListener? = null
    lateinit var notifreadstatusmodel: NotificationReadStatusViewModel



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_notification, parent, false
        )
        vh = MainViewHolder(v)

        return vh
    }
  /*  companion object {


    }*/
    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        try {
            jsonObject = jsonArray.getJSONObject(position)

            if (holder is MainViewHolder) {

                Log.e(TAG, "onBindViewHolder   1051   ")
                Log.i("Notfctn Date", jsonObject!!.getString("NoticeDate"))

                var date = jsonObject!!.getString("NoticeDate")

                Log.i("Notfctn Date1", date)


                holder.txtv_date1.text        = date
                holder.tv_msg.text        = jsonObject!!.getString("NotificationMessage")



                holder.llnotf!!.setTag(position)
                holder.llnotf.setOnClickListener(View.OnClickListener
                {
                    try {
                       /* jsonObject1 = jsonArray.getJSONObject(position)
                         id =jsonObject1!!.getString("ID_NotificationDetails")*/
                        clickListener!!.onClick(position, "readstatus")



                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.e(TAG, "Exceptions   " + e.toString())
                    }
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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {



        internal var txtv_date1   : TextView
        internal var tv_msg    : TextView
        internal var llnotf   : LinearLayout


        init {
            txtv_date1          = v.findViewById<View>(R.id.txtv_dte1) as TextView
            tv_msg           = v.findViewById<View>(R.id.tv_msg) as TextView
            llnotf           = v.findViewById<View>(R.id.ll_notif) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener

    }

}





