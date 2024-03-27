package com.perfect.prodsuit.View.Adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AssignedTicketsActivity
import org.json.JSONArray
import org.json.JSONObject


class SummaryReportDetailAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray

):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "SummaryReportDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    private var progressDialog: ProgressDialog? = null
    lateinit var asgndtktListArrayList: JSONArray
    lateinit var asgndtktListSort: JSONArray
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_summary_report, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {


            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"  summaryreport   "+jsonArray)
                val pos = position+1

             //   holder.tv_si.text        = pos.toString()
//                holder.tv_Employee.text  = jsonObject!!.getString("employee")
//                holder.tv_Role.text      = jsonObject!!.getString("role")

                holder.tv_Customer.text  = jsonObject!!.getString("Employee")
                holder.txtv_date.text      = jsonObject!!.getString("LeadDate") // Role
                holder.tv_product.text      = "Product Name : "+jsonObject!!.getString("Product")
                holder.tv_status.text      = "Next Action : "+jsonObject!!.getString("Nextaction")
                holder.tv_leadno.text      = jsonObject!!.getString("LeadNo")
                holder.txtv_collectedby.text      = "Referred By : "+jsonObject!!.getString("AssignedTo")
                holder.txtv_preference.text      = jsonObject!!.getString("LeadStatusName")
                holder.tv_category.text      = "Category : "+jsonObject!!.getString("Category")
                holder.tv_actntype.text      = "Action Type : "+jsonObject!!.getString("ActionType")


               /* holder.im_delete!!.setTag(position)
                holder.im_delete!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(
                        position,
                        "deleteArrayList"
                    )
                })*/


           /*     holder.im_edit!!.setTag(position)
                holder.im_edit!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(
                        position,
                        "editArrayList"
                    )
                })

                holder.im_asgned!!.setTag(position)
                holder.im_asgned!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    jsonObject = jsonArray.getJSONObject(position)
                    var fkemployee= jsonObject!!.getString("ID_Employee")
                    var date=dateattend
                    val i = Intent(context, AssignedTicketsActivity::class.java)
                    i.putExtra("FK_Employee",fkemployee)
                    i.putExtra("Date",date)
                    context.startActivity(i)

                })*/



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

        internal var tv_Customer   : TextView
      //  internal var tv_nextdate   : TextView
        internal var tv_product   : TextView
        internal var tv_status   : TextView
        internal var txtv_date   : TextView
        internal var tv_leadno   : TextView
        internal var txtv_collectedby   : TextView
        internal var txtv_preference   : TextView
        internal var tv_category   : TextView
        internal var tv_actntype   : TextView

        init {
           /* tv_si      = v.findViewById<View>(R.id.tv_si) as TextView
            tv_Employee    = v.findViewById<View>(R.id.tv_Employee) as TextView
            tv_Role     = v.findViewById<View>(R.id.tv_Role) as TextView
            im_delete     = v.findViewById<View>(R.id.im_delete) as ImageView
            im_edit     = v.findViewById<View>(R.id.im_edit) as ImageView
            im_asgned = v.findViewById<View>(R.id.im_asgned) as ImageView*/
         //   tv_nextdate  = v.findViewById<View>(R.id.tv_nextdate) as TextView
            tv_Customer= v.findViewById<View>(R.id.tv_custmr) as TextView
            tv_product= v.findViewById<View>(R.id.tv_product) as TextView
            tv_status= v.findViewById<View>(R.id.tv_nxtaction) as TextView
            txtv_date= v.findViewById<View>(R.id.txtv_date) as TextView
            tv_leadno= v.findViewById<View>(R.id.tv_leadno) as TextView
            txtv_collectedby= v.findViewById<View>(R.id.txtv_collectedby) as TextView
            txtv_preference= v.findViewById<View>(R.id.txtv_preference) as TextView
            tv_category= v.findViewById<View>(R.id.tv_category) as TextView
            tv_actntype= v.findViewById<View>(R.id.tv_actntype) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}


