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
    internal var ID_Employee: String?,
    internal var ID_AssignedEmployee: String?
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

                var openings =jsonObject!!.getString("Opening")
                var newses =jsonObject!!.getString("New")
                var closes =jsonObject!!.getString("Closed")
                var loses =jsonObject!!.getString("Lost")
                var bal =jsonObject!!.getString("Balance")
              //  var bal  ="125550"

                var longval: Double
                var longval1: Double
                var longval2: Double
                var longval3: Double
                var longval4: Double

                if (openings.contains(",")) {
                    openings = openings.replace(",", "")
                }
                longval = openings.toDouble()
                val formattedStringopen: String =
                    Config.getDecimelFormateForText(longval).toString()


                if (newses.contains(",")) {
                    newses = openings.replace(",", "")
                }
                longval1 = newses.toDouble()
                val formattedStringnews: String =
                    Config.getDecimelFormateForText(longval1).toString()


                if (closes.contains(",")) {
                    closes = closes.replace(",", "")
                }
                longval2 = closes.toDouble()
                val formattedStringcloses: String =
                    Config.getDecimelFormateForText(longval2).toString()


                if (loses.contains(",")) {
                    loses = loses.replace(",", "")
                }
                longval3 = loses.toDouble()
                val formattedStringloses: String =
                    Config.getDecimelFormateForText(longval3).toString()

                if (bal.contains(",")) {
                    bal = bal.replace(",", "")
                }
                longval4 = bal.toDouble()
                val formattedStringbal: String =
                    Config.getDecimelFormateForText(longval4).toString()



                //holder.opening.text     = jsonObject!!.getString("Opening")
                holder.opening.text     =formattedStringopen
            //    holder.news.text     = jsonObject!!.getString("New")
                holder.news.text     = formattedStringnews

              //  holder.closed.text      = jsonObject!!.getString("Closed")
                holder.closed.text      = formattedStringcloses

             //   holder.losed.text       = jsonObject!!.getString("Lost")
                holder.losed.text       = formattedStringloses

             //  holder.balance.text   = jsonObject!!. getString("Balance")
                holder.balance.text   = formattedStringbal


               // ID_Employee=jsonObject!!. getString("ID")


                val jsonObject1: JSONObject = jsonArray.getJSONObject(position)
                var emp = jsonObject1.getString("ID")


                Log.e(TAG,"Details "+strFromdate+"\n"+"EMP : "+emp +"\n"+ID_Category)

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
                    intent.putExtra("ID_Employee",emp)
                    intent.putExtra("assigned",ID_AssignedEmployee)


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
                    intent.putExtra("ID_Employee",emp)
                    intent.putExtra("assigned",ID_AssignedEmployee)
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
                    intent.putExtra("ID_Employee",emp)
                    intent.putExtra("assigned",ID_AssignedEmployee)
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
                    intent.putExtra("ID_Employee",emp)
                    intent.putExtra("assigned",ID_AssignedEmployee)
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
                    intent.putExtra("ID_Employee",emp)
                    intent.putExtra("assigned",ID_AssignedEmployee)
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