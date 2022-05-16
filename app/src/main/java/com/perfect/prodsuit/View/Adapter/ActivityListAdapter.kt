package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ActivityListAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "activitylistAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_activity_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   36   ")
                val pos = position+1
                holder.ll_Call.visibility = View.VISIBLE
                holder.txtDate.text        = jsonObject!!.getString("Date")
                holder.txtFollowup.text        = jsonObject!!.getString("FollowUpBy")
                holder.txtv_custmrremrk.text        = "Product Name : "+jsonObject!!.getString("CustomerRemark")
                holder.txtStatus.text        = jsonObject!!.getString("Status")

             /*   holder.imMeeting_Location.setTag(position)
                holder.imMeeting_Location.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "agendaLocation")
                })

                holder.imMessage_Icon.setTag(position)
                holder.imMessage_Icon.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "agendaMessage")
                })
                holder.im_Call_Icon.setTag(position)
                holder.im_Call_Icon.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "agendaCall")
                })

*/

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }



    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtDate      : TextView
        internal var ll_Call      : LinearLayout
        internal var txtFollowup       : TextView
        internal var txtv_custmrremrk       : TextView
        internal var txtv_empremrk       : TextView
        internal var txtStatus       : TextView
        internal var imgv_Icon       : ImageView

        init {
            txtDate        = v.findViewById<View>(R.id.txtv_Date) as TextView
            ll_Call        = v.findViewById<View>(R.id.ll_Call) as LinearLayout
            txtFollowup         = v.findViewById<View>(R.id.txtv_Followup) as TextView
            txtv_custmrremrk         = v.findViewById<View>(R.id.txtv_custmrremrk) as TextView
            txtv_empremrk         = v.findViewById<View>(R.id.txtv_empremrk) as TextView
            txtStatus         = v.findViewById<View>(R.id.txtv_Status) as TextView
            imgv_Icon  = v.findViewById<View>(R.id.imgv_Icon) as ImageView
        }
    }

    override fun getItemCount(): Int {
        return  jsonArray.length()
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}