package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ApproveAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ApproveAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_approve2, parent, false
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


               // val htmlContent = "<table><tr><td>Bill Total </td><td> : </td><td style=\\\"text-align:right;\\\">67500.00</td></tr><tr><td>Discount </td><td> : </td><td style=\\\"text-align:right;\\\">0.00</td></tr><tr><td>OtherCharge </td><td> : </td><td style=\\\"text-align:right;\\\">0.00</td></tr><tr><td>RoundOff </td><td> : </td><td style=\\\"text-align:right;\\\">0.00</td></tr><tr><td><strong>Net Amount </strong></td><td> : </td><td style=\\\"text-align: right;\\\"><strong>67500.00</strong></td></tr></table>"

                holder.tv_values.text        = jsonObject!!.getString("Module_Name")
                holder.tv_counts.text        = jsonObject!!.getString("NoofRecords")
            //    holder.tv_values.setText(Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY))

                holder.rltv_main!!.setTag(position)
                holder.rltv_main!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "approveClick")
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
        internal var tv_values          : TextView
        internal var tv_counts          : TextView
        internal var rltv_main    : LinearLayout
        init {
            tv_values        = v.findViewById<View>(R.id.tv_values) as TextView
            tv_counts        = v.findViewById<View>(R.id.tv_counts) as TextView
            rltv_main  = v.findViewById<View>(R.id.rltv_main) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}