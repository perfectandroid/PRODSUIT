package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class NewListTicketReportAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "NewListTicketReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_newlistticket_report, parent, false
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
//                holder.tv_LeadNo.text       = jsonObject!!.getString("LeadNo")
//                holder.tv_LeadDate.text     = jsonObject!!.getString("LeadDate")
//                holder.tv_Customer.text     = jsonObject!!.getString("Customer")
//                holder.tv_Product.text      = jsonObject!!.getString("Product")
//                holder.tv_Branch.text       = jsonObject!!.getString("Branch")
//                holder.tv_CollectedBy.text  = jsonObject!!.getString("CollectedBy")
//                holder.tv_Status.text       = jsonObject!!.getString("CurrentStatus")
//                holder.tv_Priority.text     = jsonObject!!.getString("Priority")

//                holder.llDistrict!!.setTag(position)
//                holder.llDistrict!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "districtdetail")
//                })

                holder.txtLead.text = "Lead No : "+jsonObject!!.getString("LeadNo")
                holder.tv_date.text = "Date : "+jsonObject!!.getString("LeadDate")
                holder.tv_priority.text      = jsonObject!!.getString("Priority")
                holder.txtCustomer.text      = "Customer : "+jsonObject!!.getString("Customer")
                holder.txtCollectedBy.text      = "Collected By : "+jsonObject!!.getString("LgCollectedBy")
                var  strCat = ""
                var  strProd = "Product : "+jsonObject!!.getString("Product")
                holder.txtProduct.text = strProd
//                if (!strProd.equals("")){
//                    holder.txtProduct.text = jsonObject!!.getString("Category")+"/"+jsonObject!!.getString("Product")
//                }else{
//                    holder.txtProduct.text = jsonObject!!.getString("Category")
//                }


                holder.ll_newList!!.setTag(position)
                holder.ll_newList!!.setOnClickListener(View.OnClickListener {
                    Log.e(TAG,"newListClick   5091")
                    clickListener!!.onClick(
                        position,
                        "newListClick"
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
//        internal var tv_SiNo            : TextView
//        internal var tv_LeadNo          : TextView
//        internal var tv_LeadDate        : TextView
//        internal var tv_Customer        : TextView
//        internal var tv_Product         : TextView
//        internal var tv_Branch          : TextView
//        internal var tv_CollectedBy     : TextView
//        internal var tv_Status          : TextView
//        internal var tv_Priority        : TextView

        internal var txtLead             : TextView
        internal var txtCustomer         : TextView
        internal var txtProduct          : TextView
        internal var tv_priority          : TextView
        internal var txtCollectedBy      : TextView
        internal var tv_date      : TextView
        internal var ll_newList          : CardView

        //        internal var llDistrict      : LinearLayout
        init {
//            tv_SiNo          = v.findViewById<View>(R.id.tv_SiNo) as TextView
//            tv_LeadNo        = v.findViewById<View>(R.id.tv_LeadNo) as TextView
//            tv_LeadDate      = v.findViewById<View>(R.id.tv_LeadDate) as TextView
//            tv_Customer      = v.findViewById<View>(R.id.tv_Customer) as TextView
//            tv_Product       = v.findViewById<View>(R.id.tv_Product) as TextView
//            tv_Branch        = v.findViewById<View>(R.id.tv_Branch) as TextView
//            tv_CollectedBy   = v.findViewById<View>(R.id.tv_CollectedBy) as TextView
//            tv_Status        = v.findViewById<View>(R.id.tv_Status) as TextView
//            tv_Priority      = v.findViewById<View>(R.id.tv_Priority) as TextView
//            llDistrict  = v.findViewById<View>(R.id.llDistrict) as LinearLayout

            txtLead            = v.findViewById<View>(R.id.txtLead) as TextView
            txtCustomer        = v.findViewById<View>(R.id.txtCustomer) as TextView
            txtProduct         = v.findViewById<View>(R.id.txtProduct) as TextView
            txtCollectedBy     = v.findViewById<View>(R.id.txtCollectedBy) as TextView
            tv_date     = v.findViewById<View>(R.id.tv_date) as TextView
            tv_priority     = v.findViewById<View>(R.id.tv_priority) as TextView
            ll_newList         = v.findViewById<View>(R.id.ll_newList) as CardView


        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}