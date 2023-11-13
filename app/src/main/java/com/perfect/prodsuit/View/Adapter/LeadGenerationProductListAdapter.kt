package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class LeadGenerationProductListAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "LeadGenerationProductListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.multiple_product_view, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            Log.e(TAG, "Exception   49731   ")
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")
                val pos = position + 1
//                holder.tv_SiNo.text                  = pos.toString()
                holder.tvv_tvCategory.text = jsonObject!!.getString("CategoryName")
                holder.tvv_Product.text = jsonObject!!.getString("ProdName")
                holder.tvv_Priority.text = jsonObject!!.getString("PriorityName")
                holder.tvv_action.text = jsonObject!!.getString("NxtActnName")
                holder.tvv_action_type.text = jsonObject!!.getString("ActionTypeName")
                holder.tvv_followup_date.text = jsonObject!!.getString("NextActionDate")
                holder.tvv_amount.text = "₹ "+jsonObject!!.getString("LgpSalesPrice")
                holder.tvv_Model.text = jsonObject!!.getString("ProjectName")
                holder.tvv_mrp.text = "₹ "+jsonObject!!.getString("MRP")

                Log.e(TAG, "ID_Status 0000777   " + jsonObject!!.getString("ID_Status"))

                if (jsonObject!!.getString("ID_Status").equals("1")){

                    Log.e(TAG, "ID_Status 0000777  12 " + jsonObject!!.getString("ID_Status"))

                    holder.tvv_tvCategory.visibility = View.VISIBLE
                    holder.tvv_Product.visibility = View.VISIBLE
                    holder.tvv_Priority.visibility = View.VISIBLE
                    holder.tvv_action.visibility = View.VISIBLE
                    holder.tvv_action_type.visibility = View.VISIBLE
                    holder.tvv_followup_date.visibility = View.VISIBLE
                    holder.tvv_amount.visibility = View.VISIBLE
                    holder.tvv_Model.visibility = View.VISIBLE
                }else{

                    Log.e(TAG, "ID_Status 0000777 123  " + jsonObject!!.getString("ID_Status"))

                    holder.tvv_tvCategory.visibility = View.VISIBLE
                    holder.tvv_Product.visibility = View.VISIBLE
                    holder.tvv_Priority.visibility = View.VISIBLE
                    holder.tvv_action.visibility = View.VISIBLE
                    holder.tvv_amount.visibility = View.VISIBLE
                    holder.tvv_Model.visibility = View.VISIBLE
                    holder.llfollowup.visibility = View.GONE
                    holder.llactionType.visibility = View.GONE
                }
//
//                if (jsonObject!!.getString("MRP").equals("")){
//                    holder.tvv_mrp.visibility = View.GONE
//                }
//                if (jsonObject!!.getString("tvv_amount").equals("")){
//                    holder.tvv_amount.visibility = View.GONE
//                }


                holder.im_delete!!.setTag(position)
                holder.im_delete!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "deleteArrayList"
                    )
                })

                holder.im_edit!!.setTag(position)
                holder.im_edit!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "editArrayList"
                    )
                })

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   4973   " + e.toString())
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

        internal var tvv_tvCategory: TextView
        internal var tvv_Product: TextView
        internal var tvv_Priority: TextView
        internal var tvv_action: TextView
        internal var tvv_action_type: TextView
        internal var tvv_followup_date: TextView
        internal var im_edit: ImageView
        internal var im_delete: ImageView
        internal var tvv_amount: TextView
        internal var tvv_Model: TextView
        internal var tvv_mrp: TextView
        internal var llfollowup: LinearLayout
        internal var llactionType: LinearLayout

        init {

            tvv_tvCategory = v.findViewById<View>(R.id.tvv_tvCategory) as TextView
            tvv_Product = v.findViewById<View>(R.id.tvv_Product) as TextView
            tvv_Priority = v.findViewById<View>(R.id.tvv_Priority) as TextView
            tvv_action = v.findViewById<View>(R.id.tvv_action) as TextView
            tvv_action_type = v.findViewById<View>(R.id.tvv_action_type) as TextView
            tvv_followup_date = v.findViewById<View>(R.id.tvv_followup_date) as TextView
            im_edit = v.findViewById<View>(R.id.im_edit) as ImageView
            im_delete = v.findViewById<View>(R.id.im_delete) as ImageView
            tvv_amount = v.findViewById<View>(R.id.tvv_amount) as TextView
            tvv_Model = v.findViewById<View>(R.id.tvv_Model) as TextView
            tvv_mrp = v.findViewById<View>(R.id.tvv_mrp) as TextView
            llfollowup = v.findViewById<View>(R.id.llfollowup) as LinearLayout
            llactionType = v.findViewById<View>(R.id.llactionType) as LinearLayout


        }


    }
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}