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

class StockTransConfirmAdapter (internal var context: Context, internal var mList: List<ModelStockTransferDetails>,internal var mode : String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "StockDetailAdapter"
    val data = ArrayList<ModelStockTransferDetails>()
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_stock_trans_confirm, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")



                val empModel = mList[position]
                holder.tv_confirm_stock.text = empModel.StockMode
                holder.tv_confirm_product.text = empModel.Product
                holder.tv_confirm_qty.text = empModel.Quantity+" / "+empModel.StatndByQuantity

                if (mode.equals("0")){
                    holder.tv_confirm_stock.visibility = View.GONE
                }

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
        var tv_confirm_stock: TextView
        var tv_confirm_product: TextView
        var tv_confirm_qty: TextView
        var tv_confirm_stndqty: TextView


        init {
            tv_confirm_stock = v.findViewById(R.id.tv_confirm_stock) as TextView
            tv_confirm_product = v.findViewById(R.id.tv_confirm_product) as TextView
            tv_confirm_qty = v.findViewById(R.id.tv_confirm_qty) as TextView
            tv_confirm_stndqty = v.findViewById(R.id.tv_confirm_stndqty) as TextView

        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }
}