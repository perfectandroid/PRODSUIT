package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
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
            R.layout.adapter_activity_list, parent, false
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
                    holder.tvCall_name.setText(jsonObject!!.getString("CustomerName"))
                    holder.tvCall_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                    holder.tvCall_phone.setText(jsonObject!!.getString("CustomerMobile"))
                    holder.tvCall_address.setText(jsonObject!!.getString("CustomerAddress"))

                   if (jsonObject!!.getString("PendingMode").equals("3")){
                       holder.ll_Call_Icon.visibility = View.GONE
                   }
                }

                     if (jsonObject!!.getString("ID_ActionType").equals("2")){
                        holder.ll_Message.visibility = View.VISIBLE
                        holder.tvMessage_name.setText(jsonObject!!.getString("CustomerName"))
                        holder.tvMessage_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                        holder.tvMessage_phone.setText(jsonObject!!.getString("CustomerMobile"))
                        holder.tvMessage_address.setText(jsonObject!!.getString("CustomerAddress"))
                         if (jsonObject!!.getString("PendingMode").equals("3")){
                             holder.ll_Message_Icon.visibility = View.GONE
                         }
                    }

                    if (jsonObject!!.getString("ID_ActionType").equals("3")){
                        holder.ll_Meeting.visibility = View.VISIBLE
                        holder.tvMeeting_name.setText(jsonObject!!.getString("CustomerName"))
                        holder.tvMeeting_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                        holder.tvMeeting_phone.setText(jsonObject!!.getString("CustomerMobile"))
                        holder.tvMeeting_address.setText(jsonObject!!.getString("CustomerAddress"))
                        if (jsonObject!!.getString("PendingMode").equals("3")){
                            holder.ll_Meeting_Icon.visibility = View.GONE
                        }
                    }

                    if (jsonObject!!.getString("ID_ActionType").equals("4")){
                        holder.ll_Document.visibility = View.VISIBLE
                        holder.tvDocument_name.setText(jsonObject!!.getString("CustomerName"))
                        holder.tvDocument_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                        holder.tvDocument_phone.setText(jsonObject!!.getString("CustomerMobile"))
                        holder.tvDocument_address.setText(jsonObject!!.getString("CustomerAddress"))
                        if (jsonObject!!.getString("PendingMode").equals("3")){
                            holder.ll_Document_Icon.visibility = View.GONE
                        }
                    }

                    if (jsonObject!!.getString("ID_ActionType").equals("5")){
                        holder.ll_Quotation.visibility = View.VISIBLE
                        holder.tvQuotation_name.setText(jsonObject!!.getString("CustomerName"))
                        holder.tvQuotation_TrnsDate.setText(jsonObject!!.getString("TrnsDate"))
                        holder.tvQuotation_phone.setText(jsonObject!!.getString("CustomerMobile"))
                        holder.tvQuotation_address.setText(jsonObject!!.getString("CustomerAddress"))
                        if (jsonObject!!.getString("PendingMode").equals("3")){
                            holder.ll_Quotation_Icon.visibility = View.GONE
                        }
                    }


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

        internal var tvCall_name       : TextView
        internal var tvCall_TrnsDate       : TextView
        internal var tvCall_address       : TextView
        internal var tvCall_phone       : TextView

        internal var tvMessage_name    : TextView
        internal var tvMessage_TrnsDate    : TextView
        internal var tvMessage_address    : TextView
        internal var tvMessage_phone    : TextView

        internal var tvMeeting_name    : TextView
        internal var tvMeeting_TrnsDate    : TextView
        internal var tvMeeting_address    : TextView
        internal var tvMeeting_phone    : TextView

        internal var tvDocument_name   : TextView
        internal var tvDocument_TrnsDate   : TextView
        internal var tvDocument_address    : TextView
        internal var tvDocument_phone    : TextView

        internal var tvQuotation_name  : TextView
        internal var tvQuotation_TrnsDate  : TextView
        internal var tvQuotation_address    : TextView
        internal var tvQuotation_phone    : TextView

        init {

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

            tvCall_name        = v.findViewById<View>(R.id.tvCall_name) as TextView
            tvCall_TrnsDate        = v.findViewById<View>(R.id.tvCall_TrnsDate) as TextView
            tvCall_address        = v.findViewById<View>(R.id.tvCall_address) as TextView
            tvCall_phone        = v.findViewById<View>(R.id.tvCall_phone) as TextView

            tvMessage_name     = v.findViewById<View>(R.id.tvMessage_name) as TextView
            tvMessage_TrnsDate     = v.findViewById<View>(R.id.tvMessage_TrnsDate) as TextView
            tvMessage_address        = v.findViewById<View>(R.id.tvMessage_address) as TextView
            tvMessage_phone        = v.findViewById<View>(R.id.tvMessage_phone) as TextView

            tvMeeting_name     = v.findViewById<View>(R.id.tvMeeting_name) as TextView
            tvMeeting_TrnsDate     = v.findViewById<View>(R.id.tvMeeting_TrnsDate) as TextView
            tvMeeting_address        = v.findViewById<View>(R.id.tvMeeting_address) as TextView
            tvMeeting_phone        = v.findViewById<View>(R.id.tvMeeting_phone) as TextView

            tvDocument_name    = v.findViewById<View>(R.id.tvDocument_name) as TextView
            tvDocument_TrnsDate    = v.findViewById<View>(R.id.tvDocument_TrnsDate) as TextView
            tvDocument_address        = v.findViewById<View>(R.id.tvDocument_address) as TextView
            tvDocument_phone        = v.findViewById<View>(R.id.tvDocument_phone) as TextView

            tvQuotation_name   = v.findViewById<View>(R.id.tvQuotation_name) as TextView
            tvQuotation_TrnsDate   = v.findViewById<View>(R.id.tvQuotation_TrnsDate) as TextView
            tvQuotation_address        = v.findViewById<View>(R.id.tvQuotation_address) as TextView
            tvQuotation_phone        = v.findViewById<View>(R.id.tvQuotation_phone) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}