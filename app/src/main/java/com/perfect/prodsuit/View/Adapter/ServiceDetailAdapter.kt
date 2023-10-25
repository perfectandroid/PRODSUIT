package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.AddServiceDetailMode
import com.perfect.prodsuit.R

class ServiceDetailAdapter (internal var context: Context, internal var mList: List<AddServiceDetailMode>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceDetailAdapter"
    //    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_detail, parent, false
        )
        vh = MainViewHolder(v)
        return vh

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {

            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   105100   ")
                val pos = position+1

                val empModel = mList[position]
                holder.tv_service.text           = empModel.Service
                holder.chk_box!!.setTag(position)
                holder.chk_box!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "areadetail")
                    if (holder.chk_box.isChecked){
                        mList[position].isChecked = true
                    }else{
                        mList[position].isChecked = false
                    }
                })

            }


        }
        catch (e: Exception) {
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

        internal var tv_service             : TextView
        internal var ll_serviceDetail                        : LinearLayout
        internal var chk_box                       : CheckBox

        init {

            tv_service          = v.findViewById<View>(R.id.tv_service)      as TextView
            ll_serviceDetail          = v.findViewById<View>(R.id.ll_serviceDetail)      as LinearLayout
            chk_box          = v.findViewById<View>(R.id.chk_box)      as CheckBox

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}