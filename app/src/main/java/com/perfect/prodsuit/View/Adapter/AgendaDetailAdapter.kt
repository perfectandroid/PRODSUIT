package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class AgendaDetailAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "AgendaDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_agenda_detail, parent, false
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
                    if (jsonObject!!.getString("ID_ActionType").equals("1")){
                    holder.ll_Call.visibility = View.VISIBLE
                    holder.card_call.visibility = View.VISIBLE

                    holder.tvcall_leadno!!.getBackground().setTint(context.getColor(R.color.todolist_light_Color));
                    holder.tvcall_leadno.setText(jsonObject!!.getString("ID_LeadGenerate"))
                    holder.tvCall_name.setText(jsonObject!!.getString("CustomerName"))
                    holder.tvCall_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                    holder.tvCall_phone.setText(jsonObject!!.getString("CustomerMobile"))
                    holder.tvCall_address.setText(jsonObject!!.getString("CustomerAddress"))
                    holder.tvCall_product.setText(jsonObject!!.getString("EnquiryAbout"))
                    holder.tvCall_preference.setText(jsonObject!!.getString("PriorityName"))

                   if (jsonObject!!.getString("SubMode").equals("1")){
                       // PENDING
                       holder.tvCall_Status.setText(jsonObject!!.getString("Status"))
                       holder.tvCall_Status.setTextColor(context.getColor(R.color.color_pending))
                       holder.tvCall_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_svg, 0, 0, 0);
                   }
                   else if (jsonObject!!.getString("SubMode").equals("2")){
                       // UPCOMING
                       holder.tvCall_Status.setText(jsonObject!!.getString("Status"))
                       holder.tvCall_Status.setTextColor(context.getColor(R.color.color_upcoming))
                       holder.tvCall_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upcoming_svg, 0, 0, 0);
                   }
                   else if (jsonObject!!.getString("SubMode").equals("3")){
                       // COMPLETED
                       holder.tvCall_Status.setText(jsonObject!!.getString("Status"))
                       holder.tvCall_Status.setTextColor(context.getColor(R.color.color_complete))
                       holder.tvCall_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.completed_svg, 0, 0, 0);
                       holder.ll_Call_Icon.visibility = View.GONE
                   }

                        if(jsonObject!!.getString("PriorityName").equals("Hot")){
                            holder.im_Call_Preference.setImageResource(R.drawable.preference2)
                            holder.im_Call_Preference.setColorFilter(context.getColor(R.color.ColorHot))
                        }else if (jsonObject!!.getString("PriorityName").equals("Warm")){
                            holder.im_Call_Preference.setImageResource(R.drawable.preference3)
                            holder.im_Call_Preference.setColorFilter(context.getColor(R.color.ColorWarm))

                        }else if (jsonObject!!.getString("PriorityName").equals("Cold")){
                            holder.im_Call_Preference.setImageResource(R.drawable.preference1)
                            holder.im_Call_Preference.setColorFilter(context.getColor(R.color.ColorCold))

                        }


                }

                if (jsonObject!!.getString("ID_ActionType").equals("2")){
                        holder.ll_Message.visibility = View.VISIBLE
                        holder.card_Message.visibility = View.VISIBLE
                        holder.tvMessage_leadno!!.getBackground().setTint(context.getColor(R.color.todolist_light_Color));
                        holder.tvMessage_leadno.setText(jsonObject!!.getString("ID_LeadGenerate"))
                        holder.tvMessage_name.setText(jsonObject!!.getString("CustomerName"))
                        holder.tvMessage_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                        holder.tvMessage_phone.setText(jsonObject!!.getString("CustomerMobile"))
                        holder.tvMessage_address.setText(jsonObject!!.getString("CustomerAddress"))
                        holder.tvMessage_product.setText(jsonObject!!.getString("EnquiryAbout"))
                        holder.tvMessage_preference.setText(jsonObject!!.getString("PriorityName"))

                         if (jsonObject!!.getString("SubMode").equals("1")){
                             // PENDING
                             holder.tvMessage_Status.setText(jsonObject!!.getString("Status"))
                             holder.tvMessage_Status.setTextColor(context.getColor(R.color.color_pending))
                             holder.tvMessage_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_svg, 0, 0, 0);
                         }
                         else if (jsonObject!!.getString("SubMode").equals("2")){
                             // UPCOMING
                             holder.tvMessage_Status.setText(jsonObject!!.getString("Status"))
                             holder.tvMessage_Status.setTextColor(context.getColor(R.color.color_upcoming))
                             holder.tvMessage_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upcoming_svg, 0, 0, 0);
                         }
                         else if (jsonObject!!.getString("SubMode").equals("3")){
                             // COMPLETED
                             holder.tvMessage_Status.setText(jsonObject!!.getString("Status"))
                             holder.tvMessage_Status.setTextColor(context.getColor(R.color.color_complete))
                             holder.tvMessage_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.completed_svg, 0, 0, 0);
                             holder.ll_Message_Icon.visibility = View.GONE
                         }

                        if(jsonObject!!.getString("PriorityName").equals("Hot")){
                            holder.imMessage_preference.setImageResource(R.drawable.preference2)
                            holder.imMessage_preference.setColorFilter(context.getColor(R.color.ColorHot))
                        }else if (jsonObject!!.getString("PriorityName").equals("Warm")){
                            holder.imMessage_preference.setImageResource(R.drawable.preference3)
                            holder.imMessage_preference.setColorFilter(context.getColor(R.color.ColorWarm))

                        }else if (jsonObject!!.getString("PriorityName").equals("Cold")){
                            holder.imMessage_preference.setImageResource(R.drawable.preference1)
                            holder.imMessage_preference.setColorFilter(context.getColor(R.color.ColorCold))

                        }

                    }

                    if (jsonObject!!.getString("ID_ActionType").equals("3")){
                        holder.ll_Meeting.visibility = View.VISIBLE
                        holder.card_Meeting.visibility = View.VISIBLE
                        holder.tvMeeting_leadno!!.getBackground().setTint(context.getColor(R.color.todolist_light_Color));
                        holder.tvMeeting_leadno.setText(jsonObject!!.getString("ID_LeadGenerate"))
                        holder.tvMeeting_name.setText(jsonObject!!.getString("CustomerName"))
                        holder.tvMeeting_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                        holder.tvMeeting_phone.setText(jsonObject!!.getString("CustomerMobile"))
                        holder.tvMeeting_address.setText(jsonObject!!.getString("CustomerAddress"))
                        holder.tvMeeting_product.setText(jsonObject!!.getString("EnquiryAbout"))
                        holder.tvMeeting_preference.setText(jsonObject!!.getString("PriorityName"))

                        if (jsonObject!!.getString("SubMode").equals("1")){
                            // PENDING
                            holder.tvMeeting_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvMeeting_Status.setTextColor(context.getColor(R.color.color_pending))
                            holder.tvMeeting_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_svg, 0, 0, 0);
                        }
                        else if (jsonObject!!.getString("SubMode").equals("2")){
                            // UPCOMING
                            holder.tvMeeting_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvMeeting_Status.setTextColor(context.getColor(R.color.color_upcoming))
                            holder.tvMeeting_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upcoming_svg, 0, 0, 0);
                        }
                        else if (jsonObject!!.getString("SubMode").equals("3")){
                            // COMPLETED
                            holder.tvMeeting_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvMeeting_Status.setTextColor(context.getColor(R.color.color_complete))
                            holder.tvMeeting_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.completed_svg, 0, 0, 0);
                            holder.ll_Meeting_Icon.visibility = View.GONE
                        }

                        if(jsonObject!!.getString("PriorityName").equals("Hot")){
                            holder.imMeeting_preference.setImageResource(R.drawable.preference2)
                            holder.imMeeting_preference.setColorFilter(context.getColor(R.color.ColorHot))
                        }else if (jsonObject!!.getString("PriorityName").equals("Warm")){
                            holder.imMeeting_preference.setImageResource(R.drawable.preference3)
                            holder.imMeeting_preference.setColorFilter(context.getColor(R.color.ColorWarm))

                        }else if (jsonObject!!.getString("PriorityName").equals("Cold")){
                            holder.imMeeting_preference.setImageResource(R.drawable.preference1)
                            holder.imMeeting_preference.setColorFilter(context.getColor(R.color.ColorCold))

                        }




                    }

                    if (jsonObject!!.getString("ID_ActionType").equals("4")){
                        holder.ll_Document.visibility = View.VISIBLE
                        holder.card_Document.visibility = View.VISIBLE
                        holder.tvDocument_leadno!!.getBackground().setTint(context.getColor(R.color.todolist_light_Color));
                        holder.tvDocument_leadno.setText(jsonObject!!.getString("ID_LeadGenerate"))
                        holder.tvDocument_name.setText(jsonObject!!.getString("CustomerName"))
                        holder.tvDocument_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                        holder.tvDocument_phone.setText(jsonObject!!.getString("CustomerMobile"))
                        holder.tvDocument_address.setText(jsonObject!!.getString("CustomerAddress"))
                        holder.tvDocument_product.setText(jsonObject!!.getString("EnquiryAbout"))
                        holder.tvDocument_preference.setText(jsonObject!!.getString("PriorityName"))

                        if (jsonObject!!.getString("SubMode").equals("1")){
                            // PENDING
                            holder.tvDocument_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvDocument_Status.setTextColor(context.getColor(R.color.color_pending))
                            holder.tvDocument_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_svg, 0, 0, 0);
                        }
                        else if (jsonObject!!.getString("SubMode").equals("2")){
                            // UPCOMING
                            holder.tvDocument_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvDocument_Status.setTextColor(context.getColor(R.color.color_upcoming))
                            holder.tvDocument_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upcoming_svg, 0, 0, 0);
                        }
                        else if (jsonObject!!.getString("SubMode").equals("3")){
                            // COMPLETED
                            holder.tvDocument_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvDocument_Status.setTextColor(context.getColor(R.color.color_complete))
                            holder.tvDocument_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.completed_svg, 0, 0, 0);
                            holder.ll_Document_Icon.visibility = View.GONE
                        }

                        if(jsonObject!!.getString("PriorityName").equals("Hot")){
                            holder.imDocument_preference.setImageResource(R.drawable.preference2)
                            holder.imDocument_preference.setColorFilter(context.getColor(R.color.ColorHot))
                        }else if (jsonObject!!.getString("PriorityName").equals("Warm")){
                            holder.imDocument_preference.setImageResource(R.drawable.preference3)
                            holder.imDocument_preference.setColorFilter(context.getColor(R.color.ColorWarm))

                        }else if (jsonObject!!.getString("PriorityName").equals("Cold")){
                            holder.imDocument_preference.setImageResource(R.drawable.preference1)
                            holder.imDocument_preference.setColorFilter(context.getColor(R.color.ColorCold))

                        }


                    }

                    if (jsonObject!!.getString("ID_ActionType").equals("5")){
                        holder.ll_Quotation.visibility = View.VISIBLE
                        holder.card_Quotation.visibility = View.VISIBLE
                        holder.tvQuotation_leadno!!.getBackground().setTint(context.getColor(R.color.todolist_light_Color));
                        holder.tvQuotation_leadno.setText(jsonObject!!.getString("ID_LeadGenerate"))
                        holder.tvQuotation_name.setText(jsonObject!!.getString("CustomerName"))
                        holder.tvQuotation_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                        holder.tvQuotation_phone.setText(jsonObject!!.getString("CustomerMobile"))
                        holder.tvQuotation_address.setText(jsonObject!!.getString("CustomerAddress"))
                        holder.tvQuotation_product.setText(jsonObject!!.getString("EnquiryAbout"))
                        holder.tvQuotation_preference.setText(jsonObject!!.getString("PriorityName"))

                        if (jsonObject!!.getString("SubMode").equals("1")){
                            // PENDING
                            holder.tvQuotation_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvQuotation_Status.setTextColor(context.getColor(R.color.color_pending))
                            holder.tvQuotation_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_svg, 0, 0, 0);
                        }
                        else if (jsonObject!!.getString("SubMode").equals("2")){
                            // UPCOMING
                            holder.tvQuotation_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvQuotation_Status.setTextColor(context.getColor(R.color.color_upcoming))
                            holder.tvQuotation_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upcoming_svg, 0, 0, 0);
                        }
                        else if (jsonObject!!.getString("SubMode").equals("3")){
                            // COMPLETED
                            holder.tvQuotation_Status.setText(jsonObject!!.getString("Status"))
                            holder.tvQuotation_Status.setTextColor(context.getColor(R.color.color_complete))
                            holder.tvQuotation_Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.completed_svg, 0, 0, 0);
                            holder.ll_Quotation_Icon.visibility = View.GONE
                        }


                        if(jsonObject!!.getString("PriorityName").equals("Hot")){
                            holder.imQuotation_preference.setImageResource(R.drawable.preference2)
                            holder.imQuotation_preference.setColorFilter(context.getColor(R.color.ColorHot))
                        }else if (jsonObject!!.getString("PriorityName").equals("Warm")){
                            holder.imQuotation_preference.setImageResource(R.drawable.preference3)
                            holder.imQuotation_preference.setColorFilter(context.getColor(R.color.ColorWarm))

                        }else if (jsonObject!!.getString("PriorityName").equals("Cold")){
                            holder.imQuotation_preference.setImageResource(R.drawable.preference1)
                            holder.imQuotation_preference.setColorFilter(context.getColor(R.color.ColorCold))

                        }


                    }


                holder.im_Meeting_Icon.setTag(position)
                holder.im_Meeting_Icon.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "agendaLocation")
                })

                holder.im_Message_Icon.setTag(position)
                holder.im_Message_Icon.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "agendaMessage")
                })
                holder.im_Call_Icon.setTag(position)
                holder.im_Call_Icon.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "agendaCall")
                })

                holder.im_Call_Alarm.setTag(position)
                holder.im_Call_Alarm.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "CallReminder")
                })

                holder.im_Document_Icon.setTag(position)
                holder.im_Document_Icon.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "agendaDocument")
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

        internal var card_call        : CardView
        internal var card_Message     : CardView
        internal var card_Meeting     : CardView
        internal var card_Document     : CardView
        internal var card_Quotation     : CardView



        internal var ll_Call          : LinearLayout
        internal var ll_Call_Icon     : LinearLayout
        internal var ll_Message       : LinearLayout
        internal var ll_Message_Icon  : LinearLayout
        internal var ll_Meeting       : LinearLayout
        internal var ll_Meeting_Icon  : LinearLayout
        internal var ll_Document      : LinearLayout
        internal var ll_Document_Icon : LinearLayout
        internal var ll_Quotation     : LinearLayout
        internal var ll_Quotation_Icon     : LinearLayout

        internal var tvcall_leadno     : TextView
        internal var tvCall_name       : TextView
        internal var tvCall_TrnsDate   : TextView
        internal var tvCall_address    : TextView
        internal var tvCall_phone      : TextView
        internal var tvCall_Status     : TextView
        internal var tvCall_product    : TextView
        internal var tvCall_preference    : TextView

        internal var im_Call_Icon      : ImageView
        internal var im_Call_Alarm      : ImageView
        internal var im_Call_Preference     : ImageView

        internal var tvMessage_leadno    : TextView
        internal var tvMessage_name      : TextView
        internal var tvMessage_TrnsDate  : TextView
        internal var tvMessage_address   : TextView
        internal var tvMessage_phone     : TextView
        internal var tvMessage_Status    : TextView
        internal var tvMessage_product   : TextView
        internal var tvMessage_preference   : TextView
        internal var im_Message_Icon      : ImageView
        internal var im_Message_Alarm      : ImageView
        internal var imMessage_preference      : ImageView

        internal var tvMeeting_leadno      : TextView
        internal var tvMeeting_name      : TextView
        internal var tvMeeting_TrnsDate  : TextView
        internal var tvMeeting_address   : TextView
        internal var tvMeeting_phone     : TextView
        internal var tvMeeting_Status    : TextView
        internal var tvMeeting_product   : TextView
        internal var tvMeeting_preference   : TextView
        internal var im_Meeting_Icon  : ImageView
        internal var im_Meeting_Alarm  : ImageView
        internal var imMeeting_preference  : ImageView

        internal var tvDocument_leadno    : TextView
        internal var tvDocument_name      : TextView
        internal var tvDocument_TrnsDate  : TextView
        internal var tvDocument_address   : TextView
        internal var tvDocument_phone     : TextView
        internal var tvDocument_Status    : TextView
        internal var tvDocument_preference    : TextView
        internal var tvDocument_product   : TextView
        internal var im_Document_Icon  : ImageView
        internal var imDocument_preference  : ImageView

        internal var tvQuotation_leadno      : TextView
        internal var tvQuotation_name      : TextView
        internal var tvQuotation_TrnsDate  : TextView
        internal var tvQuotation_address   : TextView
        internal var tvQuotation_phone     : TextView
        internal var tvQuotation_Status    : TextView
        internal var tvQuotation_product   : TextView
        internal var tvQuotation_preference   : TextView
        internal var imQuotation_preference   : ImageView

        init {

            card_call          = v.findViewById<View>(R.id.card_call) as CardView
            card_Message       = v.findViewById<View>(R.id.card_Message) as CardView
            card_Meeting       = v.findViewById<View>(R.id.card_Meeting) as CardView
            card_Document      = v.findViewById<View>(R.id.card_Document) as CardView
            card_Quotation      = v.findViewById<View>(R.id.card_Quotation) as CardView

            ll_Call            = v.findViewById<View>(R.id.ll_Call) as LinearLayout
            ll_Call_Icon       = v.findViewById<View>(R.id.ll_Call_Icon) as LinearLayout
            ll_Message         = v.findViewById<View>(R.id.ll_Message) as LinearLayout
            ll_Message_Icon    = v.findViewById<View>(R.id.ll_Message_Icon) as LinearLayout
            ll_Meeting         = v.findViewById<View>(R.id.ll_Meeting) as LinearLayout
            ll_Meeting_Icon    = v.findViewById<View>(R.id.ll_Meeting_Icon) as LinearLayout
            ll_Document        = v.findViewById<View>(R.id.ll_Document) as LinearLayout
            ll_Document_Icon   = v.findViewById<View>(R.id.ll_Document_Icon) as LinearLayout
            ll_Quotation       = v.findViewById<View>(R.id.ll_Quotation) as LinearLayout
            ll_Quotation_Icon  = v.findViewById<View>(R.id.ll_Quotation_Icon) as LinearLayout

            tvcall_leadno       = v.findViewById<View>(R.id.tvcall_leadno) as TextView
            tvCall_name         = v.findViewById<View>(R.id.tvCall_name) as TextView
            tvCall_TrnsDate     = v.findViewById<View>(R.id.tvCall_TrnsDate) as TextView
            tvCall_address      = v.findViewById<View>(R.id.tvCall_address) as TextView
            tvCall_phone        = v.findViewById<View>(R.id.tvCall_phone) as TextView
            tvCall_Status       = v.findViewById<View>(R.id.tvCall_Status) as TextView
            tvCall_product      = v.findViewById<View>(R.id.tvCall_product) as TextView
            tvCall_preference      = v.findViewById<View>(R.id.tvCall_preference) as TextView
            im_Call_Icon        = v.findViewById<View>(R.id.im_Call_Icon) as ImageView
            im_Call_Alarm        = v.findViewById<View>(R.id.im_Call_Alarm) as ImageView
            im_Call_Preference        = v.findViewById<View>(R.id.im_Call_Preference) as ImageView

            tvMessage_leadno     = v.findViewById<View>(R.id.tvMessage_leadno) as TextView
            tvMessage_name     = v.findViewById<View>(R.id.tvMessage_name) as TextView
            tvMessage_TrnsDate     = v.findViewById<View>(R.id.tvMessage_TrnsDate) as TextView
            tvMessage_address        = v.findViewById<View>(R.id.tvMessage_address) as TextView
            tvMessage_phone        = v.findViewById<View>(R.id.tvMessage_phone) as TextView
            tvMessage_Status        = v.findViewById<View>(R.id.tvMessage_Status) as TextView
            tvMessage_product        = v.findViewById<View>(R.id.tvMessage_product) as TextView
            tvMessage_preference        = v.findViewById<View>(R.id.tvMessage_preference) as TextView
            im_Message_Icon        = v.findViewById<View>(R.id.im_Message_Icon) as ImageView
            im_Message_Alarm        = v.findViewById<View>(R.id.im_Message_Alarm) as ImageView
            imMessage_preference        = v.findViewById<View>(R.id.imMessage_preference) as ImageView

            tvMeeting_leadno     = v.findViewById<View>(R.id.tvMeeting_leadno) as TextView
            tvMeeting_name     = v.findViewById<View>(R.id.tvMeeting_name) as TextView
            tvMeeting_TrnsDate     = v.findViewById<View>(R.id.tvMeeting_TrnsDate) as TextView
            tvMeeting_address        = v.findViewById<View>(R.id.tvMeeting_address) as TextView
            tvMeeting_phone        = v.findViewById<View>(R.id.tvMeeting_phone) as TextView
            tvMeeting_Status        = v.findViewById<View>(R.id.tvMeeting_Status) as TextView
            tvMeeting_product        = v.findViewById<View>(R.id.tvMeeting_product) as TextView
            tvMeeting_preference        = v.findViewById<View>(R.id.tvMeeting_preference) as TextView
            im_Meeting_Icon            = v.findViewById<View>(R.id.im_Meeting_Icon) as ImageView
            im_Meeting_Alarm            = v.findViewById<View>(R.id.im_Meeting_Alarm) as ImageView
            imMeeting_preference            = v.findViewById<View>(R.id.imMeeting_preference) as ImageView

            tvDocument_leadno    = v.findViewById<View>(R.id.tvDocument_leadno) as TextView
            tvDocument_name    = v.findViewById<View>(R.id.tvDocument_name) as TextView
            tvDocument_TrnsDate    = v.findViewById<View>(R.id.tvDocument_TrnsDate) as TextView
            tvDocument_address        = v.findViewById<View>(R.id.tvDocument_address) as TextView
            tvDocument_phone        = v.findViewById<View>(R.id.tvDocument_phone) as TextView
            tvDocument_Status        = v.findViewById<View>(R.id.tvDocument_Status) as TextView
            tvDocument_product        = v.findViewById<View>(R.id.tvDocument_product) as TextView
            tvDocument_preference       = v.findViewById<View>(R.id.tvDocument_preference) as TextView
            im_Document_Icon        = v.findViewById<View>(R.id.im_Document_Icon) as ImageView
            imDocument_preference       = v.findViewById<View>(R.id.imDocument_preference) as ImageView

            tvQuotation_leadno   = v.findViewById<View>(R.id.tvQuotation_leadno) as TextView
            tvQuotation_name   = v.findViewById<View>(R.id.tvQuotation_name) as TextView
            tvQuotation_TrnsDate   = v.findViewById<View>(R.id.tvQuotation_TrnsDate) as TextView
            tvQuotation_address        = v.findViewById<View>(R.id.tvQuotation_address) as TextView
            tvQuotation_phone        = v.findViewById<View>(R.id.tvQuotation_phone) as TextView
            tvQuotation_Status        = v.findViewById<View>(R.id.tvQuotation_Status) as TextView
            tvQuotation_product        = v.findViewById<View>(R.id.tvQuotation_product) as TextView
            tvQuotation_preference        = v.findViewById<View>(R.id.tvQuotation_preference) as TextView
            imQuotation_preference        = v.findViewById<View>(R.id.imQuotation_preference) as ImageView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}