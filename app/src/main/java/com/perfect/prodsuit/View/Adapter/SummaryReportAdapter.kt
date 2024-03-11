package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.LeadSummaryDetailReportActivity
import org.json.JSONArray
import org.json.JSONObject

class SummaryReportAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    internal var strFromdate: String,
    internal var strTodate: String,
    internal var ID_Product: String?,
    internal var ID_Category: String?,
    internal var ID_Branch: String?,
    internal var ID_Employee: String?
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "SummaryReportAdapter"
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
                holder.balance.text   = jsonObject!!. getString("Balance")
                ID_Employee=jsonObject!!. getString("ID")





                Log.e(TAG,"Details "+strFromdate+"\n"+ID_Employee +"\n"+ID_Category)

                holder.opening!!.setTag(position)
                holder.opening!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    val intent = Intent(context, LeadSummaryDetailReportActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("SubMode","1")
                    intent.putExtra("strFromdate",strFromdate)
                    intent.putExtra("strTodate",strTodate)
                    intent.putExtra("ID_Product",ID_Product)
                    intent.putExtra("ID_Category",ID_Category)
                    intent.putExtra("ID_Branch",ID_Branch)
                    intent.putExtra("ID_Employee",ID_Employee)
                    context.startActivity(intent)
                })



                holder.news!!.setTag(position)
                holder.news!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    val intent = Intent(context, LeadSummaryDetailReportActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("SubMode","2")
                    intent.putExtra("strFromdate",strFromdate)
                    intent.putExtra("strTodate",strTodate)
                    intent.putExtra("ID_Product",ID_Product)
                    intent.putExtra("ID_Category",ID_Category)
                    intent.putExtra("ID_Branch",ID_Branch)
                    intent.putExtra("ID_Employee",ID_Employee)
                    context.startActivity(intent)

                })
                holder.closed!!.setTag(position)
                holder.closed!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)

                    val intent = Intent(context, LeadSummaryDetailReportActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("SubMode","3")
                    intent.putExtra("strFromdate",strFromdate)
                    intent.putExtra("strTodate",strTodate)
                    intent.putExtra("ID_Product",ID_Product)
                    intent.putExtra("ID_Category",ID_Category)
                    intent.putExtra("ID_Branch",ID_Branch)
                    intent.putExtra("ID_Employee",ID_Employee)
                    context.startActivity(intent)
                })
                holder.losed!!.setTag(position)
                holder.losed!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    val intent = Intent(context, LeadSummaryDetailReportActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("SubMode","4")
                    intent.putExtra("strFromdate",strFromdate)
                    intent.putExtra("strTodate",strTodate)
                    intent.putExtra("ID_Product",ID_Product)
                    intent.putExtra("ID_Category",ID_Category)
                    intent.putExtra("ID_Branch",ID_Branch)
                    intent.putExtra("ID_Employee",ID_Employee)

                    context.startActivity(intent)
                })
                holder.balance!!.setTag(position)
                holder.balance!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    val intent = Intent(context, LeadSummaryDetailReportActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("SubMode","5")
                    intent.putExtra("strFromdate",strFromdate)
                    intent.putExtra("strTodate",strTodate)
                    intent.putExtra("ID_Product",ID_Product)
                    intent.putExtra("ID_Category",ID_Category)
                    intent.putExtra("ID_Branch",ID_Branch)
                    intent.putExtra("ID_Employee",ID_Employee)
                    context.startActivity(intent)
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
   /* fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }*/
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}