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

class LocationMarkingAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    internal val TAG : String = "AccountDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_location_marking, parent, false
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
                holder.txtBattery.text        = jsonObject!!.getString("ChargePercentage")+" %"
                holder.txtDate.text           = jsonObject!!.getString("EnteredDate")
                holder.txtTime.text           = jsonObject!!.getString("EnteredTime")
                holder.txtCustomer.text       = jsonObject!!.getString("EmployeeName")
                holder.txtDesignation.text    = jsonObject!!.getString("DesName")
                holder.txtAddress.text        = jsonObject!!.getString("LocLocationName")

                holder.im_mapview!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "LocList"
                    )

                })

                holder.im_detail!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "LocDetails"
                    )

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
        internal var txtBattery         : TextView
        internal var txtDate            : TextView
        internal var txtTime            : TextView
        internal var txtCustomer        : TextView
        internal var txtDesignation     : TextView
        internal var txtAddress         : TextView
        internal var im_mapview         : ImageView
        internal var im_detail          : ImageView
        internal var llLocationMarking  : LinearLayout
        init {
            txtBattery                  = v.findViewById<View>(R.id.txtBattery) as TextView
            txtDate                     = v.findViewById<View>(R.id.txtDate) as TextView
            txtTime                     = v.findViewById<View>(R.id.txtTime) as TextView
            txtCustomer                 = v.findViewById<View>(R.id.txtCustomer) as TextView
            txtDesignation              = v.findViewById<View>(R.id.txtDesignation) as TextView
            txtAddress                  = v.findViewById<View>(R.id.txtAddress) as TextView
            im_mapview                  = v.findViewById<View>(R.id.im_mapview) as ImageView
            im_detail                   = v.findViewById<View>(R.id.im_detail) as ImageView
            llLocationMarking           = v.findViewById<View>(R.id.llLocationMarking) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}