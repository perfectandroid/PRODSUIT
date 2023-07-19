package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class Popconfirmation_lead_adapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "LeadGenerationProductListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.popup_recycle_confirm, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            Log.e(TAG, "Exception   49731   ")
            jsonObject = jsonArray.getJSONObject(position)

            Log.e(TAG, "jsonObject   497310   "+jsonObject)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")
                val pos = position + 1
//                holder.tv_SiNo.text                  = pos.toString()
                holder.tvv_Product.text = jsonObject!!.getString("ProdName")
                holder.tvv_Priority.text = jsonObject!!.getString("PriorityName")
//                holder.tvv_action.text = jsonObject!!.getString("NxtActnName")
//                holder.tvv_action_type.text = jsonObject!!.getString("ActionTypeName")
//                holder.tvv_followup_date.text = jsonObject!!.getString("NextActionDate")
               // holder.tvv_amount.text = "₹ "+jsonObject!!.getString("LgpSalesPrice")
                holder.tvv_amount.text = jsonObject!!.getString("MRP")+" / "+jsonObject!!.getString("LgpSalesPrice")

                if (jsonObject!!.getString("LgpSalesPrice").equals("")){
                    holder.tvv_amount.visibility = View.GONE
                }

                if (jsonObject!!.getString("ProdName").equals("")){
                    holder.tvv_tvCategory.text = jsonObject!!.getString("CategoryName")
//                    holder.category_prod.text = "Category"
                }else{
                    holder.tvv_tvCategory.text = jsonObject!!.getString("ProdName")
//                    holder.category_prod.text = "Product"
                }

//                holder.im_delete!!.setTag(position)
//                holder.im_delete!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "deleteArrayList")
//                })
//
//                holder.im_edit!!.setTag(position)
//                holder.im_edit!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "editArrayList")
//                })

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
        internal var tvv_amount: TextView
//        internal var category_prod: TextView

        init {

            tvv_tvCategory = v.findViewById<View>(R.id.tvv_tvCategory) as TextView
            tvv_Product = v.findViewById<View>(R.id.tvv_Product) as TextView
            tvv_Priority = v.findViewById<View>(R.id.tvv_Priority) as TextView
            tvv_amount = v.findViewById<View>(R.id.tvv_amount) as TextView
//            category_prod = v.findViewById<View>(R.id.category_prod) as TextView


        }


    }
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}