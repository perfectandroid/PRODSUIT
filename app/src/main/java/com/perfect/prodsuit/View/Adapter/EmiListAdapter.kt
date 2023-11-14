package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class EmiListAdapter (internal var context: Context, internal var jsonArray: JSONArray, internal var SubMode: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "EmiListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_emi_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {

            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.i("responserere","onBindView   ")
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                if (SubMode.equals("1")){
                    holder.llEmiLeft.setBackgroundColor(context.getColor(R.color.color_common1));
                }
                if (SubMode.equals("2")){
                    holder.llEmiLeft.setBackgroundColor(context.getColor(R.color.color_common2));
                }
                if (SubMode.equals("3")){
                    holder.llEmiLeft.setBackgroundColor(context.getColor(R.color.color_common3));
                }

                holder.tv_EmiNo.text        = jsonObject!!.getString("EMINo")
                holder.tv_Customer.text        = jsonObject!!.getString("Customer")
                holder.tv_Mobile.text        = jsonObject!!.getString("Mobile")
                holder.tv_Product.text        = jsonObject!!.getString("Product")
                holder.tv_Finance_Plan.text        = jsonObject!!.getString("FinancePlan")
                holder.tv_Next_EMI.text        = jsonObject!!.getString("NextEMIDate")
                holder.tv_Due_Date.text        = jsonObject!!.getString("DueDate")
                holder.tv_DueAmount.text        = Config.changeTwoDecimel(jsonObject!!.getString("DueAmount"))
                holder.tv_Balance.text        = Config.changeTwoDecimel(jsonObject!!.getString("Balance"))
                holder.tv_Area.text        = jsonObject!!.getString("Area")


                holder.llEmiList!!.setTag(position)
                holder.llEmiList!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "EmiList"
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tv_EmiNo          : TextView
        internal var tv_Customer          : TextView
        internal var tv_Mobile          : TextView
        internal var tv_Product          : TextView
        internal var tv_Finance_Plan          : TextView
        internal var tv_Next_EMI          : TextView
        internal var tv_Due_Date          : TextView
        internal var tv_DueAmount          : TextView
        internal var tv_Balance          : TextView
        internal var tv_Area          : TextView
//        internal var im_Channel          : ImageView
//        internal var im_Priority          : ImageView
//        internal var im_Status          : ImageView
//        internal var im_edit          : ImageView
//        //        internal var txtsino          : TextView
//        internal var ll_employee    : LinearLayout
//        internal var ll_status    : LinearLayout
        internal var llEmiLeft    : LinearLayout
        internal var llEmiList    : LinearLayout
        init {
            tv_EmiNo        = v.findViewById<View>(R.id.tv_EmiNo) as TextView
            tv_Customer        = v.findViewById<View>(R.id.tv_Customer) as TextView
            tv_Mobile        = v.findViewById<View>(R.id.tv_Mobile) as TextView
            tv_Product        = v.findViewById<View>(R.id.tv_Product) as TextView
            tv_Finance_Plan        = v.findViewById<View>(R.id.tv_Finance_Plan) as TextView
            tv_Next_EMI        = v.findViewById<View>(R.id.tv_Next_EMI) as TextView
            tv_Due_Date        = v.findViewById<View>(R.id.tv_Due_Date) as TextView
            tv_DueAmount        = v.findViewById<View>(R.id.tv_DueAmount) as TextView
            tv_Balance        = v.findViewById<View>(R.id.tv_Balance) as TextView
            tv_Area        = v.findViewById<View>(R.id.tv_Area) as TextView
//
//            im_Channel        = v.findViewById<View>(R.id.im_Channel) as ImageView
//            im_Priority        = v.findViewById<View>(R.id.im_Priority) as ImageView
//            im_Status        = v.findViewById<View>(R.id.im_Status) as ImageView
//            im_edit        = v.findViewById<View>(R.id.im_edit) as ImageView
////            txtsino        = v.findViewById<View>(R.id.txtsino) as TextView
//            ll_employee       = v.findViewById<View>(R.id.ll_employee) as LinearLayout
//            ll_status       = v.findViewById<View>(R.id.ll_status) as LinearLayout
            llEmiLeft       = v.findViewById<View>(R.id.llEmiLeft) as LinearLayout
            llEmiList       = v.findViewById<View>(R.id.llEmiList) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}