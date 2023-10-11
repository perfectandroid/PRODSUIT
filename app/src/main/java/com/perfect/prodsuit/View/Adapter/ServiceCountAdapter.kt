package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.ServiceAssignListActivity
import org.json.JSONArray
import org.json.JSONObject


class ServiceCountAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    internal var ID_Branch: String,
    internal var FK_Area: String,
    internal var ID_Employee: String,
    internal var strFromDate: String,
    internal var strToDate: String,
    internal var strCustomer: String,
    internal var strMobile: String,
    internal var strTicketNo: String,
    internal var strDueDays: String
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceCountAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_countlist, parent, false
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

//                if (position % 2 == 0){
//                    holder.llServiceList.setBackgroundDrawable(context.resources.getDrawable(R.drawable.svg_list_1))
//                }
//                else{
//                    holder.llServiceList.setBackgroundDrawable(context.resources.getDrawable(R.drawable.svg_list_2))
//                }


                holder.txtv_label.text        = jsonObject!!.getString("Field")
                holder.tv_newCount.text        = jsonObject!!.getString("value")
              //  holder.tv_Branch.text        = jsonObject!!.getString("Branch")
           //     holder.tv_Customer.text        = jsonObject!!.getString("Customer")


           /*     holder.tv_Channel.text        = jsonObject!!.getString("Channel")
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
                    if (jsonObject!!.getString("Status").equals("On-Hold")){
                        holder.im_Status.setImageDrawable(context.resources.getDrawable(R.drawable.svg_stat_hold))
                    }
                    if (jsonObject!!.getString("Status").equals("Pending")){
                        holder.im_Status.setImageDrawable(context.resources.getDrawable(R.drawable.svg_stat_pending))
                    }

                }

                holder.im_edit!!.setTag(position)
                holder.im_edit!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(position, "ServiceEdit")
                })

                holder.im_tracker!!.setTag(position)
                holder.im_tracker!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(position, "ServiceTracker")
                })




*/
//                val colorAnim: ValueAnimator = ObjectAnimator.ofInt(holder.tv_TimeDue!!, "textColor", Color.RED, Color.BLUE)
//                colorAnim.setDuration(5000)
//                colorAnim.setEvaluator(ArgbEvaluator())
//                colorAnim.setRepeatCount(ValueAnimator.INFINITE)
//                colorAnim.setRepeatMode(ValueAnimator.REVERSE)
//                colorAnim.start()

                holder.ll_new!!.setTag(position)
                holder.ll_new!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "ServiceCount")
                    jsonObject = jsonArray.getJSONObject(position)
                    var submode = jsonObject!!.getString("MasterID")
                    var label=jsonObject!!.getString("Field")
                    Log.e(TAG,"Position   "+position+"\n"+submode+"\n"+"Details : "+ID_Branch+"\n"+FK_Area+"\n"+ID_Employee+"\n"+strFromDate+"\n"+strToDate+"\n"+
                    strCustomer)

                    val i = Intent(context, ServiceAssignListActivity::class.java)
                    i.putExtra("SubMode",submode)
                    i.putExtra("ID_Branch",ID_Branch)
                    i.putExtra("FK_Area",FK_Area)
                    i.putExtra("ID_Employee",ID_Employee)
                    i.putExtra("strFromDate",strFromDate)
                    i.putExtra("strToDate",strToDate)
                    i.putExtra("strCustomer",strCustomer)
                    i.putExtra("strMobile",strMobile)
                    i.putExtra("strTicketNo",strTicketNo)
                    i.putExtra("strDueDays",strDueDays)
                    i.putExtra("label",label)
                    context.startActivity(i)

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
        internal var txtv_label          : TextView
        internal var tv_newCount          : TextView
        internal var ll_new          : LinearLayout
     /*   internal var tv_Customer          : TextView
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
        internal var ll_tracker    : LinearLayout*/
        init {
           txtv_label        = v.findViewById<View>(R.id.txtv_label) as TextView
           tv_newCount        = v.findViewById<View>(R.id.tv_newCount) as TextView
            ll_new        = v.findViewById<View>(R.id.ll_new) as LinearLayout
       /*     tv_Customer        = v.findViewById<View>(R.id.tv_Customer) as TextView
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
            ll_tracker       = v.findViewById<View>(R.id.ll_tracker) as LinearLayout*/
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}