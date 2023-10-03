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
import com.perfect.prodsuit.Model.ModelUsageProduct
import com.perfect.prodsuit.R

class MaterialUsageAdapter (internal var context: Context, internal var mList: List<ModelUsageProduct>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "MaterialUsageAdapter"
    private var clickListener: ItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_material_usage, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")

                val empModel = mList[position]
                holder.tv_Product.text = empModel.Product
                holder.tv_qty.text = empModel.Quantity
                holder.tv_mode.text = empModel.Mode




                holder.im_delete!!.setTag(position)
                holder.im_delete!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "deleteStocks")

                })

                holder.im_edit!!.setTag(position)
                holder.im_edit!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "editStocks")

                })



            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tv_Product: TextView
        var tv_qty: TextView
        var tv_mode: TextView
        var ll_main: LinearLayout
        var im_delete: ImageView
        var im_edit: ImageView
//        var tv_emp_type: TextView


        init {
            tv_Product = v.findViewById(R.id.tv_Product) as TextView
            tv_qty = v.findViewById(R.id.tv_qty) as TextView
            tv_mode = v.findViewById(R.id.tv_mode) as TextView
            ll_main = v.findViewById(R.id.ll_main) as LinearLayout
            im_delete = v.findViewById(R.id.im_delete) as ImageView
            im_edit = v.findViewById(R.id.im_edit) as ImageView
//            tv_emp_type = v.findViewById(R.id.tv_emp_type) as TextView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}