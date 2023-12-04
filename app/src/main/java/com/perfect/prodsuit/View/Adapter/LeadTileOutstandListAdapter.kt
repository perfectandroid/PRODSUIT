package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.ServiceAssignListActivity
import com.perfect.prodsuit.View.Activity.TileGraphActivity
import org.json.JSONArray
import org.json.JSONObject


class LeadTileOutstandListAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,

):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "LeadTileOutstandListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_leadoutstandlist, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1052   ")
                val pos = position+1

//                if (position % 2 == 0){
//                    holder.llServiceList.setBackgroundDrawable(context.resources.getDrawable(R.drawable.svg_list_1))
//                }
//                else{
//                    holder.llServiceList.setBackgroundDrawable(context.resources.getDrawable(R.drawable.svg_list_2))
//                }
                if(position==0)
                {
                    holder.ll2.visibility=View.GONE

                }

                holder.txtv_label.text        = jsonObject!!.getString("Label")
                holder.tv_newCount.text        = jsonObject!!.getString("Value")
             //   var mode = jsonObject!!.getString("ModuleMode")

                if (jsonObject!!.getString("Label").equals("LeadOutstand")){
                    holder.txtv_label.setTextColor(context.getColor(R.color.green))
                }
                if (jsonObject!!.getString("Label").equals("Hot")){
                    holder.txtv_label.setTextColor(context.getColor(R.color.ongoing))
                }
                if (jsonObject!!.getString("Label").equals("Warm")){
                    holder.txtv_label.setTextColor(context.getColor(R.color.warm))
                }
                if (jsonObject!!.getString("Label").equals("Cold")){
                    holder.txtv_label.setTextColor(context.getColor(R.color.bluecolr))
                }

              //  holder.tv_newCount.text        = jsonObject!!.getString("value")
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
                /*    clickListener!!.onClick(position, "ModuleCount")
                    jsonObject = jsonArray.getJSONObject(position)
                    var submode = jsonObject!!.getString("ModuleMode")
                    var label=jsonObject!!.getString("ModuleName")
                    Log.e(TAG,"Position   "+position+"\n"+submode+"\n"+label)

                    val i = Intent(context, TileGraphActivity::class.java)
                    i.putExtra("SubMode",submode)
                    i.putExtra("label",label)
                    context.startActivity(i)*/

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
        internal var ll2          : LinearLayout

        init {
           txtv_label        = v.findViewById<View>(R.id.txtv_label) as TextView
           tv_newCount        = v.findViewById<View>(R.id.tv_newCount) as TextView
           ll_new        = v.findViewById<View>(R.id.ll_new) as LinearLayout
            ll2        = v.findViewById<View>(R.id.ll2) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}