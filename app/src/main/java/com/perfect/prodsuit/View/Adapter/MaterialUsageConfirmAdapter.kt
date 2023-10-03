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
import com.perfect.prodsuit.Model.ModelStockTransferDetails
import com.perfect.prodsuit.Model.ModelUsageProduct
import com.perfect.prodsuit.R

class MaterialUsageConfirmAdapter(internal var context: Context, internal var mList: List<ModelUsageProduct>,internal var mode : String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    internal val TAG : String = "MaterialUsageConfirmAdapter"
    val data = ArrayList<ModelUsageProduct>()
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_confirm_material_usage, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")



                val empModel = mList[position]
                holder.tv_confirm_product.text = empModel.Product
                holder.tv_confirm_qty.text = empModel.Quantity
                holder.tv_confirm_mode.text = empModel.Mode

                if (mode.equals("1")){
                    holder.ll_mode.visibility = View.GONE
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
        var tv_confirm_product: TextView
        var tv_confirm_qty: TextView
        var tv_confirm_mode: TextView
        var ll_mode: LinearLayout

        init {
            tv_confirm_product = v.findViewById(R.id.tv_confirm_product) as TextView
            tv_confirm_qty = v.findViewById(R.id.tv_confirm_qty) as TextView
            tv_confirm_mode = v.findViewById(R.id.tv_confirm_mode) as TextView

            ll_mode = v.findViewById(R.id.ll_mode) as LinearLayout


        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }
}