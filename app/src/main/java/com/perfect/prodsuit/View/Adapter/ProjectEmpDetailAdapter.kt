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
import com.perfect.prodsuit.Model.ModelProjectEmpDetails
import com.perfect.prodsuit.R

class ProjectEmpDetailAdapter  (internal var context: Context, internal var mList: List<ModelProjectEmpDetails>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProjectEmpDetailAdapter"
    val data = ArrayList<ModelProjectEmpDetails>()
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_project_emp_details, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")

                val empModel = mList[position]
                holder.tv_category.text = empModel.Department
                holder.tv_employee.text = empModel.Employee+" / "+ empModel.EmployeeType
//                holder.tv_emp_type.text =


                holder.im_delete!!.setTag(position)
                holder.im_delete!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "deleteArrayList")
                })

                holder.im_edit!!.setTag(position)
                holder.im_edit!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "editArrayList")
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
        var tv_category : TextView
        var tv_employee : TextView
        var im_delete   : ImageView
        var im_edit     : ImageView
//        var tv_emp_type: TextView


        init {
            tv_category = v.findViewById(R.id.tv_category) as TextView
            tv_employee = v.findViewById(R.id.tv_employee) as TextView
            im_delete  = v.findViewById(R.id.im_delete) as ImageView
            im_edit = v.findViewById(R.id.im_edit) as ImageView
//            tv_emp_type = v.findViewById(R.id.tv_emp_type) as TextView

        }
    }

//    fun setClickListener(itemClickListener: ItemClickListenerValue) {
//        clickListener = itemClickListener
//    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}