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

class FollowupTicketReportAdapter(internal var context: Context, internal var jsonArray: JSONArray):


    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "FollowupTicketReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_followupticket_report, parent, false
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
//
//                holder.tv_SiNo.text            = pos.toString()
//                holder.tv_LeadNo.text          = jsonObject!!.getString("LeadNo")
//                holder.tv_LeadDate.text        = jsonObject!!.getString("LeadDate")
//                holder.tv_Action.text          = jsonObject!!.getString("NextAction")
//                holder.tv_ActionType.text      = jsonObject!!.getString("NextActionDate")
//                holder.tv_Customer.text        = jsonObject!!.getString("Customer")
//                holder.tv_Branch.text          = jsonObject!!.getString("BRANCH")
//                holder.tv_Product.text         = jsonObject!!.getString("Product")
//
//                holder.tv_FollowUpMethod.text  = jsonObject!!.getString("FollowUpMethod")
//                holder.tv_CompleteDate.text    = jsonObject!!.getString("CompletedDate")
//                holder.tv_AssignedTo.text      = jsonObject!!.getString("AssignedTo")
//
//                holder.tv_DueDays.text      = jsonObject!!.getString("DueDays")
//                holder.tv_Status.text     = jsonObject!!.getString("Status")
//                holder.tv_Priority.text     = jsonObject!!.getString("Priority")
////                holder.tv_Remarks.text      = jsonObject!!.getString("Remarks")
//
////                holder.llDistrict!!.setTag(position)
////                holder.llDistrict!!.setOnClickListener(View.OnClickListener {
////                    clickListener!!.onClick(position, "districtdetail")
////                })


                holder.txtLead.text          = jsonObject!!.getString("LeadNo")+" ("+jsonObject!!.getString("LeadDate")+")"
                holder.txtCustomer.text      = jsonObject!!.getString("Customer")
                holder.txtCollectedBy.text      = jsonObject!!.getString("LgCollectedBy")
                var  strCat = ""
                var  strProd = jsonObject!!.getString("Product")
                if (!strProd.equals("")){
                    holder.txtProduct.text = jsonObject!!.getString("Category")+"/"+jsonObject!!.getString("Product")
                }else{
                    holder.txtProduct.text = jsonObject!!.getString("Category")
                }

                holder.ll_followList!!.setTag(position)
                holder.ll_followList!!.setOnClickListener(View.OnClickListener {
                    Log.e(TAG,"newListClick   5091")
                    clickListener!!.onClick(
                        position,
                        "followListClick"
                    )
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
//        internal var tv_SiNo             : TextView
//        internal var tv_LeadNo           : TextView
//        internal var tv_LeadDate         : TextView
//        internal var tv_Action           : TextView
//        internal var tv_ActionType       : TextView
//        internal var tv_Customer         : TextView
//        internal var tv_Branch           : TextView
//        internal var tv_Product          : TextView
//        internal var tv_FollowUpMethod   : TextView
//        internal var tv_CompleteDate     : TextView
//        internal var tv_AssignedTo       : TextView
//        internal var tv_DueDays          : TextView
//        internal var tv_Status           : TextView
//        internal var tv_Priority         : TextView
        //        internal var llDistrict      : LinearLayout


        internal var txtLead             : TextView
        internal var txtCustomer         : TextView
        internal var txtProduct          : TextView
        internal var txtCollectedBy      : TextView
        internal var ll_followList       : LinearLayout


        init {
//            tv_SiNo            = v.findViewById<View>(R.id.tv_SiNo) as TextView
//            tv_LeadNo          = v.findViewById<View>(R.id.tv_LeadNo) as TextView
//            tv_LeadDate        = v.findViewById<View>(R.id.tv_LeadDate) as TextView
//            tv_Customer        = v.findViewById<View>(R.id.tv_Customer) as TextView
//            tv_Branch          = v.findViewById<View>(R.id.tv_Branch) as TextView
//            tv_Product         = v.findViewById<View>(R.id.tv_Product) as TextView
//            tv_Action          = v.findViewById<View>(R.id.tv_Action) as TextView
//            tv_ActionType      = v.findViewById<View>(R.id.tv_ActionType) as TextView
//            tv_FollowUpMethod  = v.findViewById<View>(R.id.tv_FollowUpMethod) as TextView
//            tv_CompleteDate    = v.findViewById<View>(R.id.tv_CompleteDate) as TextView
//            tv_AssignedTo      = v.findViewById<View>(R.id.tv_AssignedTo) as TextView
//            tv_DueDays         = v.findViewById<View>(R.id.tv_DueDays) as TextView
//            tv_Status          = v.findViewById<View>(R.id.tv_Status) as TextView
//            tv_Priority        = v.findViewById<View>(R.id.tv_Priority) as TextView
//            llDistrict  = v.findViewById<View>(R.id.llDistrict) as LinearLayout

            txtLead            = v.findViewById<View>(R.id.txtLead) as TextView
            txtCustomer        = v.findViewById<View>(R.id.txtCustomer) as TextView
            txtProduct         = v.findViewById<View>(R.id.txtProduct) as TextView
            txtCollectedBy     = v.findViewById<View>(R.id.txtCollectedBy) as TextView
            ll_followList      = v.findViewById<View>(R.id.ll_followList) as LinearLayout


        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }



}