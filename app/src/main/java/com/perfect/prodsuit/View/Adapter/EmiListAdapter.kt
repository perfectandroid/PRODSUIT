package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class EmiListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "EmiListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_emi_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1



//                holder.tv_TicketNo.text        = jsonObject!!.getString("TicketNo")
//                holder.tv_TicketDate.text        = jsonObject!!.getString("TicketDate")
//                holder.tv_Branch.text        = jsonObject!!.getString("Branch")
//                holder.tv_Customer.text        = jsonObject!!.getString("Customer")
//                holder.tv_Mobile.text        = jsonObject!!.getString("Mobile")
//                holder.tv_Area.text        = jsonObject!!.getString("Area")
//                holder.tv_Employee.text        = jsonObject!!.getString("Employee")
//                holder.tv_TimeDue.text        = jsonObject!!.getString("Due")
//
//                if (jsonObject!!.getString("Channel").equals("Portal")){
//                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_portal))
//                }
//                if (jsonObject!!.getString("Channel").equals("Direct")){
//                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_direct))
//                }
//                if (jsonObject!!.getString("Channel").equals("Call")){
//                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_call))
//                }
//                if (jsonObject!!.getString("Channel").equals("Email")){
//                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_email))
//                }
//                if (jsonObject!!.getString("Channel").equals("Employee")){
//                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_employee))
//                }
//                if (jsonObject!!.getString("Channel").equals("Media")){
//                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_media))
//                }
//
//                if (jsonObject!!.getString("PriorityCode").equals("1")){
//                    holder.im_Priority.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_high))
//                }
//                if (jsonObject!!.getString("PriorityCode").equals("2")){
//                    holder.im_Priority.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_medium))
//                }
//                if (jsonObject!!.getString("PriorityCode").equals("3")){
//                    holder.im_Priority.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_low))
//                }
//
//                if (SubMode.equals("3")){
//                    holder.ll_status!!.visibility = View.VISIBLE
//                    holder.ll_employee!!.visibility = View.VISIBLE
//                    if (jsonObject!!.getString("Status").equals("On-Hold")){
//                        holder.im_Status.setImageDrawable(context.resources.getDrawable(R.drawable.svg_stat_hold))
//                    }
//                    if (jsonObject!!.getString("Status").equals("Pending")){
//                        holder.im_Status.setImageDrawable(context.resources.getDrawable(R.drawable.svg_stat_pending))
//                    }
//
//                }
//
//                holder.im_edit!!.setTag(position)
//                holder.im_edit!!.setOnClickListener(View.OnClickListener {
//                    Config.disableClick(it)
//                    clickListener!!.onClick(position, "ServiceEdit")
//                })
//
                holder.llEmiList!!.setTag(position)
                holder.llEmiList!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "EmiList")
                })
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
//        internal var tv_TicketNo          : TextView
//        internal var tv_TicketDate          : TextView
//        internal var tv_Branch          : TextView
//        internal var tv_Customer          : TextView
//        internal var tv_Mobile          : TextView
//        internal var tv_Area          : TextView
//        internal var tv_Employee          : TextView
//        internal var tv_TimeDue          : TextView
//        internal var im_Channel          : ImageView
//        internal var im_Priority          : ImageView
//        internal var im_Status          : ImageView
//        internal var im_edit          : ImageView
//        //        internal var txtsino          : TextView
//        internal var ll_employee    : LinearLayout
//        internal var ll_status    : LinearLayout
        internal var llEmiList    : LinearLayout
        init {
//            tv_TicketNo        = v.findViewById<View>(R.id.tv_TicketNo) as TextView
//            tv_TicketDate        = v.findViewById<View>(R.id.tv_TicketDate) as TextView
//            tv_Branch        = v.findViewById<View>(R.id.tv_Branch) as TextView
//            tv_Customer        = v.findViewById<View>(R.id.tv_Customer) as TextView
//            tv_Mobile        = v.findViewById<View>(R.id.tv_Mobile) as TextView
//            tv_Area        = v.findViewById<View>(R.id.tv_Area) as TextView
//            tv_Employee        = v.findViewById<View>(R.id.tv_Employee) as TextView
//            tv_TimeDue        = v.findViewById<View>(R.id.tv_TimeDue) as TextView
//
//            im_Channel        = v.findViewById<View>(R.id.im_Channel) as ImageView
//            im_Priority        = v.findViewById<View>(R.id.im_Priority) as ImageView
//            im_Status        = v.findViewById<View>(R.id.im_Status) as ImageView
//            im_edit        = v.findViewById<View>(R.id.im_edit) as ImageView
////            txtsino        = v.findViewById<View>(R.id.txtsino) as TextView
//            ll_employee       = v.findViewById<View>(R.id.ll_employee) as LinearLayout
//            ll_status       = v.findViewById<View>(R.id.ll_status) as LinearLayout
            llEmiList       = v.findViewById<View>(R.id.llEmiList) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}