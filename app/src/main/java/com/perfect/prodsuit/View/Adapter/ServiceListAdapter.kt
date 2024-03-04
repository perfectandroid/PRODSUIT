package com.perfect.prodsuit.View.Adapter

import android.animation.AnimatorInflater
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject


class ServiceListAdapter (internal var context: Context, internal var jsonArray: JSONArray,internal var SubMode :String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)

            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   idcustomerservice and    "+jsonArray.getJSONObject(position).toString())
                val pos = position+1

//                if (position % 2 == 0){
//                    holder.llServiceList.setBackgroundDrawable(context.resources.getDrawable(R.drawable.svg_list_1))
//                }
//                else{
//                    holder.llServiceList.setBackgroundDrawable(context.resources.getDrawable(R.drawable.svg_list_2))
//                }


                holder.tv_TicketNo.text        = jsonObject!!.getString("TicketNo")
                holder.tv_TicketDate.text        = jsonObject!!.getString("TicketDate")
                holder.tv_Branch.text        = jsonObject!!.getString("Branch")
                holder.tv_Customer.text        = jsonObject!!.getString("Customer")
                holder.tv_Mobile.text        = jsonObject!!.getString("Mobile")
                holder.tv_Area.text        = jsonObject!!.getString("Area")
                holder.tv_Employee.text        = jsonObject!!.getString("Employee")
                holder.tv_TimeDue.text        = "Time Due : "+jsonObject!!.getString("Due")


                val FK_IDCustserviceregist =
                    context.getSharedPreferences(Config.SHARED_PREF72, 0)
                val FK_idcustsrvceregistEditer = FK_IDCustserviceregist.edit()
                FK_idcustsrvceregistEditer.putString(
                    "idcustsrvceregist",
                    jsonObject!!.getString("ID_CustomerServiceRegister")
                )
                FK_idcustsrvceregistEditer.commit()


                val FK_idcustsrvceregistproductdetail =
                    context.getSharedPreferences(Config.SHARED_PREF73, 0)
                val FK_idcustsrvceregistproductdetailEditer = FK_idcustsrvceregistproductdetail.edit()
                FK_idcustsrvceregistproductdetailEditer.putString(
                    "idcustsrvceregistproductdetail",
                    jsonObject!!.getString("ID_CustomerServiceRegisterProductDetails")
                )
                FK_idcustsrvceregistproductdetailEditer.commit()
                if (jsonObject!!.getString("Channel").equals(""))
                {
                    holder.tv_Channel.text        = "No Channel"
                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.no_channel_one))
                }
                else
                {
                    holder.tv_Channel.text        = jsonObject!!.getString("Channel")
                }
           //     holder.tv_Channel.text        = jsonObject!!.getString("Channel")
                if (jsonObject!!.getString("Channel").equals("Portal")){
                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_portal1))
                }
                if (jsonObject!!.getString("Channel").equals("Direct")){
                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_direct1))
                }
                if (jsonObject!!.getString("Channel").equals("Call")){
                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_call))
                }
                if (jsonObject!!.getString("Channel").equals("Email")){
                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_email1))
                }
                if (jsonObject!!.getString("Channel").equals("Employee")){
                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_employee))
                }
                if (jsonObject!!.getString("Channel").equals("Media")){
                    holder.im_Channel.setImageDrawable(context.resources.getDrawable(R.drawable.svg_ch_media1))
                }

                holder.tv_Priority!!.setText(jsonObject!!.getString("Priority"))
                if (jsonObject!!.getString("PriorityCode").equals("1")){
                    holder.im_Priority.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_high))
                }
                if (jsonObject!!.getString("PriorityCode").equals("2")){
                    holder.im_Priority.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_medium))
                }
                if (jsonObject!!.getString("PriorityCode").equals("3")){
                    holder.im_Priority.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_low))
                }


                if (SubMode.equals("3")){
                    holder.ll_status!!.visibility = View.VISIBLE
                    holder.ll_employee!!.visibility = View.VISIBLE
                    holder.ll_tracker!!.visibility = View.VISIBLE
                    Log.e(TAG,"1117772   "+SubMode  +"  : "+jsonObject!!.getString("Status"))
                    if (jsonObject!!.getString("Status").equals("On-Hold")){
                        holder.im_Status.setImageDrawable(context.resources.getDrawable(R.drawable.svg_stat_hold))
                    }
                    else if (jsonObject!!.getString("Status").equals("Pending")){
                        holder.im_Status.setImageDrawable(context.resources.getDrawable(R.drawable.svg_stat_pending))
                    }
                    else if (jsonObject!!.getString("Status").equals("Completed")){

                        holder.im_Status.setImageDrawable(context.resources.getDrawable(R.drawable.ic_completed))
                    }else{
                        Log.e(TAG,"1117773   "+SubMode  +"  : "+jsonObject!!.getString("Status"))
                    }

                }

                holder.im_edit!!.setTag(position)
                holder.im_edit!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(
                        position,
                        "ServiceEdit"
                    )
                })

                holder.im_tracker!!.setTag(position)
                holder.im_tracker!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(
                        position,
                        "ServiceTracker"
                    )
                })



                holder.llServiceList!!.setTag(position)
                holder.llServiceList!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(
                        position,
                        "ServiceList"
                    )
                })

                holder.ll_ticketNumber!!.setTag(position)
                holder.ll_ticketNumber!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "Service_timeLine"
                    )
                })

//                val colorAnim: ValueAnimator = ObjectAnimator.ofInt(holder.tv_TimeDue!!, "textColor", Color.RED, Color.BLUE)
//                colorAnim.setDuration(5000)
//                colorAnim.setEvaluator(ArgbEvaluator())
//                colorAnim.setRepeatCount(ValueAnimator.INFINITE)
//                colorAnim.setRepeatMode(ValueAnimator.REVERSE)
//                colorAnim.start()

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
        internal var tv_TicketNo          : TextView
        internal var tv_TicketDate          : TextView
        internal var tv_Branch          : TextView
        internal var tv_Customer          : TextView
        internal var tv_Mobile          : TextView
        internal var tv_Area          : TextView
        internal var tv_Employee          : TextView
        internal var tv_TimeDue          : TextView
        internal var im_Channel          : ImageView
        internal var im_Priority          : ImageView
        internal var im_Status          : ImageView
        internal var im_edit          : ImageView
        internal var im_tracker          : ImageView
//        internal var txtsino          : TextView
        internal var tv_Priority          : TextView
        internal var tv_Channel          : TextView
        internal var ll_employee    : LinearLayout
        internal var ll_status    : LinearLayout
        internal var llServiceList    : LinearLayout
        internal var ll_tracker    : LinearLayout
        internal var ll_ticketNumber    : LinearLayout
        init {
            tv_TicketNo        = v.findViewById<View>(R.id.tv_TicketNo) as TextView
            tv_TicketDate        = v.findViewById<View>(R.id.tv_TicketDate) as TextView
            tv_Branch        = v.findViewById<View>(R.id.tv_Branch) as TextView
            tv_Customer        = v.findViewById<View>(R.id.tv_Customer) as TextView
            tv_Mobile        = v.findViewById<View>(R.id.tv_Mobile) as TextView
            tv_Area        = v.findViewById<View>(R.id.tv_Area) as TextView
            tv_Employee        = v.findViewById<View>(R.id.tv_Employee) as TextView
            tv_TimeDue        = v.findViewById<View>(R.id.tv_TimeDue) as TextView
            tv_Priority        = v.findViewById<View>(R.id.tv_Priority) as TextView
            tv_Channel        = v.findViewById<View>(R.id.tv_Channel) as TextView

            im_Channel        = v.findViewById<View>(R.id.im_Channel) as ImageView
            im_Priority        = v.findViewById<View>(R.id.im_Priority) as ImageView
            im_Status        = v.findViewById<View>(R.id.im_Status) as ImageView
            im_edit        = v.findViewById<View>(R.id.im_edit) as ImageView
            im_tracker        = v.findViewById<View>(R.id.im_tracker) as ImageView
//            txtsino        = v.findViewById<View>(R.id.txtsino) as TextView
            ll_employee       = v.findViewById<View>(R.id.ll_employee) as LinearLayout
            ll_status       = v.findViewById<View>(R.id.ll_status) as LinearLayout
            llServiceList       = v.findViewById<View>(R.id.llServiceList) as LinearLayout
            ll_tracker       = v.findViewById<View>(R.id.ll_tracker) as LinearLayout
            ll_ticketNumber       = v.findViewById<View>(R.id.ll_ticketNumber) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}