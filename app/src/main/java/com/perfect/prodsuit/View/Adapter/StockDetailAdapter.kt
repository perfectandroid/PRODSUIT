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
import com.perfect.prodsuit.Model.ModelStockTransferDetails
import com.perfect.prodsuit.R

class StockDetailAdapter(internal var context: Context, internal var mList: List<ModelStockTransferDetails>,internal var mode : String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "StockDetailAdapter"
    val data = ArrayList<ModelStockTransferDetails>()
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_stock_details, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   "+mode)

                val empModel = mList[position]
                holder.tv_stock.text = empModel.StockMode
                holder.tv_product.text = empModel.Product
                holder.tv_qty.text = empModel.Quantity+" / "+empModel.StatndByQuantity

                if (mode.equals("0")){
                    holder.tv_stock.visibility = View.GONE
                }
                if (mode.equals("1")){
                    holder.tv_stock.visibility = View.VISIBLE
                }

                holder.im_delete!!.setTag(position)
                holder.im_delete!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "deleteStocks"
                    )

                })

                holder.im_edit!!.setTag(position)
                holder.im_edit!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "editStocks"
                    )

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
        var tv_stock: TextView
        var tv_product: TextView
        var tv_qty: TextView
        var tv_stndqty: TextView
        var ll_main: LinearLayout
        var im_delete: ImageView
        var im_edit: ImageView
//        var tv_emp_type: TextView


        init {
            tv_stock = v.findViewById(R.id.tv_stock) as TextView
            tv_product = v.findViewById(R.id.tv_product) as TextView
            tv_qty = v.findViewById(R.id.tv_qty) as TextView
            tv_stndqty = v.findViewById(R.id.tv_stndqty) as TextView
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