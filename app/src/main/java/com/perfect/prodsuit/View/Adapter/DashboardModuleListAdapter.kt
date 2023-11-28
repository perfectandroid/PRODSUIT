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
import com.perfect.prodsuit.View.Activity.*
import org.json.JSONArray
import org.json.JSONObject


class DashboardModuleListAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,

):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "DashboardModuleListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_dashmodulelist, parent, false
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


                holder.txtv_label.text        = jsonObject!!.getString("ModuleName")
                var mode = jsonObject!!.getString("ModuleMode")

                if(mode.equals("1"))
                {
                    holder.ll_bkgrnd!!.setBackgroundDrawable(context.getDrawable(R.drawable.shapes_1))
                    holder.ll2!!.setBackgroundColor(context.getColor(R.color.white))
                    holder.img_module.setImageDrawable(context.resources.getDrawable(R.drawable.dash_lead))
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.textlead))

                }
                if(mode.equals("2"))
                {
                    holder.ll_bkgrnd!!.setBackgroundDrawable(context.getDrawable(R.drawable.shapes_servce))
                    holder.img_module.setImageDrawable(context.resources.getDrawable(R.drawable.dash_services))
                    holder.ll2!!.setBackgroundColor(context.getColor(R.color.white))
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.textsrvce))
                }
                if(mode.equals("3"))
                {
                    holder.ll_bkgrnd!!.setBackgroundDrawable(context.getDrawable(R.drawable.shapes_project))
                    holder.img_module.setImageDrawable(context.resources.getDrawable(R.drawable.dash_project))
                    holder.ll2!!.setBackgroundColor(context.getColor(R.color.white))
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.textprjct))
                }
                if(mode.equals("4"))
                {
                    holder.ll_bkgrnd!!.setBackgroundDrawable(context.getDrawable(R.drawable.shapes_collection))
                    holder.img_module.setImageDrawable(context.resources.getDrawable(R.drawable.dash_collection))
                    holder.ll2!!.setBackgroundColor(context.getColor(R.color.white))
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.textcollctn))
                }
                if(mode.equals("5"))
                {
                    holder.ll_bkgrnd!!.setBackgroundDrawable(context.getDrawable(R.drawable.shapes_pickup))
                    holder.img_module.setImageDrawable(context.resources.getDrawable(R.drawable.dash_pickup))
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.textpickup))
                    holder.ll2!!.setBackgroundColor(context.getColor(R.color.white))
                }
                if(mode.equals("6"))
                {
                    holder.ll_bkgrnd!!.setBackgroundDrawable(context.getDrawable(R.drawable.shapes_account))
                    holder.img_module.setImageDrawable(context.resources.getDrawable(R.drawable.dash_acnt))
                    holder.ll2!!.setBackgroundColor(context.getColor(R.color.white))
                    holder.txtv_label!!.setTextColor(context.resources.getColor(R.color.textacnt))
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
                    clickListener!!.onClick(
                        position,
                        "ModuleCount"
                    )
                    jsonObject = jsonArray.getJSONObject(position)
                    var submode = jsonObject!!.getString("ModuleMode")
                    var label=jsonObject!!.getString("ModuleName")
                    Log.e(TAG,"Position   "+position+"\n"+submode+"\n"+label)

                    if (submode=="1")
                    {
                        val i = Intent(context, TileGraphActivity::class.java)
                        i.putExtra("SubMode",submode)
                        i.putExtra("label",label)
                        context.startActivity(i)
                    }
                    if (submode=="2")
                    {
                        val i = Intent(context, ServiceGraphActivity::class.java)
                        i.putExtra("SubMode",submode)
                        i.putExtra("label",label)
                        context.startActivity(i)
                    }

                    if (submode=="4")
                    {
                        val i = Intent(context, InventoryGraphActivity::class.java)
                        i.putExtra("SubMode",submode)
                        i.putExtra("label",label)
                        context.startActivity(i)
                    }
                    if (submode=="5")
                    {
                        val i = Intent(context, AccountsGraphActivity::class.java)
                        i.putExtra("SubMode",submode)
                        i.putExtra("label",label)
                        context.startActivity(i)
                    }




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
        internal var ll_bkgrnd          : LinearLayout
        internal var img_module          : ImageView
        internal var ll2          : LinearLayout


        init {
           txtv_label        = v.findViewById<View>(R.id.txtv_label) as TextView
           tv_newCount        = v.findViewById<View>(R.id.tv_newCount) as TextView
            ll_new        = v.findViewById<View>(R.id.ll_new) as LinearLayout
         ll_bkgrnd       = v.findViewById<View>(R.id.ll_bkgrnd) as LinearLayout
            ll2       = v.findViewById<View>(R.id.ll2) as LinearLayout
         img_module = v.findViewById<View>(R.id.img_module) as ImageView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}