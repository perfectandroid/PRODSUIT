package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelServiceAttended
import com.perfect.prodsuit.Model.ModelWalkingExist
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat

class WalkingExistingAdapter (internal var context: Context, internal var modelWalkingExist: List<ModelWalkingExist>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "WalkingExistingAdapter"
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_walking_existing, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")

                val ItemsModel = modelWalkingExist[position]
                holder.adp_lead_number.text = "Lead No : "+ItemsModel.LeadNo
                holder.adp_customer.text = ItemsModel.Customer
                holder.adp_mobile.text = ItemsModel.Mobile
                holder.adp_action.text = ItemsModel.Action
                holder.adp_assignedto.text = ItemsModel.AssignedTo
                holder.adp_followupdate.text = ItemsModel.FollowUpDate

                holder.adp_imgEdit!!.setTag(position)
                holder.adp_imgEdit!!.setOnClickListener(View.OnClickListener {
                    Log.e(TAG,"Click   51111  "+position)
                    clickListener!!.onClick(
                        position,
                        "clickLead"
                    )
                })

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   51111   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        return modelWalkingExist.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var adp_lead_number: TextView
        var adp_customer: TextView
        var adp_mobile: TextView
        var adp_action: TextView
        var adp_assignedto: TextView
        var adp_followupdate: TextView
        var adp_imgEdit: ImageView


        init {
            adp_lead_number = v.findViewById(R.id.adp_lead_number) as TextView
            adp_customer = v.findViewById(R.id.adp_customer) as TextView
            adp_mobile = v.findViewById(R.id.adp_mobile) as TextView
            adp_action = v.findViewById(R.id.adp_action) as TextView
            adp_assignedto = v.findViewById(R.id.adp_assignedto) as TextView
            adp_followupdate = v.findViewById(R.id.adp_followupdate) as TextView
            adp_imgEdit = v.findViewById(R.id.adp_imgEdit) as ImageView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}