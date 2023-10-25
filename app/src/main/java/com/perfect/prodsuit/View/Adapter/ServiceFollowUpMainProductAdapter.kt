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
import com.perfect.prodsuit.Model.LabelPartsreplaceModel
import com.perfect.prodsuit.Model.ServicePartsReplacedModel
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceFollowUpMainProductAdapter(internal var context: Context, internal var mList: List<LabelPartsreplaceModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "ServiceFollowUpMainProductAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    var row_index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_header_main_product, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {



            if (holder is MainViewHolder) {

                val ItemsModel = mList[position]

                val pos = position+1
                holder.txt_mainProduct.text        = ItemsModel.MainProduct
                holder.llProduct_Main!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "MainProductList")


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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var txt_mainProduct        : TextView
        internal var llProduct_Main        : LinearLayout

        init {

            txt_mainProduct               = v.findViewById<View>(R.id.txt_mainProduct) as TextView
            llProduct_Main               = v.findViewById<View>(R.id.llProduct_Main) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}