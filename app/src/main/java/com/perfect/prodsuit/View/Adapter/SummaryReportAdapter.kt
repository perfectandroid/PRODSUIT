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

class SummaryReportAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ActionListTicketReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_lead_summary_report, parent, false
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
//                holder.tv_SiNo.text         = pos.toString()
                holder.text.text       = jsonObject!!.getString("GroupName")
                holder.opening.text     = jsonObject!!.getString("Opening")
                holder.news.text     = jsonObject!!.getString("New")
                holder.closed.text      = jsonObject!!.getString("Closed")
                holder.losed.text       = jsonObject!!.getString("Lost")
                holder.balance.text   = jsonObject!!.getString("Balance")

//                holder.llDistrict!!.setTag(position)
//                holder.llDistrict!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "districtdetail")
//                })
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
        internal var balance            : TextView
        internal var losed          : TextView
        internal var closed        : TextView
        internal var news        : TextView
        internal var opening         : TextView
        internal var text          : TextView
        init {
    balance        = v.findViewById<View>(R.id.balance) as TextView
    losed        = v.findViewById<View>(R.id.losed) as TextView
    closed      = v.findViewById<View>(R.id.closed) as TextView
    news      = v.findViewById<View>(R.id.news) as TextView
    opening       = v.findViewById<View>(R.id.opening) as TextView
    text        = v.findViewById<View>(R.id.text) as TextView
        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }
}