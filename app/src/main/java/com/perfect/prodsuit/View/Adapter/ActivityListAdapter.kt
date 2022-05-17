package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
            if (holder is MainViewHolder) {
                Log.i("array", jsonArray.toString()+"\n"+"Test")
                jsonObject = jsonArray.getJSONObject(position)
                Log.e(TAG,"onBindViewHolder   36   ")
                val pos = position+1
                holder.ll_Call.visibility = View.VISIBLE
                holder.txtDate.text        = jsonObject!!.getString("Date")
                holder.txtFollowup.text        = jsonObject!!.getString("FollowUpBy")
                holder.txtv_custmrremrk.text        = jsonObject!!.getString("CustomerRemark")
                holder.txtv_empremrk.text        = jsonObject!!.getString("EmployeeRemark")
                holder.txtStatus.text        = jsonObject!!.getString("Status")
                holder.txtv_nxtactn.text        = jsonObject!!.getString("NextActionDate")
                var actiontype= jsonObject!!.getString("ActionType")
                holder.txtv_actntype.text        = actiontype+" with"
                holder.ll_Call_Icon.visibility = View.VISIBLE



                val call = ContextCompat.getDrawable(context!!,R.drawable.call_icon)?.apply {
                    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                }

                val sitevisit = ContextCompat.getDrawable(context!!,R.drawable.location_icon)?.apply {
                    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                }
                val email = ContextCompat.getDrawable(context!!,R.drawable.message_icon)?.apply {
                    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                }
                if(actiontype.equals("Call"))
                {
                    holder.im_Call_Icon.setImageDrawable(call)
                }
                else if(actiontype.equals("Site Visit"))
                {
                    holder.im_Call_Icon.setImageDrawable(sitevisit)
                }
                else if(actiontype.equals("Email"))
                {
                    holder.im_Call_Icon.setImageDrawable(email)
                }
                else if(actiontype.equals("Courier"))
                {
                    holder.im_Call_Icon.setImageDrawable(email)
                }
                holder.txtStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_svg, 0, 0, 0);

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
        internal var txtv_nxtactn      : TextView
        internal var txtFollowup       : TextView
        internal var txtv_custmrremrk       : TextView
        internal var txtv_empremrk       : TextView
        internal var txtStatus       : TextView
        internal var txtv_actntype       : TextView
        lateinit var ll_Call       : LinearLayout
        lateinit var ll_Call_Icon       : LinearLayout
        lateinit var ll_cstmrremrk       : LinearLayout
        lateinit var ll_empremrk       : LinearLayout
        lateinit var ll_status       : LinearLayout
        lateinit var ll_nxtactn       : LinearLayout
        lateinit var im_Call_Icon       : ImageView
        lateinit var ll_sitevisit_Icon       : LinearLayout
        lateinit var ll_email_Icon       : LinearLayout


        init {
            txtDate        = v.findViewById<View>(R.id.txtv_Date) as TextView
            txtv_nxtactn        = v.findViewById<View>(R.id.txtv_nxtactn) as TextView
            txtFollowup         = v.findViewById<View>(R.id.txtv_Followup) as TextView
            txtv_custmrremrk         = v.findViewById<View>(R.id.txtv_custmrremrk) as TextView
            txtv_empremrk         = v.findViewById<View>(R.id.txtv_empremrk) as TextView
            txtStatus         = v.findViewById<View>(R.id.txtv_Status) as TextView
            txtv_actntype         = v.findViewById<View>(R.id.txtv_actntype) as TextView
            im_Call_Icon         = v.findViewById<View>(R.id.im_Call_Icon) as ImageView
            ll_Call         = v.findViewById<View>(R.id.ll_Call) as LinearLayout
            ll_Call_Icon         = v.findViewById<View>(R.id.ll_Call_Icon) as LinearLayout
          /*  ll_sitevisit_Icon         = v.findViewById<View>(R.id.ll_sitevisit_Icon) as LinearLayout
            ll_sitevisit_Icon         = v.findViewById<View>(R.id.ll_sitevisit_Icon) as LinearLayout
            ll_email_Icon = v.findViewById<View>(R.id.ll_email_Icon) as LinearLayout*/
         /*   ll_date         = v.findViewById<View>(R.id.ll_date) as LinearLayout
            ll_followup         = v.findViewById<View>(R.id.ll_followup) as LinearLayout
            ll_cstmrremrk         = v.findViewById<View>(R.id.ll_cstmrremrk) as LinearLayout
            ll_empremrk         = v.findViewById<View>(R.id.ll_empremrk) as LinearLayout
            ll_status         = v.findViewById<View>(R.id.ll_status) as LinearLayout
            ll_nxtactn         = v.findViewById<View>(R.id.ll_nxtactn) as LinearLayout*/
        }
    }

    override fun getItemCount(): Int {
        return  jsonArray.length()
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}