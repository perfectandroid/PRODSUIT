package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject


class ServiceFollowUpListAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemClickListener: ItemClickListenerData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_follow_up, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                var isChecked = ""
                val pos = position
                Log.v("gdfgfg44g","Ticket  "+jsonObject!!.getString("Ticket"))
                Log.v("gdfgfg44g","TicketDate  "+jsonObject!!.getString("TicketDate"))
                Log.v("gdfgfg44g","Customer  "+jsonObject!!.getString("Customer"))
                Log.v("gdfgfg44g","Mobile  "+jsonObject!!.getString("Mobile"))
                Log.v("gdfgfg44g","Priority  "+jsonObject!!.getString("Priority"))
                Log.v("gdfgfg44g","CurrentStatus  "+jsonObject!!.getString("CurrentStatus"))
                Log.v("gdfgfg44g","AssignedDate  "+jsonObject!!.getString("AssignedDate"))
                Log.v("gdfgfg44g","DueDays  "+jsonObject!!.getString("DueDays"))

                holder.tv_ticket.text = jsonObject!!.getString("Ticket")
                holder.tv_ticketDate.text = jsonObject!!.getString("TicketDate")
                holder.tv_customer.setText(jsonObject!!.getString("Customer"))
                holder.tv_mobile.text=""+jsonObject!!.getString("Mobile");
                holder.tv_priority.text=jsonObject!!.getString("Priority")
                if(jsonObject!!.getString("Priority").toString().equals("High"))
                {
                    Glide.with(context)
                        .load(R.drawable.rounded_profile_bg)
                        .into(holder.imgPriority);
                }
                else if(jsonObject!!.getString("Priority").toString().equals("Medium"))
                {
                    Glide.with(context)
                        .load(R.drawable.priority_medium)
                        .into(holder.imgPriority);
                }
                else
                {

                }
                holder.tv_status.text=""+jsonObject!!.getString("CurrentStatus")
                holder.tv_assignedDate.setText(jsonObject!!.getString("AssignedDate"))
                holder.tv_dueDays.setText(jsonObject!!.getString("DueDays"))
                holder.lin_follow!!.setTag(position)
                holder.lin_follow.setOnClickListener(View.OnClickListener {
                    if (mItemClickListener != null) {
                        mItemClickListener!!.onClick(position, "followUp", jsonObject!!);
                    }
                })
                holder.img_call.setOnClickListener(View.OnClickListener {
                    if (mItemClickListener != null) {
                        mItemClickListener!!.onClick(position, "call", jsonObject!!);
                    }
                })
                holder.img_info.setOnClickListener(View.OnClickListener {
                    if (mItemClickListener != null) {
                        mItemClickListener!!.onClick(position, "info", jsonObject!!);
                    }
                })
                holder.img_location.setOnClickListener(View.OnClickListener {
                    if (mItemClickListener != null) {
                        mItemClickListener!!.onClick(position, "location", jsonObject!!);
                    }
                })
                holder.lin_time.setOnClickListener(View.OnClickListener {
                   // Toast.makeText(context, "ServiceFollowUpListAdapter ln100", Toast.LENGTH_SHORT).show()
//                    if(jsonObject!!.getString("runningStatus")=="0")
//                    {
//                        if (mItemClickListener != null) {
//                            mItemClickListener!!.onClick(position, "start", jsonObject!!);
//                        }
//                    }
//                    if(jsonObject!!.getString("runningStatus")=="1")
//                    {
//                        if (mItemClickListener != null) {
//                            mItemClickListener!!.onClick(position, "stop", jsonObject!!);
//                        }
//                    }
//                    if(jsonObject!!.getString("runningStatus")=="2")
//                    {
//                        if (mItemClickListener != null) {
//                            mItemClickListener!!.onClick(position, "restart", jsonObject!!);
//                        }
//                    }

                })
               /* if(jsonObject!!.getString("runningStatus")=="1")
                {
                    holder.lin_time.background=ContextCompat.getDrawable(context,R.drawable.clock)
                    Glide.with(context)
                        .load(R.drawable.clock)
                        .into(holder.img_running);
                    val rotate = RotateAnimation(
                        0F,
                        360F,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                    )
                    rotate.setDuration(5000)
                    rotate.repeatCount=-1
                    rotate.setInterpolator(LinearInterpolator())
                    holder.img_running.startAnimation(rotate)
                }
                else
                {
                  //  holder.lin_time.background=ContextCompat.getDrawable(context,R.drawable.rounded_profile_bg)
                    Glide.with(context)
                        .load(R.drawable.ic_stop)
                        .into(holder.img_running);
                }
                */
                holder.lin_time.background=ContextCompat.getDrawable(context,R.drawable.clock)
                Glide.with(context)
                    .load(R.drawable.clock)
                    .into(holder.img_running);
                val rotate = RotateAnimation(
                    0F,
                    360F,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
                )
                rotate.setDuration(5000)
                rotate.repeatCount=-1
                rotate.setInterpolator(LinearInterpolator())
                holder.img_running.startAnimation(rotate)


            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.v("sadsdsssss", "e  " + e)
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
        var tv_ticket: TextView
        var tv_mobile: TextView
        var tv_customer: TextView
        var tv_ticketDate: TextView
        var tv_assignedDate: TextView
        var tv_priority: TextView
        var tv_status: TextView
        var tv_dueDays: TextView
        var lin_follow: CardView
        var img_call: ImageView
        var img_location: ImageView
        var img_info: ImageView
        var img_running: ImageView
        var imgPriority: ImageView
        var lin_time: LinearLayout


        init {
            lin_time = v.findViewById(R.id.lin_time) as LinearLayout
            tv_ticket = v.findViewById(R.id.tv_ticket) as TextView
            tv_mobile = v.findViewById(R.id.tv_mobile) as TextView
            tv_customer = v.findViewById(R.id.tv_customer) as TextView
            tv_ticketDate = v.findViewById(R.id.tv_ticketDate) as TextView
            tv_assignedDate = v.findViewById(R.id.tv_assignedDate) as TextView
            tv_priority = v.findViewById(R.id.tv_priority) as TextView
            tv_status = v.findViewById(R.id.tv_status) as TextView
            tv_dueDays = v.findViewById(R.id.tv_dueDays) as TextView
            lin_follow = v.findViewById(R.id.lin_follow) as CardView
            img_call = v.findViewById(R.id.img_call) as ImageView
            img_location = v.findViewById(R.id.img_location) as ImageView
            img_info = v.findViewById(R.id.img_info) as ImageView
            img_running = v.findViewById(R.id.img_running) as ImageView
            imgPriority = v.findViewById(R.id.imgPriority) as ImageView
        }
    }

    fun addItemClickListener(listener: ItemClickListenerData) {
        mItemClickListener = listener
    }



}