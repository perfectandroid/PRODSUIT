package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelMesurementDetails
import com.perfect.prodsuit.R


class MeasurementShowAdapter (internal var context: Context, internal var mList: ArrayList<ModelMesurementDetails>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "MeasurementShowAdapter"
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_measurment_show, parent, false
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
                holder.tv_WorkType.text           = empModel.WorkType
                holder.tv_MeasurementType.text    = empModel.MeasurementUnit
                holder.tv_Value.text              = empModel.Value
                holder.tv_Unit.text               = empModel.Unit

                holder.im_edit.setTag(position)
                holder.im_edit.setOnClickListener {
                    clickListener!!.onClick(position, "editMeasureClick")
                }

                holder.im_delete.setTag(position)
                holder.im_delete.setOnClickListener {
                    clickListener!!.onClick(position, "deleteMeasureClick")
                }


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
        internal var tv_WorkType: TextView
        internal var tv_MeasurementType: TextView
        internal var tv_Value: TextView
        internal var tv_Unit: TextView

        internal var im_edit: ImageView
        internal var im_delete: ImageView



        init {
            tv_WorkType = v.findViewById<View>(R.id.tv_WorkType) as TextView
            tv_MeasurementType = v.findViewById<View>(R.id.tv_MeasurementType) as TextView
            tv_Value = v.findViewById<View>(R.id.tv_Value) as TextView
            tv_Unit = v.findViewById<View>(R.id.tv_Unit) as TextView

            im_edit = v.findViewById<View>(R.id.im_edit) as ImageView
            im_delete = v.findViewById<View>(R.id.im_delete) as ImageView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}