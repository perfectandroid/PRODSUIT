package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.MyActivitysActionCountModelList
import com.perfect.prodsuit.R

class MyActivitysActionCountAdapter(internal var context: Context,  internal var actionCount: List<MyActivitysActionCountModelList>,internal var myActivityActionCountMode: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "MyActivitysActionCountAdapter"
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_activity_actioncount, parent, false)
        vh = MainViewHolder(v)
        return vh

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {

            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   213331   "+myActivityActionCountMode)
                val pos = position+1

                val empModel = actionCount[position]
                holder.tv_ActionName.text           = empModel.ActionName

                if (myActivityActionCountMode == position){
                    holder.tv_ActionName.setTextColor(ContextCompat.getColor(context, R.color.completed_color2))
                }else{
                    holder.tv_ActionName.setTextColor(Color.WHITE)
                }

                holder.ll_actiondetails.setTag(position)
                holder.ll_actiondetails.setOnClickListener {
                    Config.disableClick(it)
                    Log.e(TAG,"213332   "+position)
                    myActivityActionCountMode = position
                    notifyDataSetChanged()
                    clickListener!!.onClick(position, "actionCountDetailClick")
                }


            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }


    override fun getItemCount(): Int {
        return actionCount.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tv_ActionName: TextView
        internal var ll_actiondetails: LinearLayout
//        internal var tv_MeasurementType: TextView

        init {
            tv_ActionName = v.findViewById<View>(R.id.tv_ActionName) as TextView
            ll_actiondetails = v.findViewById<View>(R.id.ll_actiondetails) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}