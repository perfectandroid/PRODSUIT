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
import com.perfect.prodsuit.Helper.ClickListener
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.PickUpAndDeliveryListActivity
import org.json.JSONArray
import org.json.JSONObject

class PickupDeliveryListAdapter (internal var context: Context, internal var jsonArray: JSONArray, internal var SubMode: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG               : String         = "PickupDeliveryListAdapter"
    internal var jsonObject        : JSONObject?    = null
    private var clickListener      : ClickListener? = null
    private var priority           : String?        = null
    private var ID_ProductDelivery : String?        = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_pickup_delivery_list, parent, false
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

                priority = jsonObject!!.getString("Priority")
                ID_ProductDelivery = jsonObject!!.getString("ID_ProductDelivery")
                Log.e(TAG,"ID_ProductDelivery   112548566   "+ID_ProductDelivery)

                holder.tv_TicketNo.text            =  jsonObject!!.getString("ReferenceNo")
                holder.tv_Customer.text            =  jsonObject!!.getString("CustomerName")
                holder.tv_Mobile.text              =  "Mobile : "+jsonObject!!.getString("Mobile")
                holder.tv_AssignedDate.text        =  "Assigned On : "+jsonObject!!.getString("AssignedOn")
                holder.tv_Employee.text            =  "Employee : "+jsonObject!!.getString("EMPName")

                if (SubMode.equals("1")){

                    holder.ll_first!!.setBackgroundColor(context.resources.getColor(R.color.color_pickup))
                    holder.tv_DeliveryDateTime.text    =  "Pickup Date Time : "+jsonObject!!.getString("PickUpTime")

                    if (priority.equals("Medium")){
                        holder.img_Priority!!.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_medium))
                        holder.tv_Priority!!.setText("Medium")
                    }
                    if (priority.equals("Normal")){
                        holder.img_Priority!!.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_low))
                        holder.tv_Priority!!.setText("Normal")
                    }
                    if (priority.equals("High")){
                        holder.img_Priority!!.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_high))
                        holder.tv_Priority!!.setText("High")
                    }
                }
                if (SubMode.equals("2")){
                    holder.ll_first!!.setBackgroundColor(context.resources.getColor(R.color.color_delivery))
                    holder.tv_DeliveryDateTime.text    =  "Delivery Date Time : "+jsonObject!!.getString("PickUpTime")

                    if (priority.equals("Medium")){
                        holder.img_Priority!!.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_medium))
                        holder.tv_Priority!!.setText("Medium")
                    }
                    if (priority.equals("Normal")){
                        holder.img_Priority!!.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_low))
                        holder.tv_Priority!!.setText("Normal")
                    }
                    if (priority.equals("High")){
                        holder.img_Priority!!.setImageDrawable(context.resources.getDrawable(R.drawable.svg_hml_high))
                        holder.tv_Priority!!.setText("High")
                    }
                }

                holder.ll_main!!.setTag(position)
                holder.ll_main!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "pickupDelivery",it)
                })

                holder.img_call!!.setTag(position)
                holder.img_call!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "pickDelCall",it)
                })

                holder.img_location!!.setTag(position)
                holder.img_location!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "pickDelLocation",it)
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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tv_TicketNo            : TextView
        internal var tv_Customer            : TextView
        internal var tv_Mobile              : TextView
        internal var tv_DeliveryDateTime    : TextView
        internal var tv_AssignedDate        : TextView
        internal var tv_Employee            : TextView
        internal var ll_first               : LinearLayout
        internal var ll_main                : LinearLayout
        internal var img_call               : ImageView
        internal var img_location           : ImageView
        internal var img_Priority           : ImageView
        internal var tv_Priority            : TextView
        init {
            tv_TicketNo          = v.findViewById<View>(R.id.tv_TicketNo) as TextView
            tv_Customer          = v.findViewById<View>(R.id.tv_Customer) as TextView
            tv_Mobile            = v.findViewById<View>(R.id.tv_Mobile) as TextView
            tv_DeliveryDateTime  = v.findViewById<View>(R.id.tv_DeliveryDateTime) as TextView
            tv_AssignedDate      = v.findViewById<View>(R.id.tv_AssignedDate) as TextView
            tv_Employee          = v.findViewById<View>(R.id.tv_Employee) as TextView
            ll_first             = v.findViewById<View>(R.id.ll_first) as LinearLayout
            ll_main              = v.findViewById<View>(R.id.ll_main) as LinearLayout
            img_call             = v.findViewById<View>(R.id.img_call) as ImageView
            img_location         = v.findViewById<View>(R.id.img_location) as ImageView
            img_Priority         = v.findViewById<View>(R.id.img_Priority) as ImageView
            tv_Priority          = v.findViewById<View>(R.id.tv_Priority) as TextView
        }
    }

    fun setClickListener(itemClickListener: PickUpAndDeliveryListActivity) {
        clickListener = itemClickListener
    }
}