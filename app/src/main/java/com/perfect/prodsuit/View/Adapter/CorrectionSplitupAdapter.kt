package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class CorrectionSplitupAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext: Context? = null
    internal val TAG : String = "CorrectionSplitupAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_correction_splitup, parent, false
        )
        vh = MainViewHolder(v)

        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        try {
            jsonObject = jsonArray.getJSONObject(position)

            if (holder is MainViewHolder) {

                Log.e(TAG, "onBindViewHolder   1051   ")

                holder.tv_leadNo.text          = jsonObject!!.getString("TransNo")
                holder.tv_customername.text    = jsonObject!!.getString("CorrectionPassBy")
            //    holder.tv_customermobile.text  = jsonObject!!.getString("Mobile")
                holder.tv_LeadName.text        = jsonObject!!.getString("TransTitle")
                holder.tv_FollowUpDate.text    = jsonObject!!.getString("CorrectionPassOn")



                holder.card_main!!.setTag(position)
                holder.card_main!!.setOnClickListener {
                    clickListener!!.onClick(
                        position,
                        "correctsplitClick"
                    )
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
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

        internal var tv_leadNo          : TextView
        internal var tv_customername    : TextView
        internal var tv_customermobile  : TextView
        internal var tv_LeadName        : TextView
        internal var tv_FollowUpDate    : TextView
        internal var card_main          : CardView


        init {
            tv_leadNo               = v.findViewById<View>(R.id.tv_leadNo) as TextView
            tv_customername         = v.findViewById<View>(R.id.tv_customername) as TextView
            tv_customermobile       = v.findViewById<View>(R.id.tv_customermobile) as TextView
            tv_LeadName             = v.findViewById<View>(R.id.tv_LeadName) as TextView
            tv_FollowUpDate         = v.findViewById<View>(R.id.tv_FollowUpDate) as TextView
            card_main               = v.findViewById<View>(R.id.card_main) as CardView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener

    }
}